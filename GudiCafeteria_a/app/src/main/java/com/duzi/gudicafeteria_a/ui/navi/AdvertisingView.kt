package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.navi_advertising.view.*

class AdvertisingView(context: Context?, var clickEvent: () -> Unit): NaviBaseView(context) {
    override fun layoutRes(): Int = R.layout.navi_advertising
    override fun setupButtonsEvents() {
        advertising.setOnClickListener {
            Toast.makeText(context, "advertisement clicked", Toast.LENGTH_SHORT).show()
            clickEvent()
        }
    }
}