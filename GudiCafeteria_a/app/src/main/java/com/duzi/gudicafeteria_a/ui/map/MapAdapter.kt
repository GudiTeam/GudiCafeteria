package com.duzi.gudicafeteria_a.ui.map

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.duzi.gudicafeteria_a.data.Cafe

/**
 * [ISSUE]
 * FragmentPagerAdapter -> FragmentStatePagerAdapter로 변경
 * FragmentPagerAdapter는 영구적으로 변하지 않는 Fragment에 적합함. 미리 로드해놓고 사용하기때문에 빠르게 로드할 수 있음
 * FragmentStatePagerAdapter는 Fragment가 많은 경우 적합함. 대신 화면에 안보이는 뷰들은 메모리를 해제시켜줌. 적은 메모리를 차지함
 * 지도 하단에 마커에 매칭되는 Fragment들이 ViewPager에 저장되어 Adpater를 통해 보여주는 케이스이므로 FragmentStatePagerAdapter로 변경함
 */
class MapAdapter(private val context: Context,
                 fm: FragmentManager,
                 private val cafes: ArrayList<Cafe>): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = MapFragment.newInstance(position, cafes[position])

    override fun getCount(): Int = cafes.size

    // ViewPager 양쪽 끝 보이게하기
    override fun getPageWidth(position: Int): Float = 0.95f

    fun addAll(cafes: List<Cafe>) = this.cafes.addAll(cafes)

}