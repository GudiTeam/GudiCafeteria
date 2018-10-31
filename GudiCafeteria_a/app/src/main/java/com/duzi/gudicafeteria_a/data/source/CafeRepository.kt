package com.duzi.gudicafeteria_a.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.Menu
import com.duzi.gudicafeteria_a.service.CafeService
import com.duzi.gudicafeteria_a.service.CafeService.Companion.HTTP_API_CAFE_URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CafeRepository {

    private val cafeService: CafeService by lazy {
        CafeRepository.create()
    }

    private val mutableData: MutableLiveData<List<Cafe>> = MutableLiveData()

    fun loadData() {
        // get data from retrofit
        val disposable = cafeService.getCafeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mutableData.postValue(it)
                }

        val menu_L = Menu("CAFE001", "20181023", "L", "MENUIMG", "MENUDIR",
                "흰쌀밥_02L23", "된장찌개_02L23", "사이드1_02L23", "사이드1_02L23", "사이드1_02L23",
                "사이드1_02L23", "사이드1_02L23", "사이드1_02L23", "사이드1_02L23", "사이드1_02L23",
                "사이드1_02L23")
        val menu_D = Menu("CAFE001", "20181023", "D", "MENUIMG", "MENUDIR",
                "흰쌀밥_02L23", "된장찌개_02L23", "사이드1_02L23", "사이드1_02L23", "사이드1_02L23",
                "사이드1_02L23", "사이드1_02L23", "사이드1_02L23", "사이드1_02L23", "사이드1_02L23",
                "사이드1_02L23")
        val cafe1 = Cafe("CAFE001", "구디식당001", "imgname1", "imgdir1",
                5000, "Y", "Y", "점심 - 11:30 ~ 13:30, 저녁 17:30 ~ 19:00",
                "서울 구로구 디지털로29길 01", "에이스테크노타워1차", "02-999-0001",
                "Home1", "Key_ID1", 11111.22, 22221.35, 91.5, "Y",
                menu_L, menu_D)


        val dataList:List<Cafe> = listOf(cafe1, cafe1, cafe1, cafe1, cafe1, cafe1, cafe1, cafe1, cafe1, cafe1)
        mutableData.postValue(dataList)
    }

    fun getData(): LiveData<List<Cafe>> = mutableData

    companion object {
        private var INSTANCE: CafeRepository? = null

        fun getInstance() =
                INSTANCE ?: synchronized(CafeRepository::class.java) {
                    INSTANCE ?: CafeRepository().also { INSTANCE = it }
                }

        fun distoryInstance() {
            INSTANCE = null
        }

        fun create(): CafeService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(HTTP_API_CAFE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(CafeService::class.java)
        }
    }
}