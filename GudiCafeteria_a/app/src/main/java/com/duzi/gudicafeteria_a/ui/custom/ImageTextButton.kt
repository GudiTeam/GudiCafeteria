package com.duzi.gudicafeteria_a.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.duzi.gudicafeteria_a.R

class ImageTextButton : RelativeLayout {

    constructor(context: Context,
                attrs: AttributeSet? = null,
                defStyleAttr:Int = 0) : super(context, attrs, defStyleAttr) {
        init(context, attrs!!)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context,
                attrs: AttributeSet?,
                defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs!!)
    }

    private var icon: ImageView? = null
    private var title: TextView? = null
    private var area: ConstraintLayout? = null
    private var positionType:IconPosition? = null

    companion object {
        enum class IconPosition(value: Int) {
            LEFT(1), TOP(2), RIGHT(3), BOTTOM(4)
        }
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val rootView: View = initLayout(context)
        area = getArea(rootView)
        icon = getIcon(rootView)
        title = getTitle(rootView)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextButton)
        val positionValue = typedArray.getInt(R.styleable.ImageTextButton_itb_icon_position, 1)
        positionType = IconPosition.values()[positionValue]
        initPadding(typedArray)
        initBackground(typedArray)
        initTitle(typedArray, title!!)
        initIcon(typedArray, icon!!)
        typedArray.recycle()
    }

    private fun initLayout(context: Context): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return when(positionType) {
            IconPosition.LEFT -> inflater.inflate(R.layout.layout_image_text_button_left, this)
            else -> inflater.inflate(R.layout.layout_image_text_button_left, this)
        }
    }

    private fun getArea(rootView: View): ConstraintLayout = rootView.findViewById(R.id.area)
    private fun getIcon(parentView: View): ImageView = parentView.findViewById(R.id.icon)
    private fun getTitle(parentView: View): TextView = parentView.findViewById(R.id.button_text)

    private fun initPadding(typedArray: TypedArray) {
        val padding = typedArray.getDimensionPixelOffset(R.styleable.ImageTextButton_itb_padding,
                resources.getDimensionPixelOffset(R.dimen.default_padding))
        if(padding > 0) {
            setPadding(padding, padding, padding, padding)
        }
    }
    private fun initBackground(typedArray: TypedArray) {

    }
    private fun initTitle(typedArray: TypedArray, textView: TextView) {
        val title = typedArray.getString(R.styleable.ImageTextButton_itb_text)

        if(title.isEmpty()) {
            textView.visibility = GONE
        } else {
            val textSize = typedArray.getDimensionPixelSize(R.styleable.ImageTextButton_itb_text_size,
                    resources.getDimensionPixelSize(R.dimen.default_text_size))
            val textColor = typedArray.getColor(R.styleable.ImageTextButton_itb_text_color,
                    resources.getColor(R.color.default_text_color))
            val margin = typedArray.getDimensionPixelOffset(R.styleable.ImageTextButton_itb_icon_text_marggin,
                    resources.getDimensionPixelOffset(R.dimen.default_text_margin))
            textView.textSize = px2dip(textSize)
            textView.setTextColor(textColor)

            val params = textView.layoutParams as ViewGroup.MarginLayoutParams
            when(positionType) {
                IconPosition.LEFT -> params.setMargins(margin, 0, 0, 0)
                else -> params.setMargins(margin, 0, 0, 0)
            }
            textView.layoutParams = params
            textView.text = title
        }

    }
    private fun initIcon(typedArray: TypedArray, imageView: ImageView) {
        val icon: Drawable? = typedArray.getDrawable(R.styleable.ImageTextButton_itb_icon)
        val iconSize = typedArray.getDimensionPixelSize(R.styleable.ImageTextButton_itb_icon_size,
                getResources().getDimensionPixelOffset(R.dimen.default_icon_width))

        if(icon == null) {
            imageView.visibility = GONE
            return
        }

        val params = imageView.layoutParams
        params.height = iconSize
        params.width = iconSize
        imageView.layoutParams = params
        imageView.setImageDrawable(icon)
    }

    private fun px2dip(pxValue: Int): Float {
        val scale = resources.displayMetrics.density
        return pxValue / scale + 0.5f
    }
}