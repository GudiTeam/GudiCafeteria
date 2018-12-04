package com.duzi.gudicafeteria_a.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseActivity
import com.duzi.gudicafeteria_a.ui.autopager.AutoPagerAdapter
import com.duzi.gudicafeteria_a.ui.cafe.CafeAdapter
import com.duzi.gudicafeteria_a.ui.cafe.CafeRepository
import com.duzi.gudicafeteria_a.ui.cafe.CafeViewModel
import com.duzi.gudicafeteria_a.ui.custom.filter.FilterBottomDialog
import com.duzi.gudicafeteria_a.ui.custom.filter.FilterListener
import com.duzi.gudicafeteria_a.ui.custom.recycler.PullLoadMoreRecyclerView
import com.duzi.gudicafeteria_a.ui.detail.CafeDetailActivity
import com.duzi.gudicafeteria_a.ui.map.MapActivity
import com.duzi.gudicafeteria_a.ui.notice.NoticeActivity
import com.duzi.gudicafeteria_a.util.GlideApp
import com.kakao.auth.*
import com.kakao.auth.network.response.AccessTokenInfoResponse
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.callback.UnLinkResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.navigation_view.*

class MainActivity : BaseActivity(), PullLoadMoreRecyclerView.PullLoadMoreListener, FilterListener {

    override val layoutResID = R.layout.activity_main
    override val requestedPermissionList: List<String> = listOf("android.permission.ACCESS_FINE_LOCATION")

    private val recyclerAdapter = CafeAdapter { position -> onClick(position) }
    private val compositeDisposable = CompositeDisposable()
    private lateinit var sessionCallback: ISessionCallback
    private val authType = AuthType.KAKAO_LOGIN_ALL

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initLayout()
        initMenu()
        observeViewModel()
        requestAccessTokenInfo()
        initSession()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()

        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data))
            return

        super.onActivityResult(requestCode, resultCode, data)
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
        Toast.makeText(this@MainActivity, "거리순 정렬", Toast.LENGTH_SHORT).show()
    }

    override fun sortBycreatedAt() {
        Toast.makeText(this@MainActivity, "생성순 정렬", Toast.LENGTH_SHORT).show()
    }

    override fun sortByStar() {
        Toast.makeText(this@MainActivity, "평점순 정렬", Toast.LENGTH_SHORT).show()
    }



    override fun onRefresh() {
        setRefresh()
        compositeDisposable.add(CafeRepository.getInstance().loadCafeList("20181023"))
    }

    override fun onLoadMore() {
        compositeDisposable.add(CafeRepository.getInstance().loadCafeList("20181023"))
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)

        drawerToggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                appBarCollapsed()
            } else {
                appBarExpanded()
            }
        })
    }

    private fun initLayout() {
        val imageIdList = arrayListOf(
                R.mipmap.food1, R.mipmap.food2,
                R.mipmap.food3, R.mipmap.food4,
                R.mipmap.food5, R.mipmap.food6,
                R.mipmap.food7)
        auto_view_pager.setOnItemClickListener { Toast.makeText(this,"Item $it clicked",Toast.LENGTH_SHORT).show() }
        auto_view_pager.setAdapter(AutoPagerAdapter(auto_view_pager, imageIdList))

        btnFilter.setOnClickListener {
            FilterBottomDialog.getInstance()
                    .setFilterListener(this)
                    .show(supportFragmentManager)
        }

        btnMap.setOnClickListener {
            startActivity(Intent(this@MainActivity, MapActivity::class.java))
        }

        pullLoadMoreRecyclerView.setRefreshing(true)
        pullLoadMoreRecyclerView.setLinearLayout()
        pullLoadMoreRecyclerView.setFooterViewText("로딩중입니다")
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this)
        pullLoadMoreRecyclerView.setAdapter(recyclerAdapter)
        pullLoadMoreRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun onClick(position: Int) {
        val intent = Intent(this@MainActivity, CafeDetailActivity::class.java)
        intent.putExtra("position", position)

        startActivity(intent)
    }

    private fun initMenu() {
        btnLogin.setOnClickListener {
            closeDrawer()
            requestLogin()
        }

        btnLogout.setOnClickListener {
            closeDrawer()
            requestLogout()
        }

        // 카카오 계정으로 로그인 상태
        if(Session.getCurrentSession().isOpened) {
            requestMe()
        }


        btnFavorite.setOnClickListener {
            closeDrawer()
        }

        btnReview.setOnClickListener {
            closeDrawer()
        }

        btnSettings.setOnClickListener {  // kakao unlink
            closeDrawer()
            requestUnLink()
        }

        btnBottomAdvertising.setOnClickListener {
            closeDrawer()
        }

        noticeView.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this@MainActivity, NoticeActivity::class.java))
        }

        adRequireView.setOnClickListener {
            closeDrawer()
        }

        developerView.setOnClickListener {
            closeDrawer()
        }
    }

    private fun initSession() {
        sessionCallback = object: ISessionCallback {
            // 로그인 실패 상태
            override fun onSessionOpenFailed(exception: KakaoException?) {
                Toast.makeText(this@MainActivity, "로그인 실패 ${exception?.errorType?.name}", Toast.LENGTH_SHORT).show()
            }

            // 로그인 성공 상태
            override fun onSessionOpened() {
                Toast.makeText(this@MainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                requestMe()
            }
        }
        Session.getCurrentSession().addCallback(sessionCallback)

        // 토근 만료시 갱신
        if(Session.getCurrentSession().isOpenable) {
            Session.getCurrentSession().checkAndImplicitOpen()
        }
    }

    private fun getViewModel(): CafeViewModel = ViewModelProviders.of(this).get(CafeViewModel::class.java)

    private fun observeViewModel() {
        getViewModel().getCafeList()
                .observe(this, Observer {
                    if (it != null) {
                        recyclerAdapter.addAllData(it)
                        pullLoadMoreRecyclerView.setPullLoadMoreCompleted()
                    }
                })

        compositeDisposable.add(CafeRepository.getInstance().loadCafeList("20181023"))
    }

    private fun setRefresh() {
        getViewModel().clearCache()
        recyclerAdapter.clearData()
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun requestMe() {
        val keys = arrayListOf("properties.nickname", "properties.profile_image", "properties.thumbnail_image")
        UserManagement.getInstance().me(keys, object: MeV2ResponseCallback() {
            override fun onSuccess(result: MeV2Response?) {
                Toast.makeText(this@MainActivity,
                        "#2 id:${result?.id}  가입여부:${result?.hasSignedUp()} nickname:${result?.nickname}",
                        Toast.LENGTH_SHORT).show()

                setLogin()
                userId.text = result?.nickname
                GlideApp.with(this@MainActivity)
                        .load(result?.profileImagePath)
                        .into(userImage)
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                // TODO Redirect Login.
                Toast.makeText(this@MainActivity,
                        "errorCode:${errorResult?.errorCode}",
                        Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun requestLogin() {
        Session.getCurrentSession().checkAndImplicitOpen()
        Session.getCurrentSession().open(authType, this@MainActivity)
    }

    private fun requestLogout() {
        UserManagement.getInstance().requestLogout(object: LogoutResponseCallback() {
            override fun onCompleteLogout() {
                runOnUiThread {
                    setLogout()

                    userId.text = getString(R.string.app_name)
                    GlideApp.with(this@MainActivity)
                            .load(R.mipmap.ic_launcher_round)
                            .into(userImage)

                }
            }
        })
    }

    private fun requestAccessTokenInfo() {
        AuthService.getInstance().requestAccessTokenInfo(object: ApiResponseCallback<AccessTokenInfoResponse>() {
            override fun onSuccess(result: AccessTokenInfoResponse?) {
                // 유저id와 토큰 만료기간 체크
                Toast.makeText(this@MainActivity, "userId:${result?.userId} expiresInMills:${result?.expiresInMillis}", Toast.LENGTH_SHORT).show()
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                // TODO redirect login
                Toast.makeText(this@MainActivity, "세션 종료된 상태", Toast.LENGTH_SHORT).show()
            }

            override fun onNotSignedUp() {
                // not happened
            }
        })
    }

    private fun requestUnLink() {
        val appendMessage = getString(R.string.com_kakao_confirm_unlink)
        AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button)) { dialog, _ ->
                    UserManagement.getInstance().requestUnlink(object: UnLinkResponseCallback() {
                        override fun onSuccess(result: Long?) {
                            setLogout()
                            Toast.makeText(this@MainActivity, "카카오 계정 UnLink!", Toast.LENGTH_SHORT).show()
                        }

                        override fun onSessionClosed(errorResult: ErrorResult?) {
                            Toast.makeText(this@MainActivity, "세션이 종료된 상태", Toast.LENGTH_SHORT).show()
                        }

                        override fun onNotSignedUp() {
                            Toast.makeText(this@MainActivity, "미가입 상태", Toast.LENGTH_SHORT).show()
                        }

                    })
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.com_kakao_cancel_button)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }

    private fun appBarCollapsed() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        collapsingtoolbarlayout.title = getString(R.string.app_name)
        collapsingtoolbarlayout.setContentScrimColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
    }

    private fun appBarExpanded() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        collapsingtoolbarlayout.title = " "
        collapsingtoolbarlayout.setContentScrimColor(ContextCompat.getColor(this@MainActivity, android.R.color.transparent))
    }

    private fun setLogin() {
        btnLogout.visibility = VISIBLE
        btnLogin.visibility = INVISIBLE
    }

    private fun setLogout() {
        btnLogout.visibility = INVISIBLE
        btnLogin.visibility = VISIBLE
    }
}
