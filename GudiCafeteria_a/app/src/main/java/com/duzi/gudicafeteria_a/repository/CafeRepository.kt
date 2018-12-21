package com.duzi.gudicafeteria_a.repository

import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.Menu
import com.duzi.gudicafeteria_a.data.Review
import com.duzi.gudicafeteria_a.data.User
import com.duzi.gudicafeteria_a.service.ApiService
import com.duzi.gudicafeteria_a.service.ApiService.Companion.HTTP_API_BASE_URL
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class CafeRepository {

    private val apiService: ApiService by lazy {
        create()
    }

    private val cafeListCache: ArrayList<Cafe> = arrayListOf()  // 지도화면에서 사용할 카페리스트 캐시
    private val cafesMap = HashMap<String, Cafe>()  // 상세페이지에서 사용할 카페리스트 캐시

    // 해당날짜의 카페 리스트 네트워크에서 받기
    fun loadCafeList(date: String, sortType: Int, lat: Double, lon: Double, count: Int):  Observable<List<Cafe>> =
            apiService.getCafeList(date, sortType, lat, lon, count)

    fun addCache(cafes: List<Cafe>) {
        cafeListCache.addAll(cafes)
        for(cafe in cafes) {
            cafesMap[cafe.cafe_Id] = cafe
        }
    }

    fun getCacheCafes(): List<Cafe> = cafeListCache

    fun clearCache() {
        cafeListCache.clear()
        cafesMap.clear()
    }

    fun getCafeById(id: String): Cafe = cafesMap[id]!!

    fun getCommentsById(id: String): Observable<List<Review>> = apiService.getComments(id)

    fun getUserById(id: String): Observable<User> = apiService.getUser(id)

    fun loadWeeklyMenus(cafeId: String, start: Long, end: Long): Observable<List<Menu>> =
            apiService.getWeeklyMenus(cafeId, start, end)

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