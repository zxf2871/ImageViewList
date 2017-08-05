package com.study.b8a3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ViewPager mViewPager;
    private List<MyImageView> mDataImage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.vp_demo);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setOffscreenPageLimit(4);

        mViewPager.setPageMargin(100);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 8; i++) {
            MyImageView img = new MyImageView(this);
            img.setBackgroundColor(Color.rgb(
                    (i % 6 == 0 || i % 6 == 1|| i % 6 == 2) ? 255 : 0,
                    (i % 6 == 2 || i % 6 == 3|| i % 6 == 4) ? 255 : 0,
                    (i % 6 == 1 || i % 6 == 4|| i % 6 == 5) ? 255 : 0));
            img.setText(String.valueOf(i));

            mDataImage.add(img);
        }

        mViewPager.setAdapter(new PagerAdapter() {
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
        });
    }

}
