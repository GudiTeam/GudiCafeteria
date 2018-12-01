package com.duzi.gudicafeteria_a.ui.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Review

class ReviewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<Review> by lazy { arrayListOf<Review>() }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

    }

    fun addList(list: List<Review>) {
        this.list.addAll(list)
    }

    companion object {
        class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    }
}