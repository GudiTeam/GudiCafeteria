package com.duzi.gudicafeteria_a.ui

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.navigation_menu.*

class MainActivity : BaseActivity() {

    override val layoutResID = R.layout.activity_main
    override val requestedPermissionList: List<String> = listOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE")
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    override val initView: () -> Unit = {
        // TODO 네트워크 작업
        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initLayout() {
        mainHamburger.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else
                drawerLayout.openDrawer(GravityCompat.START)
        }

        navi_menu_root.layoutManager = LinearLayoutManager(this)
    }
}
