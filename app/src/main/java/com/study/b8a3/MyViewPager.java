package com.study.b8a3;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by B8A3 on 2017/8/6.
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);

    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
//        Log.e("------->", "position "+position+" offset: "+offset+"  offsetPixels: "+offsetPixels);
    }



}
