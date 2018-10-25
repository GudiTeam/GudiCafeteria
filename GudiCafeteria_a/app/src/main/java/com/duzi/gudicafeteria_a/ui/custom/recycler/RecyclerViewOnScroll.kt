package com.duzi.gudicafeteria_a.ui.custom.recycler

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

class RecyclerViewOnScroll(private val pullLoadMoreRecyclerView: PullLoadMoreRecyclerView): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        var lastItem = 0
        var firstItem = 0

        val layoutManager: RecyclerView.LayoutManager = recyclerView.layoutManager!!
        var totalItemCount = layoutManager.itemCount

        when(layoutManager) {
            is LinearLayoutManager -> {
                firstItem = layoutManager.findFirstCompletelyVisibleItemPosition() // 현재 화면에 출력된 리스트중 첫번째 view의 position 리턴
                lastItem = layoutManager.findLastCompletelyVisibleItemPosition() // 현재 화면에 출력된 리스트중 마지막 view의 position 리턴
                if(lastItem == -1)
                    lastItem = layoutManager.findLastVisibleItemPosition()
            }

            is GridLayoutManager -> {
                firstItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                if(lastItem == -1)
                    lastItem = layoutManager.findLastVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                val lastPositions = intArrayOf()
                layoutManager.findLastVisibleItemPositions(lastPositions)
                lastItem = finxMax(lastPositions)
                firstItem = layoutManager.findFirstVisibleItemPositions(lastPositions)[0]
            }
        }

        if(firstItem == 0 || firstItem == RecyclerView.NO_POSITION) {
            if(pullLoadMoreRecyclerView.getPullRefreshEnable())
                pullLoadMoreRecyclerView.setSwipeRefreshEnable(true)
        } else {
            pullLoadMoreRecyclerView.setSwipeRefreshEnable(false)
        }

        /**
         * 데이터를 더 받을수 있는 상태이거나
         * 새로고침 상태가 아니거나
         * 추가 데이터가 있는 경우거나
         * 리스트의 마지막 포지션이거나
         * 추가 데이터 로드 상태가 아니거나 (데이터 추가가 완료되면 false 상태)
         */
        if(pullLoadMoreRecyclerView.getPushRefreshEnable() &&
                !pullLoadMoreRecyclerView.isRefresh() &&
                pullLoadMoreRecyclerView.isHasMore() &&
                (lastItem == totalItemCount - 1) &&
                !pullLoadMoreRecyclerView.isLoadMore() &&
                (dx > 0 || dy > 0)) {

            pullLoadMoreRecyclerView.setIsLoadMore(true)
            pullLoadMoreRecyclerView.loadMore()
        }
    }

    private fun finxMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for(value in lastPositions) {
            if(value > max) {
                max = value
            }
        }
        return max
    }
}