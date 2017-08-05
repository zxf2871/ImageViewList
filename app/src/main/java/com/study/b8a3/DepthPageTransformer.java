package com.study.b8a3;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by B8A3 on 2017/8/5.
 */

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        float scaleFactor = Math.max(9
                + (1 - MIN_SCALE) * (1 - Math.abs(position)), 0);
        view.setTranslationX(pageWidth * -position*0.95f + 100);
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        Log.e("------->", String.valueOf(((TextView) view).getText()) + "   " + scaleFactor + "   " + position + "   " + pageWidth);
    }

}
