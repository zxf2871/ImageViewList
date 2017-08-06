package com.study.b8a3;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by B8A3 on 2017/8/6.
 */

public class FreshText extends TextView {
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

    public void setFreshText(){
        this.setText("松开刷新。。。");
    }

    public void setFreshing(){
        this.setText("正在刷新。。。");
    }
}
