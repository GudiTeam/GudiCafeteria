package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout

abstract class NaviBaseView(context: Context?) : LinearLayout(context) {

    init {
        addView(LayoutInflater.from(context).inflate(layoutRes(), this, false))
        setupButtonsEvents()
    }

    protected open fun setupButtonsEvents() {}
    abstract fun layoutRes(): Int
}