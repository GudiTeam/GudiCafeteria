package com.duzi.gudicafeteria_a.ui.detail

import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_cafe_detail.*

class CafeDetailActivity : AppCompatActivity() , MenuFragment.OnFragmentInteractionListener, ReviewFragment.OnFragmentInteractionListener {

    private lateinit var cafe: Cafe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        cafe = intent.getParcelableExtra("cafe")
        if(cafe.menu_L != null && cafe.menu_D != null)
            Toast.makeText(this,
                    "${cafe.build_Nm} ${cafe.cafe_Id} ${cafe.oper_Time} ${cafe.menu_L?.cafe_Id} ${cafe.menu_D?.cafe_Id}",
                    Toast.LENGTH_SHORT).show()


        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_fragment)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.review_fragment)))
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
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
