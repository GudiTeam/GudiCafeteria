package com.duzi.gudicafeteria_a.ui.cafe

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseActivity
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.util.GlideApp
import kotlinx.android.synthetic.main.activity_cafe_detail.*
import kotlinx.android.synthetic.main.view_coordinatortablayout.*

class CafeDetailActivity : BaseActivity() {

    override val initView: () -> Unit = {}
    override val requestedPermissionList: List<String> = listOf()
    override val layoutResID: Int = R.layout.activity_cafe_detail

    private lateinit var cafeViewModel: CafeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cafeId= intent.getStringExtra(CAFE_ID) ?: "-1"

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_fragment)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.review_fragment)))

        viewpager.adapter = CafeDetailTabAdapter(supportFragmentManager, tabLayout.tabCount, cafeId)
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

        cafeViewModel = getViewModel()
        cafeViewModel.setCafeId(cafeId)
        cafeViewModel.getCafe().observe(this, Observer {
            cafe -> displayCafeInfo(cafe!!)
        })
    }

    @SuppressLint("SetTextI18n", "NewApi")
    private fun displayCafeInfo(cafe: Cafe) {
        cafeTitle.text = cafe.cafe_Nm
        mainRatingBar.rating = cafe.build_Score.toFloat() //FIXME 리뷰를 작성해도 카페 리스트를 다시 받지않으면 동기화가 안되는 이슈
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

    companion object {
        private const val CAFE_ID = "CAFE_ID"
        fun open(context: Context, cafeId: String) {
            val intent = Intent(context, CafeDetailActivity::class.java)
            intent.putExtra(CAFE_ID, cafeId)
            context.startActivity(intent)
        }

    }
}
