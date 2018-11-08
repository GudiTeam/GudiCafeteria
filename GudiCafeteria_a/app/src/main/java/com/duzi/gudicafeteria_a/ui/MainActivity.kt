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
import com.duzi.gudicafeteria_a.cafe.CafeRepository
import com.duzi.gudicafeteria_a.cafe.CafeViewModel
import com.duzi.gudicafeteria_a.ui.custom.filter.FilterBottomDialog
import com.duzi.gudicafeteria_a.ui.custom.filter.FilterListener
import com.duzi.gudicafeteria_a.ui.custom.recycler.PullLoadMoreRecyclerView
import com.duzi.gudicafeteria_a.ui.navi.*
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.util.exception.KakaoException
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.navigation_menu.*

class MainActivity : BaseActivity(), PullLoadMoreRecyclerView.PullLoadMoreListener, FilterListener {

    override val layoutResID = R.layout.activity_main
    override val requestedPermissionList: List<String> = listOf("android.permission.ACCESS_FINE_LOCATION")

    private val recyclerAdapter = CafeAdapter()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var sessionCallback: ISessionCallback
    private val authType = AuthType.KAKAO_LOGIN_ALL

    private lateinit var loginView: LoginView
    private lateinit var mainMenuView: MainMenuView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        initMenu()
        observeViewModel()
        initSession()
    }

    override fun onDestroy() {
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onDestroy()
    }

    override val initView: () -> Unit = {
        // TODO 퍼미션 허가 후에 UI와 상관없는 네트워크 작업
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun sortByDistance() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortBycreatedAt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortByStar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            FilterBottomDialog().setFilterListener(this)
                    .show(supportFragmentManager)
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
        loginView = LoginView(this, "Kakao 로그인", "#FFF600").apply {
            setLoginListener {
                closeDrawer()
                Session.getCurrentSession().checkAndImplicitOpen()
                Session.getCurrentSession().open(authType, this@MainActivity)
            }

            setLogoutListener {
                UserManagement.getInstance().requestLogout(object: LogoutResponseCallback() {
                    override fun onCompleteLogout() {
                        loginView.setLogout()
                        //TODO 상단 프로필 내리기
                    }
                })
            }
        }
        if(Session.getCurrentSession().checkAndImplicitOpen()) loginView.setLogin() // 로그인 상태일 경우
        naviMenuRoot.addView(loginView)

        val imageButtonSet = listOf(
                ImageButtonSet(R.drawable.ic_heart, "찜하기"),
                ImageButtonSet(R.drawable.ic_chat, "리뷰관리"),
                ImageButtonSet(R.drawable.ic_settings, "환경설정")
        )

        mainMenuView = MainMenuView(this, imageButtonSet)
        mainMenuView.setBtn1ClickListener {  }
        mainMenuView.setBtn2ClickListener {  }
        mainMenuView.setBtn3ClickListener {  }
        naviMenuRoot.addView(mainMenuView)

        naviMenuRoot.addView(AdvertisingView(this) { closeDrawer() })
        naviMenuRoot.addView(BasicView(this, "공지사항"){ closeDrawer() })
        naviMenuRoot.addView(BasicView(this, "광고문의") { closeDrawer() })
    }

    private fun initSession() {
        sessionCallback = object: ISessionCallback {
            override fun onSessionOpenFailed(exception: KakaoException?) {
                Toast.makeText(this@MainActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onSessionOpened() {
                Toast.makeText(this@MainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                loginView.setLogin()
                // TODO 상단 프로필 올리기
            }
        }
        Session.getCurrentSession().addCallback(sessionCallback)
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

    private fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}
