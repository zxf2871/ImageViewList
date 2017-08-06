package com.study.b8a3;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private MyViewPager mViewPager;
    private List<MyImageView> mDataImage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (MyViewPager) findViewById(R.id.vp_demo);
        mViewPager.setPageTransformer(true, new DepthPageTransformer(mViewPager));
        mViewPager.setOffscreenPageLimit(5);

//        mViewPager.setPageMargin(300);
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

        MyAdapter adapter = new MyAdapter(mDataImage);

        mViewPager.setAdapter(adapter);
    }

}
