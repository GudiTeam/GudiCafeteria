package com.duzi.gudicafeteria_a.ui.cafe

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Cafe
import com.duzi.gudicafeteria_a.data.Favorite
import kotlinx.android.synthetic.main.layout_main_item.view.*
import java.util.*

class CafeAdapter(private val listener: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = arrayListOf<Cafe>()
    private var cachedFavorites = HashMap<String, Int>()

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

        when(cachedFavorites[item.cafe_Id]) {
            IS_FAVORITE -> {
                holder.itemView.ivFavorite.visibility = VISIBLE
            }
            IS_NOT_FAVORITE -> {
                holder.itemView.ivFavorite.visibility = GONE
            }
        }

        holder.itemView.setOnClickListener {
            listener.invoke(item.cafe_Id)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun addAllData(cafes: MutableList<Cafe>, favorites: List<Favorite>) {
        for(cafe in cafes) {
            cachedFavorites[cafe.cafe_Id] = IS_NOT_FAVORITE
        }

        for(favorite in favorites) {
            cachedFavorites[favorite.cafe_Id] = IS_FAVORITE
        }

        cafes.sortWith(Comparator { cafe1, cafe2 ->
            cachedFavorites[cafe2.cafe_Id]!!.compareTo(cachedFavorites[cafe1.cafe_Id]!!)
        })

        dataList.addAll(cafes)
        notifyDataSetChanged()
    }

    fun addAllData(cafes: List<Cafe>) {
        for(cafe in cafes) {
            cachedFavorites[cafe.cafe_Id] = IS_NOT_FAVORITE
        }

        dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.dataList.clear()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    companion object {
        const val IS_FAVORITE = 1
        const val IS_NOT_FAVORITE = 0
    }
}