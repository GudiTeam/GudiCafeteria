package com.duzi.gudicafeteria_a.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.duzi.gudicafeteria_a.repository.AppDataSource
import com.duzi.gudicafeteria_a.ui.cafe.CafeViewModel
import com.duzi.gudicafeteria_a.ui.comment.CommentViewModel
import com.duzi.gudicafeteria_a.ui.favorite.FavoriteViewModel
import com.duzi.gudicafeteria_a.ui.user.UserViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val dataSource: AppDataSource): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CafeViewModel::class.java) -> CafeViewModel(dataSource) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(dataSource) as T
            modelClass.isAssignableFrom(CommentViewModel::class.java) -> CommentViewModel(dataSource) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(dataSource) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class..")
        }
    }

}