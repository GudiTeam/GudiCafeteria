package com.duzi.gudicafeteria_a.ui.review

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.ReviewWithUser
import com.duzi.gudicafeteria_a.util.GlideApp
import kotlinx.android.synthetic.main.review_list_item.view.*

class ReviewAdapter(private val mContext: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<ReviewWithUser> by lazy { arrayListOf<ReviewWithUser>() }

    override fun onCreateViewHolder(parent: ViewGroup, holderType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.run {
            val reviewWithUser = list[position]
            holder.itemView.userId.text = reviewWithUser.user.user_Nm
            GlideApp.with(mContext)
                    .load(reviewWithUser.user.user_Img)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(holder.itemView.userImage)

            reviewRatingBar.rating = reviewWithUser.review.comment_score.toFloat()
            reviewContents.text = reviewWithUser.review.comment
            date.text = "이번주"
        }
    }

    fun addList(list: List<ReviewWithUser>) {
        this.list.clear()
        this.list.addAll(list)
    }

    companion object {
        class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    }
}