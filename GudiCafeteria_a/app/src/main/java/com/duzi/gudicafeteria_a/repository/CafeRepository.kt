package com.duzi.gudicafeteria_a.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.Menu
import com.duzi.gudicafeteria_a.data.Review
import com.duzi.gudicafeteria_a.data.User
import com.duzi.gudicafeteria_a.service.ApiService
import com.duzi.gudicafeteria_a.service.ApiService.Companion.HTTP_API_BASE_URL
import com.duzi.gudicafeteria_a.ui.cafe.WeeklyMenusQuery
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class CafeRepository {

    private val apiService: ApiService by lazy {
        create()
    }

    private val cafeList: MutableLiveData<List<Cafe>> = MutableLiveData()
    private val cafeListCache: ArrayList<Cafe> = arrayListOf()
    private val cafesMap = HashMap<String, Cafe>()

    // 해당날짜의 카페 리스트 네트워크에서 받기
    fun loadCafeList(date: String, sortType: Int, lat: Double, lon: Double, count: Int): Disposable {
        return apiService.getCafeList(date, sortType, lat, lon, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    cafeListCache.addAll(it)
                    for(cafe in it) {
                        cafesMap[cafe.cafe_Id] = cafe
                    }
                    cafeList.postValue(it)
                }
    }

    // 카페리스트와 카페리스트 캐시 접근
    fun getCafes() = cafeList
    fun getCacheCafes(): List<Cafe> = cafeListCache
    // pull to refresh 할때 캐시데이터 삭제
    fun clearCache() = cafeListCache.clear()


    // 식당 상세페이지
    private val cafe: MutableLiveData<Cafe> = MutableLiveData()
    fun getCafeById(id: String): LiveData<Cafe> {
        cafe.value = cafesMap[id]
        return cafe
    }

    fun getCommentsById(id: String): Observable<List<Review>> {
        return apiService.getComments(id)
    }

    fun getUserById(id: String): Observable<User> {
        return apiService.getUser(id)
    }


    private val weeklyMenus: MutableLiveData<List<Menu>> = MutableLiveData()

    // 일주일치 식단 데이터 네트워크에서 받기
    private fun loadWeeklyMenus(cafeId: String, start: Long, end: Long): Disposable {
        return apiService.getWeeklyMenus(cafeId, start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    weeklyMenus.postValue(it)
                }
    }

    // 일주일치 식단 데이터 접근
    fun getWeeklyMenus(query: WeeklyMenusQuery): LiveData<List<Menu>> {
        loadWeeklyMenus(query.cafeId, query.start, query.end)
        return weeklyMenus
    }


    companion object {
        private var INSTANCE: CafeRepository? = null

        fun getInstance() = INSTANCE ?: synchronized(CafeRepository::class.java) {
                    INSTANCE ?: CafeRepository().also { INSTANCE = it }
                }

        fun distoryInstance() {
            INSTANCE = null
        }

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(HTTP_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}