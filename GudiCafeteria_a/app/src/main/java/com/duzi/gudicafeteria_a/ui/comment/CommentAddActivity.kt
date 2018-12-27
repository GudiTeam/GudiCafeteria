package com.duzi.gudicafeteria_a.ui.comment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.base.BaseActivity
import com.duzi.gudicafeteria_a.data.Comment

class CommentAddActivity : BaseActivity() {

    override val initView: () -> Unit = {}
    override val requestedPermissionList: List<String> = listOf()
    override val layoutResID: Int = R.layout.activity_review_add

    private lateinit var commentViewModel: CommentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.getParcelableExtra<Comment>(COMMENT)
        if(bundle != null) {

        }

        observeViewModel()
    }

    private fun observeViewModel() {
        commentViewModel = getViewModel()
    }

    companion object {
        private const val COMMENT = "COMMENT"
        fun open(context: Context, comment: Comment) {
            val intent = Intent(context, CommentAddActivity::class.java)
            intent.putExtra(COMMENT, comment)
            context.startActivity(intent)
        }
    }
}
