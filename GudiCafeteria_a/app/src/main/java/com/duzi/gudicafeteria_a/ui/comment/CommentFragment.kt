package com.duzi.gudicafeteria_a.ui.comment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.ui.cafe.CafeViewModel
import kotlinx.android.synthetic.main.fragment_review.*

class CommentFragment : Fragment() {
    private lateinit var adapter: CommentAdapter
    private lateinit var cafeViewModel: CafeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_review, container, false)

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let { parent ->
            cafeViewModel = ViewModelProviders.of(parent).get(CafeViewModel::class.java)
            cafeViewModel.getCommentsWithUser().observe(this, Observer { reviews ->
                totalReviewCountText.text = "총 ${reviews!!.size}개의 리뷰가 있어요"
                adapter.addList(reviews)
            })
        }

        adapter = CommentAdapter(context!!)
        reviewRecyclerView.layoutManager = LinearLayoutManager(activity)
        reviewRecyclerView.adapter = adapter

        review_write.setOnClickListener {
            startActivity(Intent(context, CommentAddActivity::class.java))
        }
    }

    companion object {
        private var INSTANCE: CommentFragment? = null
        fun getInstance() = INSTANCE
                ?: synchronized(CommentFragment::class.java) {
            INSTANCE
                    ?: CommentFragment().also { INSTANCE = it }
        }
    }
}
