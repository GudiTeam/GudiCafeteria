package com.duzi.gudicafeteria_a.service

import com.duzi.gudicafeteria_a.data.Cafe
import io.reactivex.Observable
import retrofit2.http.GET

interface CafeService {
    @GET("")
    fun getCafeList(): Observable<List<Cafe>>

    companion object {
        val HTTP_API_CAFE_URL = "http://xxx.xxxx.xxx/"
    }
}