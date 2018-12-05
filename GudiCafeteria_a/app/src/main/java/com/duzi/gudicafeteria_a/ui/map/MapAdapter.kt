package com.duzi.gudicafeteria_a.ui.map

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.duzi.gudicafeteria_a.data.Cafe

class MapAdapter(private val context: Context,
                 fm: FragmentManager,
                 private val cafes: ArrayList<Cafe>): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = MapFragment.newInstance(position, cafes[position])

    override fun getCount(): Int = cafes.size

    // ViewPager 양쪽 끝 보이게하기
    override fun getPageWidth(position: Int): Float = 0.95f

    fun addAll(cafes: List<Cafe>) = this.cafes.addAll(cafes)

}