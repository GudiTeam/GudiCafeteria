package com.duzi.gudicafeteria_a.ui.notice

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.notice_item.view.*
import net.cachapa.expandablelayout.ExpandableLayout

class NoticeActivity : AppCompatActivity() {

    private val adapter by lazy { NoticeAdapter(notice_recycler_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        initToolbar()
        initLayout()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_black_24dp)
    }

    private fun initLayout() {
        notice_recycler_view.adapter = this.adapter
        notice_recycler_view.layoutManager = LinearLayoutManager(this)
        notice_recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    companion object {
        class NoticeAdapter(private val recyclerView: RecyclerView): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            private var selectedItem = -1

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
                    NoticeAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notice_item, parent, false))

            override fun getItemCount(): Int = 50

            @SuppressLint("SetTextI18n")
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                holder.itemView.expandable_layout.setInterpolator(OvershootInterpolator())
                holder.itemView.expandable_layout.setOnExpansionUpdateListener { _, state ->
                    if(state == ExpandableLayout.State.EXPANDING) {
                        recyclerView.smoothScrollToPosition(position)
                    }
                }

                val curPosition = holder.adapterPosition
                holder.itemView.setOnClickListener {
                    val selectedHolder = recyclerView.findViewHolderForAdapterPosition(selectedItem)
                    if(selectedHolder != null) {
                        holder.itemView.isSelected = false
                        holder.itemView.expandable_layout.collapse()
                    }

                    if(curPosition == selectedItem) {
                        selectedItem = -1
                    } else {
                        holder.itemView.isSelected = true
                        holder.itemView.expandable_layout.expand()
                        selectedItem = curPosition
                    }
                }

                val isSelected = curPosition == selectedItem
                holder.itemView.notice_title.text = "공지사항 $curPosition"
                holder.itemView.notice_date.text = "2018-07-11"

                val sb = StringBuilder()
                sb.append("안녕하세요 구디식당입니다\n")
                        .append("10월 이벤트 혜택알림 수신 동의 이벤트 당첨자를 발표합니다.\n")
                        .append("이벤트 혜택알림을 수신 동의 하신 분들 중 3,000명에게 5,000원 할인 쿠폰을 쿠폰함으로 지급하였습니다.\n")
                        .append("\n\n")
                        .append("다양한 이벤트로 찾아오겠습니다 :) 감사합니다~\n")
                        .append("구디식당 드림.\n")
                holder.itemView.notice_content.text = sb.toString()
                holder.itemView.isSelected = isSelected
                holder.itemView.expandable_layout.setExpanded(isSelected, false)
            }

            class ViewHolder(view: View): RecyclerView.ViewHolder(view)
        }
    }
}
