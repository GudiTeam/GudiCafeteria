package com.duzi.gudicafeteria_a.ui.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Review
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : Fragment() {
    private lateinit var adapter: ReviewAdapter
    private lateinit var cafeDetailViewModel: CafeDetailViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_review, container, false)

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let { parent ->
            cafeDetailViewModel = ViewModelProviders.of(parent).get(CafeDetailViewModel::class.java)
            cafeDetailViewModel.getComments().observe(this, Observer { reviews  ->
                totalReviewCountText.text = "총 ${reviews!!.size}개의 리뷰가 있어요"
                adapter.addList(reviews)
            })
        }

        adapter = ReviewAdapter(context!!) { compositeDisposable += it}
        reviewRecyclerView.layoutManager = LinearLayoutManager(activity)
        reviewRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        private var INSTANCE: ReviewFragment? = null
        fun getInstance() = INSTANCE ?: synchronized(ReviewFragment::class.java) {
            INSTANCE ?: ReviewFragment().also { INSTANCE = it }
        }
    }
}
