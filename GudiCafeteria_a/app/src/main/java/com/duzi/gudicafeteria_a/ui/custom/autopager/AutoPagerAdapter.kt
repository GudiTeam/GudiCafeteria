package com.duzi.gudicafeteria_a.ui.custom.autopager

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jude.rollviewpager.RollPagerView
import com.jude.rollviewpager.adapter.LoopPagerAdapter

/**
 *  https://github.com/Jude95/RollViewPager
 */
class AutoPagerAdapter(viewPager: RollPagerView,
                       private val imageIdList: List<Int>) : LoopPagerAdapter(viewPager) {
    override fun getView(container: ViewGroup?, position: Int): View {
        val imageView = ImageView(container?.context)
        imageView.setImageResource(imageIdList[position])
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return imageView
    }

    override fun getRealCount(): Int = imageIdList.size

}