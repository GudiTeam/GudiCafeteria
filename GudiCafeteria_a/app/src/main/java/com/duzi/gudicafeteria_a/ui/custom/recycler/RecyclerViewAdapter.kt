package com.duzi.gudicafeteria_a.ui.custom.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.layout_main_item.view.*

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = arrayListOf<DummyData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_main_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.itemView.title.text = item.title
        holder.itemView.distance.text = item.distance.toString() + " km"
        holder.itemView.address.text = item.address
        holder.itemView.star.text = item.star.toString()
        holder.itemView.review.text = item.review.toString()
    }

    override fun getItemCount(): Int = dataList.size

    fun addAllData(dataList: List<DummyData>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.dataList.clear()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.title
        val distance = view.distance
        val address = view.address
        val star = view.star
        val review = view.review
    }
}

data class DummyData(val title: String, val distance: Int, val address: String, val star: Double, val review: Int)