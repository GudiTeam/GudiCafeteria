package com.duzi.gudicafeteria_a.ui.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Review
import kotlinx.android.synthetic.main.review_list_item.view.*

class ReviewAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<Review> by lazy { arrayListOf<Review>() }

    override fun onCreateViewHolder(parent: ViewGroup, holderType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, parent, false))

    override fun getItemCount(): Int = 30

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(context)
                .load("http://cdnweb01.wikitree.co.kr/webdata/editor/201603/23/img_20160323162857_ecbc80a5.jpg")
                .into(holder.itemView.userImage)

        holder.itemView.userId.text = "맛있으면우는고양이"
        holder.itemView.reviewRatingBar.rating = 4.5f
        holder.itemView.date.text = "이번주"
        holder.itemView.reviewContents.text = "저번에 맛있게 먹었던 기억이 있어서 오랜만에 주문했습니다~ " +
                "주문한 음료 3개중에 하나가 뚜껑없이 와서 조금 아쉬웠어요 그래도 완전 맛있었음~"
    }

    fun addList(list: List<Review>) {
        this.list.addAll(list)
    }

    companion object {
        class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    }
}