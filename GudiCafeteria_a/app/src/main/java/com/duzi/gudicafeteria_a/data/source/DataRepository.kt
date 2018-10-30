package com.duzi.gudicafeteria_a.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.duzi.gudicafeteria_a.data.Cafe

class DataRepository {

    private val mutableData: MutableLiveData<List<Cafe>> = MutableLiveData()
    fun getData(): LiveData<List<Cafe>> = mutableData
    fun loadData() {
        // get data from retrofit
        val dataList = listOf(Cafe("asdf"), Cafe("zxcv"))
        mutableData.postValue(dataList)
    }


    companion object {
        private var INSTANCE: DataRepository? = null

        fun getInstance() =
                INSTANCE ?: synchronized(DataRepository::class.java) {
                    INSTANCE ?: DataRepository().also { INSTANCE = it }
                }

        fun distoryInstance() {
            INSTANCE = null
        }
    }
}