package com.duzi.gudicafeteria_a.ui.comment

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseActivity
import com.duzi.gudicafeteria_a.data.Comment
import com.duzi.gudicafeteria_a.service.ApiErrorResponse
import com.duzi.gudicafeteria_a.service.ApiSuccessResponse
import com.duzi.gudicafeteria_a.util.APP_TAG
import kotlinx.android.synthetic.main.activity_review_add.*

class CommentAddActivity : BaseActivity() {

    override val initView: () -> Unit = {}
    override val requestedPermissionList: List<String> = listOf()
    override val layoutResID: Int = R.layout.activity_review_add

    private lateinit var commentViewModel: CommentViewModel
    private var isModified: Boolean = false
    private var comment: Comment? = null
    private var userId: String? = null
    private var cafeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_black_24dp)

        cafeId = intent.getStringExtra(CAFE_ID)
        userId = intent.getStringExtra(USER_ID)
        comment = intent.getParcelableExtra(COMMENT) as Comment?
        comment?.let {
            isModified = true
            if(cafeId.isNullOrEmpty() && userId.isNullOrEmpty()) {
                cafeId = it.cafe_Id
                userId = it.user_Id
            }
            commentRatingBar.rating = it.comment_score.toFloat()
            edtComment.setText(it.comment)
        }

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_comment, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.confirm -> {
                commentAddDone()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeViewModel() {
        commentViewModel = getViewModel()
    }

    private fun commentAddDone() {
        if(cafeId.isNullOrEmpty() || userId.isNullOrEmpty())
            return

        comment = comment ?: Comment(cafeId!!, userId!!, edtComment.text.toString(), commentRatingBar.rating.toString())
        comment?.let { it ->
            val liveData = if(isModified) {
                commentViewModel.updateComment(it, edtComment.text.toString(), commentRatingBar.rating.toString())
            } else {
                commentViewModel.insertComment(it)
            }

            liveData.observe(this, Observer {
                response ->
                when(response) {
                    is ApiSuccessResponse<Int> -> { finish() }
                    is ApiErrorResponse<Int> -> {
                        Log.d(APP_TAG, "#Comment insert error  ${response.code} ${response.errorMessage}")
                        finish()
                    }
                }
            })
        }
    }

    companion object {
        private const val CAFE_ID = "CAFE_ID"
        private const val USER_ID = "USER_ID"
        private const val COMMENT = "COMMENT"
        fun open(context: Context, cafeId: String? = null, userId: String? = null, comment: Comment? = null) {
            val intent = Intent(context, CommentAddActivity::class.java)
            intent.putExtra(CAFE_ID, cafeId)
            intent.putExtra(USER_ID, userId)
            intent.putExtra(COMMENT, comment)
            context.startActivity(intent)
        }
    }
}
