package com.duzi.gudicafeteria_a.base

import android.arch.lifecycle.ViewModel
import com.duzi.gudicafeteria_a.repository.AppDataSource
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(protected var dataSource: AppDataSource): ViewModel() {
    protected val disposables = CompositeDisposable()
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}