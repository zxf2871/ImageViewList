package com.study.b8a3;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by B8A3 on 2017/8/5.
 */

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.95f;

    private final MyViewPager mVp;

    public DepthPageTransformer(MyViewPager viewPager) {
        mVp = viewPager;
    }

    public void transformPage(View view, float position) {
        long now = System.currentTimeMillis();

        int pageWidth = view.getWidth();
        float scaleFactor = Math.max(MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)), 0);

        if (position > -1f) {
//            view.setTranslationX(pageWidth * -(a / (position + a) + a) + 100);
        }
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);


        float tx = pageWidth * -position * 1.15f;
        view.setTranslationX(tx + 100);
        if (position < -4 || position > 4) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        //层级调换
        if (Build.VERSION.SDK_INT > LOLLIPOP) {
            if (position > 0) {
                view.setTranslationZ(-position);
            } else {
                view.setTranslationZ(position);
            }
        }

        int currentIndex = mVp.getCurrentItem();
        if (Math.abs(position) < 0.5 && ((MyAdapter) mVp.getAdapter()).getIndex((MyImageView) view) == currentIndex) {
            view.setAlpha(1 - Math.abs(position));
        } else {
            view.setAlpha(1);
        }
//        Log.e("------->", String.valueOf(((TextView) view).getText())
//                + " currentIndex  " + currentIndex
//                + " position  " + position
//                + " mVp  " + mVp.getCurrentItem()
//                + " getIndex  " + ((MyAdapter) mVp.getAdapter()).getIndex((MyImageView) view)
//                + " pageWidth  " + pageWidth);


    }


}
