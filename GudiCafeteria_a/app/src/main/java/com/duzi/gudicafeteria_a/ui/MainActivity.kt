package com.duzi.gudicafeteria_a.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.widget.DividerItemDecoration
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseActivity
import com.duzi.gudicafeteria_a.cafe.CafeAdapter
import com.duzi.gudicafeteria_a.cafe.CafeViewModel
import com.duzi.gudicafeteria_a.cafe.source.CafeRepository
import com.duzi.gudicafeteria_a.ui.custom.recycler.PullLoadMoreRecyclerView
import com.duzi.gudicafeteria_a.ui.navi.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.navigation_menu.*

class MainActivity : BaseActivity(), PullLoadMoreRecyclerView.PullLoadMoreListener {

    override val layoutResID = R.layout.activity_main
    override val requestedPermissionList: List<String> = listOf("android.permission.ACCESS_FINE_LOCATION")

    private val recyclerAdapter = CafeAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        initMenu()
        observeViewModel()
    }

    override fun onDestroy() {
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onDestroy()
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
        setRefresh()
        compositeDisposable.add(CafeRepository.getInstance().loadCafeList())
    }

    override fun onLoadMore() {
        compositeDisposable.add(CafeRepository.getInstance().loadCafeList())
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
        pullLoadMoreRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun initMenu() {
        naviMenuRoot.addView(ButtonView(this, "로그인") {
            drawerLayout.closeDrawer(GravityCompat.START)
        })
        naviMenuRoot.addView(ThreeButtonView(this,
                listOf(ImageButtonSet(R.drawable.ic_heart, "찜하기"),
                        ImageButtonSet(R.drawable.ic_chat, "리뷰관리"),
                        ImageButtonSet(R.drawable.ic_settings, "환경설정"))) {
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

    private fun observeViewModel() {
        ViewModelProviders.of(this).get(CafeViewModel::class.java).getCafeList()
                .observe(this, Observer {
                    if (it != null) {
                        recyclerAdapter.addAllData(it)
                        pullLoadMoreRecyclerView.setPullLoadMoreCompleted()
                    }
                })

        compositeDisposable.add(CafeRepository.getInstance().loadCafeList())
    }

    private fun setRefresh() {
        recyclerAdapter.clearData()
    }
}
