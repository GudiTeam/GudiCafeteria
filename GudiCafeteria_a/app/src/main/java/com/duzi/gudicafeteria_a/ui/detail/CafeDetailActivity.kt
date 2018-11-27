package com.duzi.gudicafeteria_a.ui.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.cafe.CafeViewModel
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


        val imageArray = intArrayOf(R.mipmap.bg_ios, R.mipmap.bg_js)
        val colorArray = intArrayOf(android.R.color.holo_blue_light, android.R.color.holo_green_light)

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
                collapsingtoolbarlayout.title = cafe.cafe_Nm
            } else {
                collapsingtoolbarlayout.title = " "
            }
        })

        star.setOnClickListener {
            Toast.makeText(this@CafeDetailActivity, "찜하기", Toast.LENGTH_SHORT).show()
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
}
