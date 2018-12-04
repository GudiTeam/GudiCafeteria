package com.duzi.gudicafeteria_a.ui.detail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.util.TAG
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : Fragment() {
    private var cafeId: Int? = null
    private var listener: OnReviewFragmentListener? = null
    private lateinit var adapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cafeId = it.getInt("cafeId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_review, container, false)


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //TODO 리뷰 뷰모델 구현

        totalReviewCountText.text = "총 30개의 리뷰가 있어요"

        adapter = ReviewAdapter(context!!)
        reviewRecyclerView.layoutManager = LinearLayoutManager(activity)
        reviewRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume")
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
