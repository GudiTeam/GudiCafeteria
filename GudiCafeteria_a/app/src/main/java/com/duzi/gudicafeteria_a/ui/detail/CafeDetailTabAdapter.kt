package com.duzi.gudicafeteria_a.ui.detail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CafeDetailTabAdapter(private val context: Context,
                           private val fm: FragmentManager,
                           internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> MenuFragment()
            1 -> ReviewFragment()
            2 -> MenuFragment()
            3 -> ReviewFragment()

            else -> null
        }
    }

    override fun getCount(): Int = totalTabs
}