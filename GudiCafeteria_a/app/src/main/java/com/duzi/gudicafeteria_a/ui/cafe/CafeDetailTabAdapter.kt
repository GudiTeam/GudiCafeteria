package com.duzi.gudicafeteria_a.ui.cafe

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.duzi.gudicafeteria_a.ui.review.ReviewFragment

class CafeDetailTabAdapter(fm: FragmentManager,
                           private var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> MenuFragment.getInstance()
            1 -> ReviewFragment.getInstance()

            else -> null
        }
    }

    override fun getCount(): Int = totalTabs
}