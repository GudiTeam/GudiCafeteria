package com.duzi.gudicafeteria_a.ui.comment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseFragment
import com.duzi.gudicafeteria_a.data.Comment
import com.duzi.gudicafeteria_a.service.ApiErrorResponse
import com.duzi.gudicafeteria_a.service.ApiSuccessResponse
import com.duzi.gudicafeteria_a.ui.user.UserInstance
import com.duzi.gudicafeteria_a.ui.user.UserViewModel
import com.duzi.gudicafeteria_a.util.APP_TAG
import com.duzi.gudicafeteria_a.util.GlideApp
import com.duzi.gudicafeteria_a.util.Utils
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.android.synthetic.main.review_list_item.view.*

class CommentFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_review

    private lateinit var adapter: CommentAdapter
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var userViewModel: UserViewModel

    private var cafeId: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        if(bundle != null) {
            cafeId = bundle.getString(CAFE_ID) ?: "-1"
        }


        adapter = CommentAdapter(userCallback =  { comment, holder ->
            userViewModel.getUserById(comment.user_Id).observe(this, Observer { user ->
                Log.d(APP_TAG, "${user?.user_Nm} ${comment.comment}")
                holder.itemView.userId.text = user?.user_Nm
                GlideApp.with(this)
                        .load(user?.user_Img)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(holder.itemView.userImage)
            })

        }, clickListener = { comment ->
            UserInstance.getUserId()?.let {
                if(comment.user_Id == it) {
                    CommentAddActivity.open(context!!, comment = comment)
                }
            }

        }, longClickListener = { comment ->
            UserInstance.getUserId()?.let {
                if(comment.user_Id == it) {
                    Utils.showDialog(context!!, "정말로 삭제하시겠습니까?", positive = {
                        commentDeleteObserve(comment)
                    }, negative = {
                        // nothing
                    })
                }
            }
        })


        reviewRecyclerView.layoutManager = LinearLayoutManager(activity)
        reviewRecyclerView.adapter = adapter

        commentViewModel = getViewModel()
        userViewModel = getViewModel()
        commentsObserve()

        review_write.setOnClickListener {
            UserInstance.getUserId()?.let { userId ->
                CommentAddActivity.open(context!!, cafeId = cafeId, userId = userId, comment = null)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun commentsObserve() {
        commentViewModel.getCommentsById(cafeId).observe(this, Observer { reviews ->
            totalReviewCountText.text = "총 ${reviews!!.size}개의 리뷰가 있어요"
            adapter.addList(reviews)
        })
    }

    private fun commentDeleteObserve(comment: Comment) {
        commentViewModel.deleteComment(comment).observe(this, Observer { response ->
            when(response) {
                is ApiSuccessResponse<Int> -> { adapter.deleteComment(comment) }
                is ApiErrorResponse<Int> -> {
                    Log.d(APP_TAG, "#comment ${comment.seq} 삭제취소")
                    Log.d(APP_TAG, "#Error : ${response.code} ${response.errorMessage}")
                }
            }
        })
    }

    companion object {
        private const val CAFE_ID = "CAFE_ID"
        private var INSTANCE: CommentFragment? = null
        fun getInstance() = {
            INSTANCE ?: synchronized(CommentFragment::class.java) {
                INSTANCE ?: CommentFragment().also { INSTANCE = it }
            }
        }

        fun newInstance(cafeId: String): CommentFragment {
            val fragment = CommentFragment()
            val bundle = Bundle()
            bundle.putString(CAFE_ID, cafeId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
