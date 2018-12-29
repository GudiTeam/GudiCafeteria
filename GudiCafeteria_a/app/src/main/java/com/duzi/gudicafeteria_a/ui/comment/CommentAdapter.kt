package com.duzi.gudicafeteria_a.ui.comment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Comment
import kotlinx.android.synthetic.main.review_list_item.view.*

class CommentAdapter(private val userCallback: (Comment, ViewHolder) -> Unit,
                     private val clickListener: (Comment) -> Unit,
                     private val longClickListener: (Comment) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<Comment> by lazy { arrayListOf<Comment>() }

    override fun onCreateViewHolder(parent: ViewGroup, holderType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.run {
            val comment = list[position]

            // FIXME 여기서 ViewModel과 LifecycleOwner를 받아서 처리를 해야하는건지 Activity로 holder 정보를 보내서 처리해야하는건지 잘모르겠음...
            userCallback(comment, holder as ViewHolder)

            setOnClickListener { clickListener(comment) }
            setOnLongClickListener {
                longClickListener(comment)
                true // true : 이벤트 완료   false : 다음 이벤트 진행
            }

            reviewRatingBar.rating = comment.comment_score.toFloat()
            reviewContents.text = comment.comment
            date.text = "이번주"
        }
    }

    fun addList(list: List<Comment>) {
        this.list.clear()
        this.list.addAll(list)
    }

    fun deleteComment(comment: Comment) {
        list.remove(comment)
        notifyDataSetChanged()
    }

    companion object {
        class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    }
}