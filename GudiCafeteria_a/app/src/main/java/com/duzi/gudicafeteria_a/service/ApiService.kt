package com.duzi.gudicafeteria_a.service

import com.duzi.gudicafeteria_a.data.*
import io.reactivex.Observable
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // Cafe 전체리스트
    @GET("/Cafeteria_S/allcafes/{today}/{sortType}/{lat}/{lon}/{count}")
    fun getCafeList(@Path("today") today: String,
                    @Path("sortType") sortType: Int,
                    @Path("lat") lat: Double,
                    @Path("lon") lon: Double,
                    @Path("count") count: Int): Observable<List<Cafe>>

    // 지정된 기간 + 카페 Id에 해당되는 Cafe 리스트
    @GET("/Cafeteria_S/cafemenu/{id}/{start}/{end}")
    fun getWeeklyMenus(@Path("id") id: String,
                       @Path("start") start: Long,
                       @Path("end") end: Long): Observable<List<Menu>>


    // 카페 Id에 해당되는 댓글 목록 리스트 ( 상세페이지에서 댓글리스트 출력/댓글달기)
    @GET("Cafeteria_S/allcomments/{cafeId}")
    fun getComments(@Path("cafeId") cafeId: String): Observable<List<Review>>

    // 유저 Id에 해당되는 댓글 목록 리스트 ( 댓글목록 관리/수정/삭제)
    @POST("Cafeteria_S/comment/insert")
    fun insertComment(@Body comment: Comment): Call<Int>

    @DELETE("/Cafeteria_S/user/delete/{cafeId}/{userId}/{seq}")
    fun deleteComment(@Path("cafeId") cafeId: String,
                      @Path("userId") userId: String,
                      @Path("seq") seq: Int): Call<Int>

    @GET("/Cafeteria_S/user/comment/{userId}")
    fun getMyComments(@Path("userId") userId: String): Observable<List<Review>>

    // 공지사항 전체리스트
    @GET("Cafeteria_S/allnotices")
    fun getAllNotices(): Observable<List<Notice>>

    // 유저 Id에 해당되는 즐겨찾기 리스트 ( 즐겨찾기 관리/수정/삭제)
    @GET("Cafeteria_S/allfavorites/{userId}")
    fun getAllFavoritesById(@Path("userId") userId: String): Observable<List<Favorite>>

    @POST("Cafeteria_S/favorite/insert")
    fun insertFavorite(@Body favorite: Favorite): Call<Int>

    @DELETE("Cafeteria_S/favorite/delete/{cafe_id}/{user_id}")
    fun deleteFavorite(@Path("cafeId") cafeId: String,
                       @Path("userId") userId: String)

    // 유저 정보 ( 로그인/삭제 )
    @GET("/Cafeteria_S/user/{userId}")
    fun getUser(@Path("userId") userId: String): Observable<User>

    @POST("/Cafeteria_S/user/insert")
    fun insertUser(@Body user: User): Call<Int>

    @DELETE("/Cafeteria_S/user/delete/{userId}")
    fun deleteUser(@Path("userId") userId: String): Call<Int>

    companion object {
        val HTTP_API_BASE_URL = "http://54.180.100.89:8080/"
    }
}