package com.study.b8a3;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by B8A3 on 2017/8/6.
 */

public class MyAdapter extends PagerAdapter {
    List<MyImageView> mDataImage;

    public MyAdapter(List<MyImageView> data){
        mDataImage = data;
    }

    @Override
    public int getCount() {
        return mDataImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mDataImage.get(position));
        return mDataImage.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mDataImage.get(position));
    }

    @Override
    public float getPageWidth(int position) {
        return 0.8f;
    }
}
