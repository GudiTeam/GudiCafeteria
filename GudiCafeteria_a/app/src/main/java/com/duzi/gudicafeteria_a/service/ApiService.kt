package com.duzi.gudicafeteria_a.service

import com.duzi.gudicafeteria_a.data.Cafe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("")
    fun getCafeList(): Observable<List<Cafe>>

    @GET("")
    fun getCafeListPeriod(@Query("id") id: String,
                          @Query("start") start: Long,
                          @Query("end") end: Long): Observable<List<Cafe>>


    companion object {
        val HTTP_API_BASE_URL = "http://xxx.xxxx.xxx/"
    }
}