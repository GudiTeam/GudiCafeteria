package com.duzi.gudicafeteria_a.ui.detail

import android.annotation.SuppressLint
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.util.Utils
import kotlinx.android.synthetic.main.activity_cafe_detail.*
import kotlinx.android.synthetic.main.view_coordinatortablayout.*

class CafeDetailActivity : AppCompatActivity() , MenuFragment.OnFragmentInteractionListener, ReviewFragment.OnFragmentInteractionListener {

    private lateinit var cafe: Cafe

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        cafe = intent.getParcelableExtra("cafe")

        // TODO CoordinatorLayout을 커스텀으로 만들었는데 헤더레이아웃은 내부에서 처리하는 방법을 생각해보자
        cafeTitle.text = cafe.cafe_Nm
        price.text = "${cafe.price}원"
        operation.text = cafe.oper_Time
        address.text = "${cafe.build_Addr}\n${cafe.build_Nm}"
        tel.text = cafe.build_Tel


        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_fragment)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.review_fragment)))

        val imageArray = intArrayOf(R.mipmap.bg_ios, R.mipmap.bg_js)
        val colorArray = intArrayOf(android.R.color.holo_blue_light, android.R.color.holo_green_light)

        viewpager.adapter = CafeDetailTabAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        coordinatortablayout.setTranslucentStatusBar(this)
                .setTitle(cafe.cafe_Nm)
                .setBackEnable(true)
                .setImageArray(imageArray, colorArray)
                .setupWithViewpager(viewpager)

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                collapsingtoolbarlayout.title = cafe.cafe_Nm
            } else {
                collapsingtoolbarlayout.title = ""
            }
        })
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
