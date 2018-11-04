package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.graphics.Color
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.navi_single_button.view.*

class ButtonView(context:Context?, title: String, color: String = "", var clickEvent: () -> Unit) : NaviBaseView(context) {

    init {
        btnMenuSingleButton.apply {
            text = title
            setTextColor(Color.BLACK)

            if(color.isNotEmpty())
                setBackgroundColor(Color.parseColor(color))
        }
    }

    override fun layoutRes(): Int = R.layout.navi_single_button

    override fun setupButtonsEvents() {
        btnMenuSingleButton.setOnClickListener {
            clickEvent()
        }
    }
}