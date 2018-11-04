package com.duzi.gudicafeteria_a.ui.navi

import android.content.Context
import android.graphics.Color
import android.view.View
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.navi_login.view.*

class LoginView(context:Context?, title: String, color: String = "") : NaviBaseView(context) {

    init {
        btnLogin.apply {
            text = title
            setTextColor(Color.BLACK)

            if(color.isNotEmpty())
                setBackgroundColor(Color.parseColor(color))
        }

        btnLogout.apply {
            setTextColor(Color.BLACK)

            if(color.isNotEmpty())
                setBackgroundColor(Color.parseColor(color))
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