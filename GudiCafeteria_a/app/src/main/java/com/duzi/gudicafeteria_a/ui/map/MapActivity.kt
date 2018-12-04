package com.duzi.gudicafeteria_a.ui.map

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.ui.cafe.CafeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        initLayout()
        observeViewModel()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun initLayout() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewPagerInfo.setPadding(16, 0, 16, 0)
        viewPagerInfo.clipToPadding = false  // padding과 clipToPadding false을 주면 다음 페이지가 조금 보임
        viewPagerInfo.pageMargin = 8


    }

    private fun observeViewModel() {
        // TODO cache에 값이 없으면 네트워크에서 받아오기
        val cafeList = ViewModelProviders.of(this)
                .get(CafeViewModel::class.java)
                .getCafeListCache()

        // TODO 마커찍기
        Toast.makeText(this, "size : ${cafeList.size}, ${cafeList.get(0).cafe_Nm}", Toast.LENGTH_SHORT).show()
    }
}
