package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.navi_three_button.view.*

class ThreeButtonView(context: Context?, imageButtonSet: List<ImageButtonSet>, var closeDrawer: () -> Unit): NaviBaseView(context) {

    init {
        val imageButtonList = listOf(imageButton1, imageButton2, imageButton3)
        val textList = listOf(text1, text2, text3)
        imageButtonSet.forEachIndexed { index, imageButtonSet ->
            imageButtonList[index].setBackgroundResource(imageButtonSet.resourceId)
            textList[index].text = imageButtonSet.title
        }
    }

    override fun layoutRes(): Int = R.layout.navi_three_button
    override fun setupButtonsEvents() {
        Btn1.setOnClickListener {
            Toast.makeText(context, "Btn1 clicked", Toast.LENGTH_SHORT).show()
            closeDrawer()
        }
        Btn2.setOnClickListener {
            Toast.makeText(context, "Btn2 clicked", Toast.LENGTH_SHORT).show()
            closeDrawer()
        }
        Btn3.setOnClickListener {
            Toast.makeText(context, "Btn3 clicked", Toast.LENGTH_SHORT).show()
            closeDrawer()
        }
    }
}

data class ImageButtonSet(var resourceId: Int, var title: String)