package com.duzi.gudicafeteria_a.repository

import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.Menu
import com.duzi.gudicafeteria_a.data.Comment
import com.duzi.gudicafeteria_a.data.User
import io.reactivex.Observable
import retrofit2.Call
import java.util.*

class AppRepository(
        private val localDataSource: LocalAppDataSource,
        private val remoteDataSource: RemoteAppDataSource): AppDataSource {

    private val cachedCafeList: ArrayList<Cafe> = arrayListOf()  // 지도화면에서 사용할 카페리스트 캐시
    private val cachedCafeMap = LinkedHashMap<String, Cafe>()  // 상세페이지에서 사용할 카페리스트 캐시

    fun addCache(cafes: List<Cafe>) {
        cachedCafeList.addAll(cafes)
        for(cafe in cafes) {
            cachedCafeMap[cafe.cafe_Id] = cafe
        }
    }

    fun getCachedCafeList(): List<Cafe> = cachedCafeList

    fun clearCache() {
        cachedCafeList.clear()
        cachedCafeMap.clear()
    }

    override fun getCafes(date: String, sortType: Int, lat: Double, lon: Double, count: Int): Observable<List<Cafe>> {
        // TODO local 값 비교 루틴 추가
        return remoteDataSource.getCafes(date, sortType, lat, lon, count)
    }

    override fun getCafeById(id: String): Cafe {
        if(cachedCafeMap[id] != null) {
            // TODO 캐시에 데이터없는 경우 DB, Network에서 get하는 기능 추가
        }
        return cachedCafeMap[id]!!
    }

    override fun getWeeklyMenus(id: String, start: Long, end: Long): Observable<List<Menu>> {
        return remoteDataSource.getWeeklyMenus(id, start, end)
    }

    override fun getCommentsById(id: String): Observable<List<Comment>> {
        // TODO local 값 비교 루틴 추가
        return remoteDataSource.getCommentsById(id)
    }

    override fun insertComment(comment: Comment): Call<Int> {
        return remoteDataSource.insertComment(comment)
    }

    override fun deleteComment(cafeId: String, userId: String, seq: String): Call<Int> {
        return remoteDataSource.deleteComment(cafeId, userId, seq)
    }

    override fun updateComment(cafeId: String, userId: String, seq: String, score: String, comment: String): Call<Int>  {
        return remoteDataSource.updateComment(cafeId, userId, seq, score, comment)
    }

    override fun getUserById(id: String): Observable<User> {
        return remoteDataSource.getUserById(id)
    }

    override fun insertUser(user: User): Call<Int> {
        return remoteDataSource.insertUser(user)
    }

    override fun deleteUser(userId: String): Call<Int> {
        return remoteDataSource.deleteUser(userId)
    }

    companion object {
        private var INSTANCE: AppRepository? = null

        fun getInstance(localDataSource: LocalAppDataSource,
                        remoteDataSource: RemoteAppDataSource) =
                INSTANCE ?: synchronized(AppRepository::class.java) {
                    INSTANCE ?: AppRepository(localDataSource, remoteDataSource).also { INSTANCE = it }
                }

        fun distoryInstance() {
            INSTANCE = null
        }

    }
}