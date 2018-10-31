package com.duzi.gudicafeteria_a.cafe

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.cafe.source.CafeRepository

class CafeViewModel: ViewModel() {
    private val repo: CafeRepository = CafeRepository.getInstance()

    private val cafeData: LiveData<List<Cafe>> = repo.getData()
    fun getData(): LiveData<List<Cafe>> = cafeData
}