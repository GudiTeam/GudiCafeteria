package com.duzi.gudicafeteria_a.repository

import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.Comment
import com.duzi.gudicafeteria_a.data.Menu
import com.duzi.gudicafeteria_a.data.User
import io.reactivex.Observable
import retrofit2.Call

interface AppDataSource {

    /**
     * 식당 리스트를 받아온다
     * date: date에 따라 cafe의 메뉴가 다르게 나옴
     * sortType: 정렬방식  1. 거리순   2. 평점순  3. 기본(데이터입력순)
     * lat: 위도
     * lon: 경도
     * count: 얻고 싶은 페이지
     */
    fun getCafes(date: String, sortType: Int, lat: Double, lon: Double, count: Int): Observable<List<Cafe>>

    /**
     * 지정한 식당의 기간별 메뉴를 보여준다
     * start - end
     */
    fun getWeeklyMenus(id: String,
                       start: Long,
                       end: Long): Observable<List<Menu>>

    /**
     * 식당 상세페이지 정보
     */
    fun getCafeById(id: String): Cafe

    /**
     * 식당 리뷰 가져오기
     */
    fun getCommentsById(id: String): Observable<List<Comment>>

    /**
     * 식당 리뷰 추가하기
     */
    fun insertComment(comment: Comment): Call<Int>

    /**
     * 식당 리뷰 삭제
     */
    fun deleteComment(cafeId: String,
                      userId: String,
                      seq: String): Call<Int>

    /**
     * 식당 리뷰 수정
     */
    fun updateComment(cafeId: String,
                      userId: String,
                      seq: String,
                      score: String,
                      comment: String): Call<Int>

    /**
     * 유저 정보 가져오기
     */
    fun getUserById(id: String): Observable<User>

    /**
     * 유저 추가
     */
    fun insertUser(user: User): Call<Int>

    /**
     * 유저 삭제
     */
    fun deleteUser(userId: String): Call<Int>
}