package com.duzi.gudicafeteria_a.ui.detail

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.cafe.CafeViewModel
import com.duzi.gudicafeteria_a.data.Cafe
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {
    private lateinit var cafe: Cafe
    private var cafeId: Int? = null
    private var listener: OnMenuFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cafeId = it.getInt("cafeId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_menu, container, false)

    override fun onResume() {
        super.onResume()
        cafe = observeViewModel(cafeId!!)

        if(cafe.menu_L == null || cafe.menu_D == null) {
            showNothing()
            return
        }

        date.text = cafe.menu_L?.menu_Date
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMenuFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun showNothing() {
        nothing.visibility = VISIBLE
        menuLayout.visibility = INVISIBLE
    }

    private fun showMenu() {
        nothing.visibility = INVISIBLE
        menuLayout.visibility = VISIBLE
    }

    private fun observeViewModel(position: Int): Cafe {
        return ViewModelProviders.of(this)
                .get(CafeViewModel::class.java)
                .getCafeListCache()[position]
    }

    interface OnMenuFragmentListener {
        fun showWeeklyMenu(startDate: String)
    }

    companion object {
        @JvmStatic
        fun newInstance(cafeId: Int) =
                MenuFragment().apply {
                    arguments = Bundle().apply {
                        putInt("cafeId", cafeId)
                    }
                }
    }
}
