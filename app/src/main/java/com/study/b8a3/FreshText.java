package com.study.b8a3;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by B8A3 on 2017/8/6.
 */

public class FreshText extends TextView {

    private boolean mIs = false;//1刷新动画
    private ObjectAnimator mAnim;
    private float mCycleFactorW;//波纹周期
    private float mYPositions[];
    private boolean mIsInit = false;
    private int mTotalWidth;
    private int mTotalHeight;
    private int gradientY;
    private Paint mAgePaint;
    private PorterDuffXfermode mPorterDuffXfermode;


    public static final float STRETCH_FACTOR_A = 8;


    public FreshText(Context context) {
        super(context);
        setFreshText();
    }

    public FreshText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FreshText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFreshText() {
        this.setText("松开刷新。。。");
    }

    public void setFreshing() {
        this.setText("正在刷新。。。");
//        this.setText("");
        startAnim();

    }

    private void init() {
        mTotalWidth = getWidth();
        mTotalHeight = getHeight();
        mYPositions = new float[getWidth()];
        mCycleFactorW = (float) ( 4*Math.PI / mTotalWidth);
        mAgePaint = new Paint();
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        // 根据view总宽度得出所有对应的y值
        for (int i = 0; i < mYPositions.length; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i));
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(">>>>>>>>", "adsfasdfasdf");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnim();
    }

    private void startAnim() {
        if (isAnimating()) {
            return;
        }
        init();
        mAnim = ObjectAnimator.ofInt(FreshText.this, "process", 0, getHeight());
        mAnim.setDuration(2000);
        mAnim.setRepeatCount(-1);
        mAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIs = true;

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIs = false;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    FreshText.this.postInvalidate();
                } else {
                    FreshText.this.postInvalidateOnAnimation();
                }
                animator = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnim.start();

    }

    private boolean isAnimating() {
        return mAnim != null && mAnim.isRunning();
    }

    public void cancelAnim() {
        if (mAnim != null) {
            mAnim.cancel();
        }
    }

    private void setProcess(int p) {
        this.gradientY = p;
        for (int i = 0; i < mYPositions.length; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * (i+p/2)));
        }
        invalidate();
        Log.e(">>>>>>>>>", String.valueOf(p));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (mIs) {
            mAgePaint.setColor(Color.YELLOW);
            mAgePaint.setStrokeWidth(1);
            mAgePaint.setXfermode(mPorterDuffXfermode);

            for (int i = 0; i < mTotalWidth; i++) {

                // 减400只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
                // 绘制第一条水波纹
                canvas.drawLine(i, mTotalHeight - mYPositions[i] - gradientY, i,
                        mTotalHeight,
                        mAgePaint);
            }


            // 绘制源图形

            // 清除混合模式
            mAgePaint.setXfermode(null);




        }


    }
}
