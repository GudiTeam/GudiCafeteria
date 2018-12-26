package com.duzi.gudicafeteria_a.ui.cafe

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.duzi.gudicafeteria_a.base.BaseViewModel
import com.duzi.gudicafeteria_a.data.*
import com.duzi.gudicafeteria_a.repository.AppDataSource
import com.duzi.gudicafeteria_a.repository.AppRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class CafeViewModel(dataSource: AppDataSource): BaseViewModel(dataSource) {
    private val cafesLiveData= MutableLiveData<List<Cafe>>()
    private val query = MutableLiveData<WeeklyMenusQuery>()

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

    private val cafeId = MutableLiveData<String>()
    private val cafe = Transformations.switchMap(cafeId) { id ->
        val cafeLivedData = MutableLiveData<Cafe>()
        cafeLivedData.value = dataSource.getCafeById(id)
        cafeLivedData
    }

    private val comments = Transformations.switchMap(cafeId) { id ->
        val commentsLiveData = MutableLiveData<List<Comment>>()
        disposables += dataSource.getCommentsById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                commentsLiveData.postValue(it)
            }

        commentsLiveData
    }


    private val commentsWithUser = Transformations.switchMap(cafeId) { id ->
        val commentsWithUserLiveData = MutableLiveData<List<ReviewWithUser>>()

        val userObservable: Observable<List<User>> = dataSource.getCommentsById(id).concatMapIterable { comments -> comments }
                .concatMap { comment -> dataSource.getUserById(comment.user_Id) }
                .toList()
                .toObservable()

        disposables += Observable.combineLatest(dataSource.getCommentsById(id),
                userObservable,
                BiFunction<List<Comment>, List<User>, List<ReviewWithUser>> { comment, user ->
                    val size = comment.size
                    val lists = mutableListOf<ReviewWithUser>()
                    for(i in 0 until size) {
                        lists.add(ReviewWithUser(comment[i], user[i]))
                    }

                    lists
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    commentsWithUserLiveData.postValue(it)
                }

        commentsWithUserLiveData
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


    fun getComments() = comments
    fun getCommentsWithUser(): LiveData<List<ReviewWithUser>> = commentsWithUser!!

    // 일주일치 식당+메뉴 요청    ex) 20181201 ~ 20181208 의 식당+메뉴
    fun reqeustWeeklyMenus(input: WeeklyMenusQuery) {
        query.value = input
    }

    // 일주일치 식당+메뉴 받기
    fun getWeeklyMenus(): LiveData<List<Menu>> = weeklyMenusLiveData

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

data class WeeklyMenusQuery(
        val cafeId: String,
        val start: Long,
        val end: Long
)