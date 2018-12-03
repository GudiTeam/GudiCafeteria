package com.duzi.gudicafeteria_a.ui.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.ui.cafe.CafeViewModel
import com.duzi.gudicafeteria_a.data.Cafe
import kotlinx.android.synthetic.main.activity_cafe_detail.*
import kotlinx.android.synthetic.main.view_coordinatortablayout.*

class CafeDetailActivity : AppCompatActivity() , MenuFragment.OnMenuFragmentListener, ReviewFragment.OnReviewFragmentListener {

    private lateinit var cafe: Cafe

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        val position= intent.getIntExtra("position", -1)
        if(position == -1) {
            return
        }

        cafe = observeViewModel(position)

        // TODO CoordinatorLayout을 커스텀으로 만들었는데 헤더레이아웃은 내부에서 처리하는 방법을 생각해보자
        cafeTitle.text = cafe.cafe_Nm
        price.text = "${cafe.price}원"
        operation.text = cafe.oper_Time
        address.text = "${cafe.build_Addr}\n${cafe.build_Nm}"
        tel.text = cafe.build_Tel


        val imageArray = intArrayOf(R.mipmap.food2, R.mipmap.food5)
        val colorArray = intArrayOf(R.color.colorPrimary, R.color.colorPrimary)

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_fragment)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.review_fragment)))

        viewpager.adapter = CafeDetailTabAdapter(this, supportFragmentManager, position, tabLayout.tabCount)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        coordinatortablayout.setTranslucentStatusBar(this)
                .setTitle(cafe.cafe_Nm)
                .setBackEnable(true)
                .setImageArray(imageArray, colorArray)
                .setupWithViewpager(viewpager)

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                appBarCollapsed()
            } else {
                appBarExpanded()
            }
        })

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
    }

    override fun showWeeklyMenu(startDate: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReviewCreate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReviewModify() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReviewDelete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun observeViewModel(position: Int): Cafe {
        return ViewModelProviders.of(this)
                .get(CafeViewModel::class.java)
                .getCafeListCache()[position]
    }

    private fun appBarCollapsed() {
        collapsingtoolbarlayout.title = cafe.cafe_Nm
        collapsingtoolbarlayout.setContentScrimColor(ContextCompat.getColor(this@CafeDetailActivity, R.color.colorPrimary))
    }

    private fun appBarExpanded() {
        collapsingtoolbarlayout.title = " "
        collapsingtoolbarlayout.setContentScrimColor(ContextCompat.getColor(this@CafeDetailActivity, android.R.color.transparent))
    }
}
