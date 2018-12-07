package com.duzi.gudicafeteria_a.ui.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duzi.gudicafeteria_a.R
import com.duzi.gudicafeteria_a.data.Review
import com.duzi.gudicafeteria_a.repository.CafeRepository
import com.duzi.gudicafeteria_a.util.GlideApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.review_list_item.view.*

class ReviewAdapter(private val context: Context,
                    private val disposable: (Disposable) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<Review> by lazy { arrayListOf<Review>() }

    override fun onCreateViewHolder(parent: ViewGroup, holderType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, parent, false))

    override fun getItemCount(): Int = list.size

    //TODO [ISSUE] MVVM패턴에서 데이터는 ViewModel을 통해 Model 데이터를 가져오는 방식인데 Adapter안에서 추가로 데이터가 필요한 경우
    //TODO Adapter내에서 비동기로 데이터를 가져올 것인지 ViewModel을 통해서 데이터를 받아올 건지
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       disposable.invoke(CafeRepository.create().getUser(list[position].user_Id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    holder.itemView.userId.text = it.user_Nm
                    GlideApp.with(context)
                            .load(it.user_Img)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .into(holder.itemView.userImage)
                }
       )

        holder.itemView.run {
            reviewRatingBar.rating = list[position].comment_score.toFloat()
            date.text = "이번주"
            reviewContents.text = list[position].comment
        }
    }

    fun addList(list: List<Review>) {
        this.list.clear()
        this.list.addAll(list)
    }

    companion object {
        class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    }
}