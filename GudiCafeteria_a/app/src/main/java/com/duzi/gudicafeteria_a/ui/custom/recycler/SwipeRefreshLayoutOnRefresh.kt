package com.duzi.gudicafeteria_a.ui.custom.recycler

import android.support.v4.widget.SwipeRefreshLayout

class SwipeRefreshLayoutOnRefresh(private val pullLoadMoreRecyclerView: PullLoadMoreRecyclerView): SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        if(pullLoadMoreRecyclerView.isRefresh()) {
            pullLoadMoreRecyclerView.setIsRefresh(true)
            pullLoadMoreRecyclerView.refresh()
        }
    }
}