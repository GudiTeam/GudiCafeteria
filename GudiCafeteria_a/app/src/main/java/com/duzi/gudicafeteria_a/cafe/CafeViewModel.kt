package com.duzi.gudicafeteria_a.cafe

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.source.DataRepository

class CafeViewModel: ViewModel() {
    private val repo: DataRepository = DataRepository.getInstance()

    private val cafeData: LiveData<List<Cafe>>? = repo.getData()
    fun getData(): LiveData<List<Cafe>> = cafeData!!
}