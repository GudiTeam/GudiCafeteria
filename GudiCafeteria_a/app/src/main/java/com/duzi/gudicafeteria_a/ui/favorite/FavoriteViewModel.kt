package com.duzi.gudicafeteria_a.ui.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.duzi.gudicafeteria_a.base.BaseViewModel
import com.duzi.gudicafeteria_a.data.Favorite
import com.duzi.gudicafeteria_a.repository.AppDataSource
import com.duzi.gudicafeteria_a.service.ApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(appDataSource: AppDataSource): BaseViewModel(appDataSource) {


    fun getFavoritesById(userId: String): LiveData<List<Favorite>> {
        val favoritesLiveData = MutableLiveData<List<Favorite>>()
        disposables += dataSource.getFavoritesById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    favoritesLiveData.postValue(it)
                }

        return favoritesLiveData
    }


    fun insertFavorite(favorite: Favorite): LiveData<ApiResponse<Int>> {
        val responseLiveData = MutableLiveData<ApiResponse<Int>>()
        disposables += dataSource.insertFavorite(favorite)
                .toObservable()
                .subscribe ({
                    responseLiveData.postValue(ApiResponse.create(it))
                }, {
                    responseLiveData.postValue(ApiResponse.create(it))
                })

        return responseLiveData
    }


    fun deleteFavorite(cafeId: String, userId: String): LiveData<ApiResponse<Int>> {
        val responseLiveData = MutableLiveData<ApiResponse<Int>>()
        disposables += dataSource.deleteFavorite(cafeId, userId)
                .toObservable()
                .subscribe ({
                    responseLiveData.postValue(ApiResponse.create(it))
                }, {
                    responseLiveData.postValue(ApiResponse.create(it))
                })

        return responseLiveData
    }


}