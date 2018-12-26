package com.duzi.gudicafeteria_a.ui.comment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.duzi.gudicafeteria_a.base.BaseViewModel
import com.duzi.gudicafeteria_a.data.Comment
import com.duzi.gudicafeteria_a.repository.AppDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class CommentViewModel(dataSource: AppDataSource): BaseViewModel(dataSource) {


    fun getCommentsById(cafeId: String): LiveData<List<Comment>> {
        val comments = MutableLiveData<List<Comment>>()
        disposables += dataSource.getCommentsById(cafeId).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    comments.postValue(it)
                }
        return comments
    }

}