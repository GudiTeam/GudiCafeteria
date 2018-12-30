package com.duzi.gudicafeteria_a.ui.cafe

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.duzi.gudicafeteria_a.ui.comment.CommentFragment

class CafeDetailTabAdapter(fm: FragmentManager,
                           private val totalTabs: Int,
                           private val cafeId: String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> MenuFragment.getInstance()
            1 -> CommentFragment.newInstance(cafeId)

            else -> null
        }
    }

    override fun getCount(): Int = totalTabs
}