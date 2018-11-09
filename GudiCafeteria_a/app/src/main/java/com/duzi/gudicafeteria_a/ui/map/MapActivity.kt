package com.duzi.gudicafeteria_a.ui.map

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.cafe.CafeViewModel
import kotlinx.android.synthetic.main.activity_map.*
import net.daum.mf.map.api.MapView

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        initLayout()
        observeViewModel()
    }

    private fun initLayout() {
        val mapView = MapView(this)
        map_view.addView(mapView)

        // TODO bottom sheep 가로 recycler 또는 가로 pager 구현
    }

    private fun observeViewModel() {
        val cafeList = ViewModelProviders.of(this)
                .get(CafeViewModel::class.java)
                .getCafeListCache()

        // TODO 마커찍기
        Toast.makeText(this, "size : ${cafeList.size}, ${cafeList.get(0).cafe_Nm}", Toast.LENGTH_SHORT).show()
    }
}
