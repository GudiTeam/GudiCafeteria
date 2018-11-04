package com.duzi.gudicafeteria_a.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.View


object Utils {

    /**
     * 반투명한 눌림 효과 만들기
     * @param view 효과를 넣을 뷰 ( ex TextView )
     * @param normalBgColor 기본 배경색
     */
    fun setPressedBackground(view: View, normalBgColor: Int) {
        val alpha = Color.alpha(normalBgColor)
        val red = Color.red(normalBgColor)
        val green = Color.green(normalBgColor)
        val blue = Color.blue(normalBgColor)
        val pressedBgColor = if(alpha < 0xFF) {
            Color.argb(0xFF, red, green, blue)
        } else {
            Color.argb(0x90, red, green, blue)
        }

        val normalShape = GradientDrawable()
        normalShape.setColor(normalBgColor)

        val pressedShape = GradientDrawable()
        pressedShape.setColor(pressedBgColor)

        val bg = StateListDrawable()
        bg.addState(intArrayOf(android.R.attr.state_pressed), pressedShape)
        bg.addState(intArrayOf(android.R.attr.state_enabled), normalShape)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = bg
        }else {
            view.setBackgroundDrawable(bg)
        }
    }
}