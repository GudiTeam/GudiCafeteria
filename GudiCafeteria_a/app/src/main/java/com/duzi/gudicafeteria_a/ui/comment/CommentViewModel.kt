package com.duzi.gudicafeteria_a.ui.comment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.duzi.gudicafeteria_a.base.BaseViewModel
import com.duzi.gudicafeteria_a.data.Comment
import com.duzi.gudicafeteria_a.repository.AppDataSource
import com.duzi.gudicafeteria_a.service.ApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel(dataSource: AppDataSource): BaseViewModel(dataSource) {


    fun getCommentsById(cafeId: String): LiveData<List<Comment>> {
        val comments = MutableLiveData<List<Comment>>()
        disposables += dataSource.getCommentsById(cafeId).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    comments.postValue(it)
                }, {
                    it.printStackTrace()
                })
        return comments
    }

    fun insertComment(comment: Comment, callback: (ApiResponse<Int>) -> Unit) {
        dataSource.insertComment(comment).enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                callback(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                callback(ApiResponse.create(response))
            }
        })
    }

    fun deleteComment(comment: Comment, callback: (ApiResponse<Int>) -> Unit) {
        dataSource.insertComment(comment).enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                callback(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                callback(ApiResponse.create(response))
            }
        })
    }

}