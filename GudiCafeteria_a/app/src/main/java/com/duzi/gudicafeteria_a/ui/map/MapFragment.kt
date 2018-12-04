package com.duzi.gudicafeteria_a.ui.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe

class MapFragment: Fragment() {

    private var cafe: Cafe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cafe = it.getParcelable("cafe")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_map_item, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    companion object {
        @JvmStatic
        fun newInstance(cafe: Cafe) =
                MapFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("cafe", cafe)
                    }
                }
    }
}