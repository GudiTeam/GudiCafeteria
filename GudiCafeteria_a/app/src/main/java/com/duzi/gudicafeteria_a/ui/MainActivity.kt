package com.duzi.gudicafeteria_a.ui

import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseActivity
import com.duzi.gudicafeteria_a.ui.custom.recycler.DummyData
import com.duzi.gudicafeteria_a.ui.custom.recycler.PullLoadMoreRecyclerView
import com.duzi.gudicafeteria_a.ui.custom.recycler.RecyclerViewAdapter
import com.duzi.gudicafeteria_a.ui.navi.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_image_text_button_left.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.navigation_menu.*

class MainActivity : BaseActivity(), PullLoadMoreRecyclerView.PullLoadMoreListener {

    override val layoutResID = R.layout.activity_main
    override val requestedPermissionList: List<String> = listOf("android.permission.ACCESS_FINE_LOCATION")

    private val recyclerAdapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        initMenu()
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

    override fun onRefresh() {
        loadData()
    }

    override fun onLoadMore() {
        loadData()
    }

    private fun initLayout() {
        mainHamburger.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else
                drawerLayout.openDrawer(GravityCompat.START)
        }

        btnFilter.setOnClickListener {
            Toast.makeText(this, "Filter Button clicked", Toast.LENGTH_SHORT).show()
        }
        btnMap.setOnClickListener {
            Toast.makeText(this, "Map Button clicked", Toast.LENGTH_SHORT).show()
        }

        pullLoadMoreRecyclerView.setRefreshing(true)
        pullLoadMoreRecyclerView.setLinearLayout()
        pullLoadMoreRecyclerView.setFooterViewText("로딩중입니다")
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this)
        pullLoadMoreRecyclerView.setAdapter(recyclerAdapter)
        loadData()
    }

    private fun initMenu() {
        naviMenuRoot.addView(ButtonView(this, "로그인") {
            drawerLayout.closeDrawer(GravityCompat.START)
        })
        naviMenuRoot.addView(ThreeButtonView(this,
                listOf(ImageButtonSet(R.drawable.ic_heart_64_wb, "찜하기"),
                        ImageButtonSet(R.drawable.ic_chat_64_wb, "리뷰관리"),
                        ImageButtonSet(R.drawable.ic_settings_64_wb, "환경설정"))) {
            drawerLayout.closeDrawer(GravityCompat.START)
        })
        naviMenuRoot.addView(AdvertisingView(this) {
            drawerLayout.closeDrawer(GravityCompat.START)
        })
        naviMenuRoot.addView(BasicView(this, "공지사항") {
            drawerLayout.closeDrawer(GravityCompat.START)
        })
        naviMenuRoot.addView(BasicView(this, "광고문의") {
            drawerLayout.closeDrawer(GravityCompat.START)
        })
    }

    private fun loadData() {
        Handler().postDelayed({
            recyclerAdapter.addAllData(createDummy())
            pullLoadMoreRecyclerView.setPullLoadMoreCompleted()
        }, 1000)
    }

    private fun createDummy(): List<DummyData> {
        val dummyList = arrayListOf<DummyData>()
        for(i in 1..20) {
            val dummy = DummyData("title $i", i, "address $i", i.toDouble(), i)
            dummyList.add(dummy)
        }
        return dummyList
    }
}
