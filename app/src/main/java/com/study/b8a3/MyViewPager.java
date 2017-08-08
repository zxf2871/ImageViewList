package com.study.b8a3;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.SoftReference;

/**
 * Created by B8A3 on 2017/8/6.
 */

public class MyViewPager extends ViewPager {
    public final static String TAG = MyViewPager.class.getSimpleName();
    private boolean isMoveLeft = true;//左边是否能移动
    private boolean isMoveRight;
    private Rect normal = new Rect();//记录原来的位置
    private OnRefreshListener listener;
    float x = 0;//记录开始触摸的位置
    int xMove = 0;//移动的距离
    private int SCALE = 9;
    private boolean show = false;
    private boolean showR = false;


    public void setFreshView(FreshText mFreshView) {
        this.mFreshView = mFreshView;
    }

    public void setAddView(TextView view) {
        this.mAddView = view;
    }

    private FreshText mFreshView;
    private TextView mAddView;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (x == 0) {
                    x = ev.getX();//记录位置
                }
                xMove = (int) (x - ev.getX()) / 2;//计算移动距离
//                Log.e(TAG, "xMove:" + xMove + "isMoveLeft:" + isMoveLeft + "isMoveRight:" + isMoveRight);
                if (isMoveLeft && xMove <= 0 || isMoveRight && xMove >= 0) {//移动位置

                    if (mFreshView != null && xMove < -DeviceUtils.getWindowWidth(getContext()) / SCALE) {
                        mFreshView.setVisibility(VISIBLE);
//                        mFreshView.setFreshText();
                        show = true;
                    } else if (mAddView != null && xMove > DeviceUtils.getWindowWidth(getContext()) / SCALE) {
//                        mAddView.setVisibility(VISIBLE);
                        showR = true;
                    } else {
                        mFreshView.setVisibility(INVISIBLE);
                        mAddView.setVisibility(INVISIBLE);
                        mAddView.setText("加载中。。。");

//                        mFreshView.setFreshText();
                        showR = false;
                        show = false;
                    }
                    this.layout(-xMove, normal.top, normal.right - xMove, normal.bottom);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                if (show) {
                    mFreshView.setFreshing();
                    show = false;
                }
                if (showR) {
//                    mAddView.setText("加载更多。");
                    mAddView.setVisibility(VISIBLE);

                    showR = false;
                }
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (isMoveLeft || isMoveRight) {
                            animation(xMove);//还原位置
                        }
                    }
                });

                break;


            case MotionEvent.ACTION_POINTER_UP://多点触摸
            case MotionEvent.ACTION_POINTER_INDEX_SHIFT:
                if (isMoveLeft || isMoveRight) {
                    animation(xMove);//还原位置
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {//监听viewpager是否是第一页或最后一页
//        Log.e("---------", "position:" + position + "offset:" + offset + "offsetPixels:" + offsetPixels);

        if (getAdapter() == null && getAdapter().getCount() == 0) {
            isMoveLeft = false;
            isMoveRight = false;
        } else if (position == 0 && offsetPixels == 0) {
            isMoveLeft = true;
        } else if (position == getAdapter().getCount() - 2) {
            isMoveRight = true;
        } else {
            isMoveLeft = false;
            isMoveRight = false;
        }
        if (normal.isEmpty() || normal.top - normal.bottom == 0) {
            normal.set(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());//viewpager记录原来位置
        }
        super.onPageScrolled(position, offset, offsetPixels);
    }

    /***
     * 回缩动画
     */
    public void animation(int moveX) {
        if (listener != null) {
            if (moveX > DeviceUtils.getWindowWidth(getContext()) / SCALE) {//滑动的距离超过屏幕的1/SCALE才回调
                mAddView.setText("加载中。。。");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onLoadMore();
                        Message message = Message.obtain();
                        message.what = MESSAGE_TYPE_FINSH_RIGHT_PULL;
                        mHandler.sendMessage(message);
                    }
                }).start();

            } else if (moveX < -DeviceUtils.getWindowWidth(getContext()) / SCALE) {
                mFreshView.setFreshing();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onRefresh();
                        Message message = Message.obtain();
                        message.what = MESSAGE_TYPE_FINSH_LEFT_PULL;
                        mHandler.sendMessage(message);
                    }
                }).start();
            } else {
                if (isMoveRight)
                    finishFreshR();
                if (isMoveLeft)
                    finishFresh();
            }
        } else {
            if (isMoveRight)
                finishFreshR();
            if (isMoveLeft)
                finishFresh();
        }


    }

    public static final int MESSAGE_TYPE_FINSH_RIGHT_PULL = 1;
    public static final int MESSAGE_TYPE_FINSH_LEFT_PULL = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_TYPE_FINSH_LEFT_PULL:
                    finishFresh();
                    break;

                case MESSAGE_TYPE_FINSH_RIGHT_PULL:
                    finishFreshR();
                    break;
            }
        }
    };

    private void finishFresh() {
        mFreshView.setVisibility(INVISIBLE);
        mFreshView.setFreshText();
        mFreshView.cancelAnim();
        x = 0;
        xMove = 0;
        TranslateAnimation ta = new TranslateAnimation(this.getX(), normal.left, 0, 0);
        ta.setDuration(500);
        this.startAnimation(ta);
        this.layout(normal.left, normal.top, normal.right, normal.bottom);
    }

    private void finishFreshR() {
        if (isClicked) {
            mAddView.setText("没有了");
            mAddView.setOnClickListener(null);
            isClicked = false;
        } else {
            mAddView.setText("加载失败。 点击重试");
            mAddView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    isClicked = true;
                    if (listener != null) {
                        mAddView.setText("加载中。。。");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onLoadMore();
                                Message message = Message.obtain();
                                message.what = MESSAGE_TYPE_FINSH_RIGHT_PULL;
                                mHandler.sendMessage(message);
                            }
                        }).start();
                    }
                }
            });
        }

        x = 0;
        xMove = 0;
        TranslateAnimation ta = new TranslateAnimation(this.getX(), normal.left, 0, 0);
        ta.setDuration(500);
        this.startAnimation(ta);
        this.layout(normal.left, normal.top, normal.right, normal.bottom);
    }

    boolean isClicked = false;

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
