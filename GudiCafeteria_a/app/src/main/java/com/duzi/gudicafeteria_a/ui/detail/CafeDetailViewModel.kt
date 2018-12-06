package com.duzi.gudicafeteria_a.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.repository.CafeRepository

class CafeDetailViewModel: ViewModel() {
    // Singleton Repository
    private val repo: CafeRepository = CafeRepository.getInstance()

    private val cafeId: MutableLiveData<String> = MutableLiveData()
    private val cafe: LiveData<Cafe> = Transformations.switchMap(cafeId) { id ->
        repo.getCafeById(id)
    }

    // id값만 바꾸면 cafe에 새로운 값이 갱신된다
    fun setCafeId(id: String) {
        cafeId.value = id
    }

    fun getCafe() = cafe
}