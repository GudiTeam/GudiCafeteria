package com.duzi.gudicafeteria_a.ui.cafe

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.duzi.gudicafeteria_a.base.BaseViewModel
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.Menu
import com.duzi.gudicafeteria_a.repository.AppDataSource
import com.duzi.gudicafeteria_a.repository.AppRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class CafeViewModel(dataSource: AppDataSource): BaseViewModel(dataSource) {
    private val cafesLiveData= MutableLiveData<List<Cafe>>()
    private val query = MutableLiveData<WeeklyMenusQuery>() // query 값에 따라 switchMap 동작
    private val cafeId = MutableLiveData<String>()  // id 값에 따라 switchMap 동작

    private val weeklyMenusLiveData = Transformations.switchMap(query) { querySet ->
        val weeklyMenus = MutableLiveData<List<Menu>>()
        disposables += dataSource.getWeeklyMenus(querySet.cafeId, querySet.start, querySet.end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    weeklyMenus.postValue(it)
                }

        weeklyMenus
    }

    private val cafe = Transformations.switchMap(cafeId) { id ->
        val cafeLivedData = MutableLiveData<Cafe>()
        cafeLivedData.value = dataSource.getCafeById(id)
        cafeLivedData
    }

    fun getCafes(): LiveData<List<Cafe>> = cafesLiveData
    fun loadCafes(date: String, sortType: Int, lat: Double, lon: Double, count: Int){
        disposables += dataSource.getCafes(date, sortType, lat, lon, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    (dataSource as AppRepository).addCache(it)
                    cafesLiveData.postValue(it)
                }
    }

    fun getCacheCafes(): List<Cafe> =
            (dataSource as AppRepository).getCachedCafeList()

    fun clearCache() = (dataSource as AppRepository).clearCache()

    fun setCafeId(id: String) {
        cafeId.value = id
    }
    fun getCafe(): LiveData<Cafe> = cafe


    // 일주일치 식당+메뉴 요청    ex) 20181201 ~ 20181208 의 식당+메뉴
    fun reqeustWeeklyMenus(input: WeeklyMenusQuery) {
        query.value = input
    }

    // 일주일치 식당+메뉴 받기
    fun getWeeklyMenus(): LiveData<List<Menu>> = weeklyMenusLiveData

}

data class WeeklyMenusQuery(
        val cafeId: String,
        val start: Long,
        val end: Long
)