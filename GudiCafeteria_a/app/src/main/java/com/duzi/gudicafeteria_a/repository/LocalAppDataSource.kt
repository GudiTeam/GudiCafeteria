package com.duzi.gudicafeteria_a.repository

import com.duzi.gudicafeteria_a.data.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call

// room 사용 예정
object LocalAppDataSource: AppDataSource {

    override fun getCafes(date: String, sortType: Int, lat: Double, lon: Double, count: Int): Observable<List<Cafe>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeeklyMenus(id: String, start: Long, end: Long): Observable<List<Menu>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCafeById(id: String): Cafe {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCommentsById(id: String): Observable<List<Comment>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertComment(comment: Comment): Call<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteComment(cafeId: String, userId: String, seq: String): Call<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateComment(cafeId: String, userId: String, seq: String, score: String, comment: String): Call<Int>  {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserById(id: String): Observable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertUser(user: User): Call<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(userId: String): Call<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFavoritesById(userId: String): Observable<List<Favorite>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertFavorite(favorite: Favorite): Single<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteFavorite(cafeId: String, userId: String): Single<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}