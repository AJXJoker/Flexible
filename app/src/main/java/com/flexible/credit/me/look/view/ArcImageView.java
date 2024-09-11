package com.flexible.credit.me.look.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.flexible.credit.me.look.R;

public class ArcImageView extends AppCompatImageView {

    /*
     * 弧形高度
     */
    private int mArcHeight;
    /*
     * 弧形方向，默认向下绘制
     */
    private boolean isArcUpwards;
    /*
     * 弧形突出方向，默认向内
     */
    private boolean isArcOutwards;

    public ArcImageView(Context context) {
        this(context, null);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcImageView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcImageView_arcHeight, 0);
        isArcUpwards = typedArray.getBoolean(R.styleable.ArcImageView_arcDirectionUpwards, false);
        isArcOutwards = typedArray.getBoolean(R.styleable.ArcImageView_arcDirectionOutwards, false);
        typedArray.recycle(); // 确保TypedArray被回收，防止内存泄漏
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int width = getWidth();
        int height = getHeight();

        if (isArcUpwards) {
            // 圆弧向上
            if (isArcOutwards) {
                // 圆弧向上并向外突出
                path.moveTo(0, height);
                path.lineTo(0, mArcHeight); // 到圆弧开始处
                path.quadTo(width / 2, -mArcHeight, width, mArcHeight); // 绘制向上的弧线
                path.lineTo(width, height); // 到右下角
            } else {
                // 圆弧向上并向内凹
                path.moveTo(0, height);
                path.lineTo(0, 0);
                path.quadTo(width / 2, 2 * mArcHeight, width, 0);
                path.lineTo(width, height);
            }
        } else {
            // 圆弧向下
            if (isArcOutwards) {
                // 圆弧向下并向外突出
                path.moveTo(0, 0);
                path.lineTo(0, height - mArcHeight); // 到圆弧开始处
                path.quadTo(width / 2, height + mArcHeight, width, height - mArcHeight); // 绘制向下的弧线
                path.lineTo(width, 0); // 到右上角
            } else {
                // 圆弧向下并向内凹
                path.moveTo(0, 0);
                path.lineTo(0, height);
                path.quadTo(width / 2, height - 2 * mArcHeight, width, height);
                path.lineTo(width, 0);
            }
        }

        path.close();
        canvas.clipPath(path); // 裁剪路径
        super.onDraw(canvas); // 调用父类的绘制方法，绘制图片
    }
}
