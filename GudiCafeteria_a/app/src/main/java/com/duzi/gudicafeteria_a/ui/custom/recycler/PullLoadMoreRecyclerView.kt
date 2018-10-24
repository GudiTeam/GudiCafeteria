package com.duzi.gudicafeteria_a.ui.custom.recycler

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.layout_pull_load_more.view.*
import kotlinx.android.synthetic.main.layout_pull_load_more_footer.view.*

class PullLoadMoreRecyclerView: LinearLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private var hasMore = true
    private var isRefresh = false
    private var isLoadMore = false
    private var pullRefreshEnable = true
    private var pushRefreshEnable = true
    lateinit var pullLoadMoreListener: PullLoadMoreListener

    private fun init(context: Context) {
        initLayout(context)
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(context, android.R.color.holo_blue_bright),
                ContextCompat.getColor(context, android.R.color.holo_green_light),
                ContextCompat.getColor(context, android.R.color.holo_orange_light),
                ContextCompat.getColor(context, android.R.color.holo_red_light))
        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayoutOnRefresh(this))

        recycler_view.isVerticalScrollBarEnabled = true
        recycler_view.setHasFixedSize(true)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.addOnScrollListener(RecyclerViewOnScroll(this))
        recycler_view.setOnTouchListener(OnTouchRecyclerView())

        footerView.visibility = GONE
    }

    private fun initLayout(context: Context) = LayoutInflater.from(context).inflate(R.layout.layout_pull_load_more, this)

    fun setLinearLayout() {
        recycler_view.layoutManager = LinearLayoutManager(context)
    }

    fun setGridLayout(spanCount: Int) {
        recycler_view.layoutManager = GridLayoutManager(context, spanCount)
    }

    fun setStaggeredGridLayout(spanCount: Int) {
        recycler_view.layoutManager = StaggeredGridLayoutManager(spanCount, VERTICAL)
    }

    fun setItemAnimator(animator: RecyclerView.ItemAnimator) {
        recycler_view.itemAnimator = animator
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration, index: Int) {
        recycler_view.addItemDecoration(decor, index)
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration) {
        recycler_view.addItemDecoration(decor)
    }

    fun scrollToTop() {
        recycler_view.scrollToPosition(0)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        recycler_view.adapter = adapter
    }

    fun setPullRefreshEnable(enable: Boolean) {
        pullRefreshEnable = enable
        setSwipeRefreshEnable(enable)
    }

    fun getPullRefreshEnable() = pullRefreshEnable

    fun setSwipeRefreshEnable(enable: Boolean) {
        swipeRefreshLayout.isEnabled = enable
    }

    fun getSwipeRefreshEnable() = swipeRefreshLayout.isEnabled

    /**
     * 새로고침 아이콘 색상 변경
     * 화살표가 한바퀴 돌 때마다 각각의 색상으로 나타남
     */
    fun setColorSchemeResources(vararg colors: Int) {
        swipeRefreshLayout.setColorSchemeResources(*colors)
    }

    fun setRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout.post {
            if(pullRefreshEnable)
                swipeRefreshLayout.isRefreshing = isRefreshing
        }
    }

    fun getPushRefreshEnable() = pushRefreshEnable

    fun setPushRefreshEnable(pushRefreshEnable: Boolean) {
        this.pushRefreshEnable = pushRefreshEnable
    }

    fun setFooterViewBackgroundColor(color: Int) {
        loadMoreLayout.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun setFooterViewText(text: String) {
        loadMoreText.text = text
    }

    fun setFooterViewTextColor(color: Int) {
        loadMoreText.setTextColor(color)
    }

    fun setOnPullLoadMoreListener(listener: PullLoadMoreListener) {
        pullLoadMoreListener = listener
    }

    fun refresh() {
        pullLoadMoreListener.onRefresh()
    }

    fun loadMore() {
        if(hasMore) {
            footerView.animate()
                    .translationY(0.0f)
                    .setDuration(300)
                    .setInterpolator(AccelerateInterpolator())
                    .setListener(object: AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            footerView.visibility = VISIBLE
                        }
                    })
                    .start()

            invalidate()
            pullLoadMoreListener.onLoadMore()
        }
    }

    fun setPullLoadMoreCompleted() {
        isRefresh = false
        setRefreshing(false)

        isLoadMore = false
        footerView.animate()
                .translationY(footerView.height.toFloat())
                .setDuration(300)
                .setInterpolator(AccelerateInterpolator())
                .start()
    }

    fun isLoadMore() = isLoadMore

    fun setIsLoadMore(isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
    }

    fun isRefresh(): Boolean = isRefresh

    fun setIsRefresh(isRefresh: Boolean) {
        this.isRefresh = isRefresh
    }

    fun isHasMore() = hasMore

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
    }

    inner class OnTouchRecyclerView: OnTouchListener {
        override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
            return isRefresh || isLoadMore
        }
    }

    interface PullLoadMoreListener {
        fun onRefresh()
        fun onLoadMore()
    }
}