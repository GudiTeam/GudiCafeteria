package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.navi_basic.view.*

class BasicView(context: Context?, title: String, var clickEvent: () -> Unit): NaviBaseView(context) {
    override fun layoutRes(): Int = R.layout.navi_basic

    init {
        tvTitle.text = title
    }

    override fun setupButtonsEvents() {
        root.setOnClickListener {
            Toast.makeText(context, "Basic View Clicked..", Toast.LENGTH_SHORT).show()
            clickEvent()
        }
    }
}