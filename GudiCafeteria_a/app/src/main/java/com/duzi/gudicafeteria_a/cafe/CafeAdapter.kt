package com.duzi.gudicafeteria_a.cafe

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import kotlinx.android.synthetic.main.layout_main_item.view.*

class CafeAdapter(private val listener: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = arrayListOf<Cafe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_main_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.itemView.title.text = item.cafe_Nm
        holder.itemView.distance.text = "22 km"
        holder.itemView.address.text = item.build_Addr
        holder.itemView.price.text = "${item.price}원"
        holder.itemView.operation.text = item.oper_Time
        holder.itemView.star.text = "5"
        holder.itemView.review.text = "6"

        holder.itemView.setOnClickListener {
            listener.invoke(position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun addAllData(dataList: List<Cafe>) {
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
        val price = view.price
        val operation = view.operation
        val star = view.star
        val review = view.review
    }
}