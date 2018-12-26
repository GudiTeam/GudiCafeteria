package com.duzi.gudicafeteria_a.base

import com.duzi.gudicafeteria_a.repository.AppDataSource
import com.duzi.gudicafeteria_a.repository.AppRepository
import com.duzi.gudicafeteria_a.repository.LocalAppDataSource
import com.duzi.gudicafeteria_a.repository.RemoteAppDataSource
import com.duzi.gudicafeteria_a.viewmodel.ViewModelFactory

object Injection {
    fun provideAppDataSource(): AppDataSource {
        return AppRepository.getInstance(LocalAppDataSource, RemoteAppDataSource)
    }

    fun provideViewModelFactory(): ViewModelFactory {
        val dataSource = provideAppDataSource()
        return ViewModelFactory(dataSource)
    }
}