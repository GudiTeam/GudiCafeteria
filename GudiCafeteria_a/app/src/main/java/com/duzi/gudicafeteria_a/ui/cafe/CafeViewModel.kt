package com.duzi.gudicafeteria_a.ui.cafe

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.duzi.gudicafeteria_a.data.*
import com.duzi.gudicafeteria_a.repository.CafeRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class CafeViewModel: ViewModel() {
    private val repo: CafeRepository by lazy { CafeRepository.getInstance() }
    private val disposables = CompositeDisposable()
    private val cafesLiveData= MutableLiveData<List<Cafe>>()
    private val query = MutableLiveData<WeeklyMenusQuery>()
    private val weeklyMenus = MutableLiveData<List<Menu>>()
    private val weeklyMenusLiveData = Transformations.switchMap(query) { querySet ->
            disposables += repo.loadWeeklyMenus(querySet.cafeId, querySet.start, querySet.end)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        weeklyMenus.postValue(it)
                    }

        weeklyMenus

    }

    private val cafeId = MutableLiveData<String>()
    private val cafeLivedData = MutableLiveData<Cafe>()
    private val cafe = Transformations.switchMap(cafeId) { id ->
        cafeLivedData.value = repo.getCafeById(id)
        cafeLivedData
    }
    private val commentsLiveData = MutableLiveData<List<Review>>()
    private val comments = Transformations.switchMap(cafeId) { id ->
        disposables += repo.getCommentsById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                commentsLiveData.postValue(it)
            }

        commentsLiveData
    }

    private val commentsWithUserLiveData = MutableLiveData<List<ReviewWithUser>>()
    private val commentsWithUser = Transformations.switchMap(cafeId) { id ->
        val userObservable: Observable<List<User>> = repo.getCommentsById(id).concatMapIterable { comments -> comments }
                .concatMap { comment -> repo.getUserById(comment.user_Id) }
                .toList()
                .toObservable()

        disposables += Observable.combineLatest(repo.getCommentsById(id),
                userObservable,
                BiFunction<List<Review>, List<User>, List<ReviewWithUser>> { comment, user ->
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
    fun loadCafeList(date: String, sortType: Int, lat: Double, lon: Double, count: Int){
        disposables += repo.loadCafeList(date, sortType, lat, lon, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    repo.addCache(it)
                    cafesLiveData.postValue(it)
                }
    }

    fun getCacheCafes(): List<Cafe> = repo.getCacheCafes()
    fun clearCache() = repo.clearCache()
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