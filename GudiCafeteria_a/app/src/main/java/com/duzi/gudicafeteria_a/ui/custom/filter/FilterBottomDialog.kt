package com.duzi.gudicafeteria_a.ui.custom.filter

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.layout_filter.*
import me.shaohui.bottomdialog.BaseBottomDialog

class FilterBottomDialog: BaseBottomDialog() {

    lateinit var listener: FilterListener
    fun setFilterListener(listener: FilterListener): FilterBottomDialog {
        this.listener = listener
        return this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window.attributes.windowAnimations = R.style.FilterDialogTheme

        distance.setOnClickListener {
            listener.sortByDistance()
            dialog.dismiss()
        }

        star.setOnClickListener {
            listener.sortByStar()
            dialog.dismiss()
        }

        created.setOnClickListener {
            listener.sortBycreatedAt()
            dialog.dismiss()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.layout_filter
    }

    override fun bindView(v: View?) {
        // findViewById 필요없음
    }



    override fun getHeight(): Int = 800

    companion object {
        private var INSTANCE: FilterBottomDialog? = null
        fun getInstance() =
                INSTANCE ?: synchronized(FilterBottomDialog::class.java) {
                    INSTANCE ?: FilterBottomDialog().also { INSTANCE = it }
                }

    }

}