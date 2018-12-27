package com.duzi.gudicafeteria_a.ui.user

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.duzi.gudicafeteria_a.base.BaseViewModel
import com.duzi.gudicafeteria_a.data.User
import com.duzi.gudicafeteria_a.repository.AppDataSource
import com.duzi.gudicafeteria_a.service.ApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(dataSource: AppDataSource): BaseViewModel(dataSource) {


    fun getUserById(userId: String): LiveData<User> {
        val userLiveData = MutableLiveData<User>()
        disposables += dataSource.getUserById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    userLiveData.postValue(it)
                }
        return userLiveData
    }


    fun insertUser(user: User, callback: (ApiResponse<Int>) -> Unit){
        dataSource.insertUser(user).enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                callback(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                callback(ApiResponse.create(response))
            }
        })
    }

    fun deleteUser(userId: String, callback: (ApiResponse<Int>) -> Unit) {
        dataSource.deleteUser(userId).enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                callback(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                callback(ApiResponse.create(response))
            }
        })
    }

}