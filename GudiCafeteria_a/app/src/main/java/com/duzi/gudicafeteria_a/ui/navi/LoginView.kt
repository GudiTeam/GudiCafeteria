package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.util.Utils.setPressedBackground
import kotlinx.android.synthetic.main.navi_login.view.*

class LoginView(context:Context?, private val title: String, private val color: String = "") : NaviBaseView(context) {

    init {
        btnLogin.apply {
            text = title
            setTextColor(Color.BLACK)

            if(color.isNotEmpty()) {
                setBackgroundColor(Color.parseColor(color))
                setPressedBackground(this, (background as ColorDrawable).color)
            }
        }

        btnLogout.apply {
            setTextColor(Color.BLACK)

            if(color.isNotEmpty()) {
                setBackgroundColor(Color.parseColor(color))
                setPressedBackground(this, (background as ColorDrawable).color)
            }
        }
    }

    override fun layoutRes(): Int = R.layout.navi_login

    override fun setupButtonsEvents() = Unit

    fun setLogin() {
        btnLogout.visibility = VISIBLE
        btnLogin.visibility = INVISIBLE
    }

    fun setLogout() {
        btnLogout.visibility = INVISIBLE
        btnLogin.visibility = VISIBLE
    }

    fun setLoginListener(event: () -> Unit) = btnLogin.setOnClickListener { event() }
    fun setLogoutListener(event: () -> Unit) = btnLogout.setOnClickListener { event() }
}