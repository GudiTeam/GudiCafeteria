package com.duzi.gudicafeteria_a.ui.cafe

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.Menu
import com.duzi.gudicafeteria_a.repository.CafeRepository

class CafeListViewModel: ViewModel() {
    // Singleton Repository
    private val repo: CafeRepository = CafeRepository.getInstance()

    // 카페 리스트를 관리하는 LiveData
    // 새 페이지가 로드되거나 새로고침할 때 값이 변경된다.
    private val cafesLiveData: LiveData<List<Cafe>> = repo.getCafes()
    // 카페 리스트를 외부로 노출시키는 메서드
    fun getCafes(): LiveData<List<Cafe>> = cafesLiveData

    // 페이지 별로 카페 리스트를 가져올 경우 total 리스트를 관리할 캐시 리스트.
    // 메모리에만 보관한다.
    fun getCacheCafes(): List<Cafe> = repo.getCacheCafes()
    fun clearCache() = repo.clearCache()

    private var query: MutableLiveData<WeeklyMenusQuery> = MutableLiveData()

    // Transformations.switchMap는 내부적으로 query의 값 변경을 감지하여 Rx의 flatMap처럼
    // query값을 이용해 새로운 LiveData를 만들어낸다
    private val weeklyMenus: LiveData<List<Menu>> = Transformations.switchMap(query) { querySet ->
        if(querySet == null) {
            MutableLiveData()
        } else {
            repo.getWeeklyMenus(querySet)
        }
    }

    // 일주일치 식당+메뉴 요청    ex) 20181201 ~ 20181208 의 식당+메뉴
    fun reqeustWeeklyMenus(input: WeeklyMenusQuery) {
        //if(input == query.value)
        //    return

        query.value = input
    }

    // 일주일치 식당+메뉴 접근
    fun getWeeklyMenus(): LiveData<List<Menu>> = weeklyMenus
}

data class WeeklyMenusQuery(
        val cafeId: String,
        val start: Long,
        val end: Long
)