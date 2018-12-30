package com.duzi.gudicafeteria_a.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.ui.cafe.CafeDetailActivity
import kotlinx.android.synthetic.main.fragment_map_item.*

class MapFragment: Fragment() {

    private var position: Int? = null
    private var cafe: Cafe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
            cafe = it.getParcelable("cafe")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_map_item, container, false)

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapCardView.setOnClickListener {
            CafeDetailActivity.open(context!!, cafe!!.cafe_Id)
        }

        distance.text = "500m"
        price.text = "${cafe?.price}원"
        rating.text = "4.5"
        title.text = cafe?.cafe_Nm
        operation.text = cafe?.oper_Time

        val lunch = cafe?.menu_L
        if(lunch != null) {
            val sb = StringBuilder()
                    .append("${cafe?.menu_L?.rice},")
                    .append("${cafe?.menu_L?.soup},")
                    .append("${cafe?.menu_L?.side_Dish1},")
                    .append("${cafe?.menu_L?.side_Dish2},")
                    .append("${cafe?.menu_L?.side_Dish3},")
                    .append("${cafe?.menu_L?.side_Dish4},")
                    .append("${cafe?.menu_L?.side_Dish5},")
                    .append("${cafe?.menu_L?.side_Dish6},")
                    .append("${cafe?.menu_L?.side_Dish7},")
                    .append("${cafe?.menu_L?.side_Dish8},")
                    .append("${cafe?.menu_L?.dessert1},")
                    .append("${cafe?.menu_L?.dessert2}")
            menu.text = sb.toString()
        } else {
            menu.text = "점심메뉴가없습니다."
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int, cafe: Cafe) =
                MapFragment().apply {
                    arguments = Bundle().apply {
                        putInt("position", position)
                        putParcelable("cafe", cafe)
                    }
                }
    }
}