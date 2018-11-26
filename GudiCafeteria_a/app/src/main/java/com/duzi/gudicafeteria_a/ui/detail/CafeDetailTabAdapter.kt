package com.duzi.gudicafeteria_a.ui.detail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CafeDetailTabAdapter(private val context: Context,
                           fm: FragmentManager,
                           private val cafeId: Int,
                           private var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> MenuFragment.newInstance(cafeId)
            1 -> ReviewFragment.newInstance(cafeId)

            else -> null
        }
    }

    override fun getCount(): Int = totalTabs
}