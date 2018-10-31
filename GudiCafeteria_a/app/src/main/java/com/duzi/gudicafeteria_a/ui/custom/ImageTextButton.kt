package com.duzi.gudicafeteria_a.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.duzi.gudicafeteria_a.R
import kotlinx.android.synthetic.main.layout_image_text_button_left.view.*

class ImageTextButton : RelativeLayout {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
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

    private var positionType:IconPosition? = null

    companion object {
        enum class IconPosition(value: Int) {
            LEFT(1), TOP(2), RIGHT(3), BOTTOM(4)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun init(context: Context, attrs: AttributeSet?) {
        initLayout(context)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextButton)
        val positionValue = typedArray.getInt(R.styleable.ImageTextButton_itb_icon_position, 1)
        positionType = IconPosition.values()[positionValue]
        initPadding(typedArray)
        initBackground(typedArray)
        initTitle(typedArray, button_text)
        initIcon(typedArray, icon)
        typedArray.recycle()
    }

    private fun initLayout(context: Context) {
        when(positionType) {
            IconPosition.LEFT -> LayoutInflater.from(context).inflate(R.layout.layout_image_text_button_left, this)
            else -> LayoutInflater.from(context).inflate(R.layout.layout_image_text_button_left, this)
        }
    }

    private fun initPadding(typedArray: TypedArray) {
        val padding = typedArray.getDimensionPixelOffset(R.styleable.ImageTextButton_itb_padding,
                resources.getDimensionPixelOffset(R.dimen.default_padding))
        if(padding > 0) {
            setPadding(padding, padding, padding, padding)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initBackground(typedArray: TypedArray) {
        val radius = typedArray.getDimensionPixelOffset(R.styleable.ImageTextButton_itb_radius,
                resources.getDimensionPixelOffset(R.dimen.default_radius))
        val normalBgColor = typedArray.getColor(R.styleable.ImageTextButton_itb_bg,
                ContextCompat.getColor(context, R.color.default_bg))
        var pressedBgColor = typedArray.getColor(R.styleable.ImageTextButton_itb_bg_pressed, -1)
        if(pressedBgColor == -1) {
            val alpha = Color.alpha(normalBgColor)
            val red = Color.red(normalBgColor)
            val green = Color.green(normalBgColor)
            val blue = Color.blue(normalBgColor)
            pressedBgColor = if(alpha < 0xFF) {
                Color.argb(0xFF, red, green, blue)
            } else {
                Color.argb(0x90, red, green, blue)
            }
        }
        val disabledBgColor = typedArray.getColor(R.styleable.ImageTextButton_itb_bg_disabled,
                ContextCompat.getColor(context, R.color.default_bg_disabled))

        val normalShape = GradientDrawable()
        normalShape.setColor(normalBgColor)

        val pressedShape = GradientDrawable()
        pressedShape.setColor(pressedBgColor)

        val disabledShape = GradientDrawable()
        disabledShape.setColor(disabledBgColor)

        if(radius > 0) {
            normalShape.cornerRadius = radius.toFloat()
            pressedShape.cornerRadius = radius.toFloat()
            disabledShape.cornerRadius = radius.toFloat()
        }

        val bg = StateListDrawable()
        bg.addState(intArrayOf(android.R.attr.state_pressed), pressedShape)
        bg.addState(intArrayOf(android.R.attr.state_enabled), normalShape)
        background = bg
        isClickable = true
    }
    private fun initTitle(typedArray: TypedArray, textView: TextView) {
        val title = typedArray.getString(R.styleable.ImageTextButton_itb_text)

        if(title.isEmpty()) {
            textView.visibility = GONE
        } else {
            val textSize = typedArray.getDimensionPixelSize(R.styleable.ImageTextButton_itb_text_size,
                    resources.getDimensionPixelSize(R.dimen.default_text_size))
            val textColor = typedArray.getColor(R.styleable.ImageTextButton_itb_text_color,
                    ContextCompat.getColor(context, R.color.default_text_color))
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