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
import java.util.stream.Stream

class CafeViewModel: ViewModel() {
    private val repo: CafeRepository by lazy { CafeRepository.getInstance() }
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val cafesLiveData: LiveData<List<Cafe>> = repo.getCafes()
    private var query: MutableLiveData<WeeklyMenusQuery> = MutableLiveData()
    private val weeklyMenus: LiveData<List<Menu>> = Transformations.switchMap(query) { querySet ->
        if(querySet == null) {
            MutableLiveData()
        } else {
            repo.getWeeklyMenus(querySet)
        }
    }

    private val cafeId: MutableLiveData<String> = MutableLiveData()
    private val cafe: LiveData<Cafe> = Transformations.switchMap(cafeId) {
        id -> repo.getCafeById(id)
    }
    private val commentsLiveData: MutableLiveData<List<Review>> = MutableLiveData()
    private val comments: LiveData<List<Review>> = Transformations.switchMap(cafeId) { id ->
        disposables += repo.getCommentsById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                commentsLiveData.postValue(it)
            }

        commentsLiveData
    }

    private val commentsWithUserLiveData = MutableLiveData<List<ReviewWithUser>>()
    private val commentsWithUser: LiveData<List<ReviewWithUser>> = Transformations.switchMap(cafeId) { id ->
        val userObservable: Observable<List<User>> = repo.getCommentsById(id).take(1).concatMapIterable { comments -> comments }
                .concatMap { comment -> repo.getUserById(comment.user_Id).take(1) }
                .toList()
                .toObservable()

        Observable.combineLatest(repo.getCommentsById(id),
                userObservable,
                BiFunction<List<Review>, List<User>, List<ReviewWithUser>> { comment, user ->
                    val size = comment.size
                    val lists = mutableListOf<ReviewWithUser>()
                    for(i in 0 until size) {
                        lists.add(ReviewWithUser(comment[i], user[i]))
                    }

                    Stream.of(comment, user)
                            .flatMap { Stream::of }
                            .toArray()

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
    fun getCacheCafes(): List<Cafe> = repo.getCacheCafes()
    fun clearCache() = repo.clearCache()
    fun setCafeId(id: String) {
        cafeId.value = id
    }
    fun getCafe() = cafe
    fun getComments() = comments
    fun getCommentsWithUser() = commentsWithUser

    // 일주일치 식당+메뉴 요청    ex) 20181201 ~ 20181208 의 식당+메뉴
    fun reqeustWeeklyMenus(input: WeeklyMenusQuery) {
        query.value = input
    }

    // 일주일치 식당+메뉴 접근
    fun getWeeklyMenus(): LiveData<List<Menu>> = weeklyMenus

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