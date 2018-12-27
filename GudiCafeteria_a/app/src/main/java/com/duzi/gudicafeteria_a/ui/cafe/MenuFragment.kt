package com.duzi.gudicafeteria_a.ui.cafe

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseFragment
import com.duzi.gudicafeteria_a.data.Cafe
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : BaseFragment() {

    private lateinit var cafeViewModel: CafeViewModel

    override val layoutId: Int = R.layout.fragment_menu

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()

        weeklyMenu.setOnClickListener {
            cafeViewModel.reqeustWeeklyMenus(WeeklyMenusQuery("CAFE002", 20181020, 20181024))
        }

        cafeViewModel.getWeeklyMenus().observe(this, Observer {
            Toast.makeText(activity, "${it!![0].menu_Date} ${it[3].menu_Date}", Toast.LENGTH_SHORT).show()
        })
    }

    private fun observeViewModel() {
        cafeViewModel = getViewModel()
        cafeViewModel.getCafe().observe(this, Observer {
            cafe -> display(cafe!!)
        })
    }

    private fun display(cafe: Cafe) {
        if(cafe.menu_L == null || cafe.menu_D == null) {
            showNothing()
            return
        }

        date.text = cafe.menu_L.menu_Date
    }

    private fun showNothing() {
        nothing.visibility = VISIBLE
        menuLayout.visibility = INVISIBLE
    }

    private fun showMenu() {
        nothing.visibility = INVISIBLE
        menuLayout.visibility = VISIBLE
    }

    companion object {
        private var INSTANCE: MenuFragment? = null
        fun getInstance() = INSTANCE ?: synchronized(MenuFragment::class.java) {
            INSTANCE ?: MenuFragment().also { INSTANCE = it }
        }
    }
}
