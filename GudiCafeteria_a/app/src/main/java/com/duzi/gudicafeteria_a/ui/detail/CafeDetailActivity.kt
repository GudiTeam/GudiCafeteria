package com.duzi.gudicafeteria_a.ui.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.ui.cafe.CafeViewModel
import com.duzi.gudicafeteria_a.util.GlideApp
import kotlinx.android.synthetic.main.activity_cafe_detail.*
import kotlinx.android.synthetic.main.view_coordinatortablayout.*

class CafeDetailActivity : AppCompatActivity() {

    private lateinit var cafeViewModel: CafeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        val cafeId= intent.extras.getString("CAFE_ID")?: "-1"

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_fragment)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.review_fragment)))

        viewpager.adapter = CafeDetailTabAdapter(supportFragmentManager, tabLayout.tabCount)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        coordinatortablayout.setTranslucentStatusBar(this)
                .setBackEnable(true)
                .setupWithViewpager(viewpager)

        // TODO 좋아요 뷰모델 구현
        star.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this@CafeDetailActivity,
                    R.anim.anim_heart_plus)
            animation.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    // nothing
                }

                override fun onAnimationEnd(p0: Animation?) {
                    star_anim.visibility = INVISIBLE
                }

                override fun onAnimationStart(p0: Animation?) {
                    star_anim.visibility = VISIBLE
                }
            })
            star_anim.startAnimation(animation)

            //TODO 서버에서 좋아요 리스트 받아오거나 로컬에서 좋아요 리스트 받아서 분기처리
            //TODO 좋아요 API CALL
        }

        share.setOnClickListener {
            Toast.makeText(this@CafeDetailActivity, "공유하기", Toast.LENGTH_SHORT).show()
        }

        cafeViewModel = ViewModelProviders.of(this).get(CafeViewModel::class.java)
        cafeViewModel.setCafeId(cafeId)
        cafeViewModel.getCafe().observe(this, Observer {
            cafe -> displayCafeInfo(cafe!!)
        })
    }

    @SuppressLint("SetTextI18n", "NewApi")
    private fun displayCafeInfo(cafe: Cafe) {
        cafeTitle.text = cafe.cafe_Nm
        price.text = "${cafe.price}원"
        operation.text = cafe.oper_Time
        address.text = "${cafe.build_Addr}\n${cafe.build_Nm}"
        tel.text = cafe.build_Tel
        GlideApp.with(this)
                .load(cafe.cafe_img_Dir)
                .into(imageview)


        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                appBarCollapsed(cafe.cafe_Nm)
            } else {
                appBarExpanded()
            }
        })
    }

    private fun appBarCollapsed(title: String) {
        collapsingtoolbarlayout.title = title
        collapsingtoolbarlayout.setContentScrimColor(ContextCompat.getColor(this@CafeDetailActivity, R.color.colorPrimary))
    }

    private fun appBarExpanded() {
        collapsingtoolbarlayout.title = " "
        collapsingtoolbarlayout.setContentScrimColor(ContextCompat.getColor(this@CafeDetailActivity, android.R.color.transparent))
    }
}
