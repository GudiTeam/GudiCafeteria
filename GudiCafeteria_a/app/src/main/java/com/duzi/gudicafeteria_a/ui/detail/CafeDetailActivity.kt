package com.duzi.gudicafeteria_a.ui.detail

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.TextView
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import kotlinx.android.synthetic.main.activity_cafe_detail.*

class CafeDetailActivity : AppCompatActivity() , MenuFragment.OnFragmentInteractionListener, ReviewFragment.OnFragmentInteractionListener {

    private lateinit var cafe: Cafe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        cafe = intent.getParcelableExtra("cafe")
        cafeTitle.text = cafe.cafe_Nm

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_fragment)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.review_fragment)))

        viewpager.adapter = CafeDetailTabAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                viewpager.currentItem = position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        for(i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            val relativeLayout = LayoutInflater.from(this).inflate(R.layout.tab_layout, tabLayout, false)
            val tabTextView = relativeLayout.findViewById<TextView>(R.id.tab_title)
            tabTextView.text = tab!!.text
            tab.customView = relativeLayout
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
