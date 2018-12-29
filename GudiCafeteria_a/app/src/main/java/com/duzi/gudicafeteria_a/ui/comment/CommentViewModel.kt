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


    fun insertComment(comment: Comment): LiveData<ApiResponse<Int>> {
        val responseLiveData = MutableLiveData<ApiResponse<Int>>()
        dataSource.insertComment(comment).enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                responseLiveData.postValue(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                responseLiveData.postValue(ApiResponse.create(response))
            }
        })
        return responseLiveData
    }


    fun deleteComment(comment: Comment): LiveData<ApiResponse<Int>> {
        val responseLiveData = MutableLiveData<ApiResponse<Int>>()
        dataSource.deleteComment(comment.cafe_Id, comment.user_Id, comment.seq.toString()).enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                responseLiveData.postValue(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                responseLiveData.postValue(ApiResponse.create(response))
            }
        })
        return responseLiveData
    }


    fun updateComment(comment: Comment): LiveData<ApiResponse<Int>> {
        val responseLiveData = MutableLiveData<ApiResponse<Int>>()
        dataSource.updateComment(comment.cafe_Id, comment.user_Id, comment.seq.toString(),
                comment.comment_score, comment.comment).enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                responseLiveData.postValue(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                responseLiveData.postValue(ApiResponse.create(response))
            }
        })
        return responseLiveData
    }

}