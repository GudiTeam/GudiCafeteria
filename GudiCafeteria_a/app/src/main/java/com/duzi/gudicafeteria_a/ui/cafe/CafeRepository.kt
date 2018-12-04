package com.duzi.gudicafeteria_a.ui.cafe

import android.arch.lifecycle.MutableLiveData
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.service.ApiService
import com.duzi.gudicafeteria_a.service.ApiService.Companion.HTTP_API_BASE_URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CafeRepository {

    private val apiService: ApiService by lazy {
        create()
    }

    private val cafeList: MutableLiveData<List<Cafe>> = MutableLiveData()
    private val cafeListCache: ArrayList<Cafe> = arrayListOf()
    private val cafeListPeriod: MutableLiveData<List<Cafe>> = MutableLiveData()

    fun loadCafeList(date: String): Disposable {
        return apiService.getCafeList(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    cafeListCache.addAll(it)
                    cafeList.postValue(it)
                }
    }

    fun getCafeList() = cafeList
    fun getCafeListCache(): List<Cafe> = cafeListCache
    fun clearCache() = cafeListCache.clear()

    fun loadCafeListPeriod(cafeId: String, start: Long, end: Long): Disposable {
        return apiService.getCafeListPeriod(cafeId, start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    cafeListPeriod.postValue(it)
                }
    }

    fun getCafeListPeriod() = cafeListPeriod


    companion object {
        private var INSTANCE: CafeRepository? = null

        fun getInstance() =
                INSTANCE ?: synchronized(CafeRepository::class.java) {
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