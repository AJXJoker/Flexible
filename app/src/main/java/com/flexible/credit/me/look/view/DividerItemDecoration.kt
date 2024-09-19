package com.flexible.credit.me.look.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
class DividerItemDecoration(
    context: Context,
    private val lineHeight: Int,         // 线条高度（1px）
    private val lineColor: Int,          // 线条颜色
    private val lineWidth: Float,        // 线条宽度（可自定义）
    private val leftPadding: Float,      // 线条左侧的间距
    private val rightPadding: Float      // 线条右侧的间距
) : RecyclerView.ItemDecoration() {

    private val paint: Paint = Paint()

    init {
        paint.color = ContextCompat.getColor(context, lineColor)
        paint.strokeWidth = lineHeight.toFloat()  // 设置线条高度（1px）
        paint.style = Paint.Style.STROKE
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val layoutManager = parent.layoutManager as GridLayoutManager

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            // 获取当前项的位置和网格的列数
            val position = parent.getChildAdapterPosition(child)
            val spanCount = layoutManager.spanCount

            // 只在列之间绘制线条（不在行尾绘制）
            if ((position + 1) % spanCount != 0) {
                // 计算项目的垂直中心位置
                val centerY = (child.top + child.bottom) / 2

                // 计算线条的左右边界
                val left = child.right + params.rightMargin + leftPadding
                val right = left + lineWidth - leftPadding - rightPadding

                // 绘制居中的线条（1px高度）
                c.drawLine(left, centerY.toFloat(), right, centerY.toFloat(), paint)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val layoutManager = parent.layoutManager as GridLayoutManager
        val spanCount = layoutManager.spanCount

        // 为列之间的线条分配右边距的空间
        if ((position + 1) % spanCount != 0) {
            outRect.right = lineWidth.toInt()  // 为列之间的线条分配空间
        }
    }
}



