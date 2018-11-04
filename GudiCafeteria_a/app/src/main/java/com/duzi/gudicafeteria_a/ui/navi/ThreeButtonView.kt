package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.navi_three_button.view.*

class ThreeButtonView(context: Context?, imageButtonSet: List<ImageButtonSet>)
    : NaviBaseView(context) {

    init {
        val imageButtonList = listOf(imageButton1, imageButton2, imageButton3)
        val textList = listOf(text1, text2, text3)
        imageButtonSet.forEachIndexed { index, imageButtonSet ->
            imageButtonList[index].setBackgroundResource(imageButtonSet.resourceId)
            textList[index].text = imageButtonSet.title
        }
    }

    override fun layoutRes(): Int = R.layout.navi_three_button
    override fun setupButtonsEvents() = Unit

    fun setBtn1ClickListener(event: () -> Unit) {
        Btn1.setOnClickListener { event() }
    }
    fun setBtn2ClickListener(event: () -> Unit) {
        Btn2.setOnClickListener { event() }
    }
    fun setBtn3ClickListener(event: () -> Unit) {
        Btn3.setOnClickListener { event() }
    }
}

data class ImageButtonSet(var resourceId: Int, var title: String)