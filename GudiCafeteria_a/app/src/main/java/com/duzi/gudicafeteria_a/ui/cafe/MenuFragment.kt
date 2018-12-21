package com.duzi.gudicafeteria_a.ui.cafe

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private lateinit var cafeViewModel: CafeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_menu, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            cafeViewModel = ViewModelProviders.of(it).get(CafeViewModel::class.java)
            cafeViewModel.getCafe().observe(this, Observer {
                cafe -> display(cafe!!)
            })
        }

        weeklyMenu.setOnClickListener {
            cafeViewModel.reqeustWeeklyMenus(WeeklyMenusQuery("CAFE002", 20181020, 20181024))
        }

        cafeViewModel.getWeeklyMenus().observe(this, Observer {
            Toast.makeText(activity, "${it!![0].menu_Date} ${it[3].menu_Date}", Toast.LENGTH_SHORT).show()
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
        fun getInstance() = INSTANCE
                ?: synchronized(MenuFragment::class.java) {
            INSTANCE
                    ?: MenuFragment().also { INSTANCE = it }
        }
    }
}
