package com.flexible.credit.me.lib_base.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.flexible.credit.me.lib_base.R

/**
 * 使用方式
 * <com.flexible.credit.me.lib_base.view.CustomTextView
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     android:text="Custom Text"
 *     android:textSize="16sp"
 *     app:borderColor="#FF0000"
 *     app:borderWidth="2dp"
 *     app:fillColor="#FFFF00"
 *     app:cornerRadius="10dp"
 *     app:topLeftRadius="15dp"
 *     app:topRightRadius="15dp"
 *     app:bottomLeftRadius="5dp"
 *     app:bottomRightRadius="5dp" />
 *
 */
class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var borderColor: Int = Color.BLACK
    private var borderWidth: Float = 0f
    private var fillColor: Int = Color.TRANSPARENT
    private var cornerRadius: Float = 0f
    private var topLeftRadius: Float = 0f
    private var topRightRadius: Float = 0f
    private var bottomLeftRadius: Float = 0f
    private var bottomRightRadius: Float = 0f

    private val backgroundDrawable = GradientDrawable()

    init {
        // 从布局属性中获取自定义属性
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomTextView)
            borderColor = typedArray.getColor(R.styleable.CustomTextView_borderColor, Color.BLACK)
            borderWidth = typedArray.getDimension(R.styleable.CustomTextView_borderWidth, 0f)
            fillColor = typedArray.getColor(R.styleable.CustomTextView_fillColor, Color.TRANSPARENT)
            cornerRadius = typedArray.getDimension(R.styleable.CustomTextView_cornerRadius, 0f)
            topLeftRadius =
                typedArray.getDimension(R.styleable.CustomTextView_topLeftRadius, cornerRadius)
            topRightRadius =
                typedArray.getDimension(R.styleable.CustomTextView_topRightRadius, cornerRadius)
            bottomLeftRadius =
                typedArray.getDimension(R.styleable.CustomTextView_bottomLeftRadius, cornerRadius)
            bottomRightRadius =
                typedArray.getDimension(R.styleable.CustomTextView_bottomRightRadius, cornerRadius)
            typedArray.recycle()
        }

        // 初始化边框和背景
        applyCustomization()
    }

    // 动态设置边框颜色
    fun setBorderColor(color: Int) {
        borderColor = color
        applyCustomization()
    }

    // 动态设置边框宽度
    fun setBorderWidth(width: Float) {
        borderWidth = width
        applyCustomization()
    }

    // 动态设置填充颜色
    fun setFillColor(color: Int) {
        fillColor = color
        applyCustomization()
    }

    // 动态设置圆角
    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        topLeftRadius = radius
        topRightRadius = radius
        bottomLeftRadius = radius
        bottomRightRadius = radius
        applyCustomization()
    }

    // 动态设置每个圆角
    fun setIndividualCornerRadius(
        topLeft: Float, topRight: Float,
        bottomLeft: Float, bottomRight: Float
    ) {
        topLeftRadius = topLeft
        topRightRadius = topRight
        bottomLeftRadius = bottomLeft
        bottomRightRadius = bottomRight
        applyCustomization()
    }

    // 应用自定义属性到背景
    private fun applyCustomization() {
        backgroundDrawable.setStroke(borderWidth.toInt(), borderColor)
        backgroundDrawable.setColor(fillColor)

        // 设置圆角角度
        backgroundDrawable.cornerRadii = floatArrayOf(
            topLeftRadius, topLeftRadius,    // Top left radius
            topRightRadius, topRightRadius,  // Top right radius
            bottomRightRadius, bottomRightRadius, // Bottom right radius
            bottomLeftRadius, bottomLeftRadius    // Bottom left radius
        )

        // 设置背景
        background = backgroundDrawable
    }
}
