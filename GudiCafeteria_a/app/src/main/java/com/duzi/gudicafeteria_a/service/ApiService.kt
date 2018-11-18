package com.duzi.gudicafeteria_a.service

import com.duzi.gudicafeteria_a.data.Cafe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Cafe 전체리스트
    @GET("/Cafeteria_S/allcafes/{today}")
    fun getCafeList(@Path("today") today: String): Observable<List<Cafe>>

    // 지정된 기간 + 카페 Id에 해당되는 Cafe 리스트
    @GET("")
    fun getCafeListPeriod(@Query("id") id: String,
                          @Query("start") start: Long,
                          @Query("end") end: Long): Observable<List<Cafe>>


    // 카페 Id에 해당되는 댓글 목록 리스트 ( 상세페이지에서 댓글리스트 출력/댓글달기)
    // 유저 Id에 해당되는 댓글 목록 리스트 ( 댓글목록 관리/수정/삭제)

    // 공지사항 전체리스트
    // 유저 Id에 해당되는 즐겨찾기 리스트 ( 즐겨찾기 관리/수정/삭제)

    // 유저 정보 ( 로그인/삭제 )

    companion object {
        val HTTP_API_BASE_URL = "http://54.180.100.89:8080/"
    }
}