package com.duzi.gudicafeteria_a.ui.detail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R

class ReviewFragment : Fragment() {
    private var cafeId: Int? = null
    private var listener: OnReviewFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cafeId = it.getInt("cafeId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_review, container, false)
        initView(view)
        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnReviewFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initView(view: View) {

    }

    interface OnReviewFragmentListener {
        fun onReviewCreate()
        fun onReviewModify()
        fun onReviewDelete()
    }

    companion object {
        @JvmStatic
        fun newInstance(cafeId: Int) =
                ReviewFragment().apply {
                    arguments = Bundle().apply {
                        putInt("cafeId", cafeId)
                    }
                }
    }
}
