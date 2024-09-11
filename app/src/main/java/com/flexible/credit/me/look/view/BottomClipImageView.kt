package com.flexible.credit.me.look.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class BottomClipImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        val bitmapDrawable = drawable as? BitmapDrawable
        val bitmap = bitmapDrawable?.bitmap
        canvas.save()
        path.reset()
        path.addRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            cornerRadius, cornerRadius, Path.Direction.CW
        )
        canvas.clipPath(path)
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
        }
        canvas.restore()

        // 绘制底部突出部分
        paint.color = -0x1000000 // 设置颜色，这里使用黑色作为示例
        path.reset()
        path.moveTo(0f, height - cornerRadius)
        path.lineTo(0f, height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height - cornerRadius)
        path.arcTo(
            RectF(width - cornerRadius * 2, height - cornerRadius * 2, width.toFloat(), height.toFloat()),
            -90f, 90f, false
        )
        path.arcTo(
            RectF(0f, height - cornerRadius * 2, cornerRadius * 2, height.toFloat()),
            90f, 90f, false
        )
        path.close()
        canvas.drawPath(path, paint)
    }

    companion object {
        private const val cornerRadius = 20f // 底部突出部分的圆角半径
    }
}