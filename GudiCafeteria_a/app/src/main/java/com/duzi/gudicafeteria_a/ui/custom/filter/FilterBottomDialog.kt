package com.duzi.gudicafeteria_a.ui.custom.filter

import android.view.View
import com.duzi.gudicafeteria_a.R
import me.shaohui.bottomdialog.BaseBottomDialog

class FilterBottomDialog: BaseBottomDialog() {

    lateinit var listener: FilterListener
    fun setFilterListener(listener: FilterListener): FilterBottomDialog {
        this.listener = listener
        return this
    }

    override fun getLayoutRes(): Int {
        return R.layout.layout_filter
    }

    override fun bindView(v: View?) {

    }

    companion object {
        // TODO 누를떄마다 새로운 객체가 생성되지않게
    }

}