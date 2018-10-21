package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.navi_single_button.view.*

class ButtonView(context:Context?, title: String, var closeDrawer: () -> Unit) : NaviBaseView(context) {

    init {
        btnMenuSingleButton.apply {
            text = title
            setTextColor(Color.BLACK)
        }
    }

    override fun layoutRes(): Int = R.layout.navi_single_button

    override fun setupButtonsEvents() {
        btnMenuSingleButton.setOnClickListener {
            Toast.makeText(context, "Single Menu Button Clicked..", Toast.LENGTH_SHORT).show()
            closeDrawer()
        }
    }
}