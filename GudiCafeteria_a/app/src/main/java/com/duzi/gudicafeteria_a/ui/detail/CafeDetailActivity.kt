package com.duzi.gudicafeteria_a.ui.detail

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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


        val pages = FragmentPagerItems.with(this)
                .add(R.string.menu_fragment, MenuFragment::class.java)
                .add(R.string.review_fragment, ReviewFragment::class.java)
                .create()

        val adapter = FragmentPagerItemAdapter(supportFragmentManager, pages)
        viewpager.adapter = adapter
        viewpagerTab.setViewPager(viewpager)
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
