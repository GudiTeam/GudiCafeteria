package com.duzi.gudicafeteria_a.util

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

val TAG = "Cafeteria"

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
        val pressedBgColor = if (alpha < 0xFF) {
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
        } else {
            view.setBackgroundDrawable(bg)
        }
    }

    fun DpToPx(dp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px)
    }

    fun PxToDp(px: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return Math.round(dp)
    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable?.intrinsicWidth!!, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}