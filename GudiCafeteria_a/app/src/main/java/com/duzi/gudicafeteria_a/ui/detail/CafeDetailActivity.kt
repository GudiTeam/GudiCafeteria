package com.duzi.gudicafeteria_a.ui.detail

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import kotlinx.android.synthetic.main.activity_cafe_detail.*
import kotlinx.android.synthetic.main.view_coordinatortablayout.*

class CafeDetailActivity : AppCompatActivity() , MenuFragment.OnFragmentInteractionListener, ReviewFragment.OnFragmentInteractionListener {

    private lateinit var cafe: Cafe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        cafe = intent.getParcelableExtra("cafe")
        cafeTitle.text = cafe.cafe_Nm

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_fragment)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.review_fragment)))

        val imageArray = intArrayOf(R.mipmap.bg_ios, R.mipmap.bg_js)
        val colorArray = intArrayOf(android.R.color.holo_blue_light, android.R.color.holo_green_light)

        viewpager.adapter = CafeDetailTabAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        coordinatortablayout.setTranslucentStatusBar(this)
                .setTitle("Demo")
                .setBackEnable(true)
                .setImageArray(imageArray, colorArray)
                .setupWithViewpager(viewpager)
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
