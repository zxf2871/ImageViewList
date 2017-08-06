package com.study.b8a3;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private MyViewPager mViewPager;
    private List<MyImageView> mDataImage = new ArrayList<>();
    private FreshText mFreshText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFreshText = (FreshText) findViewById(R.id.fresh_text);
        mViewPager = (MyViewPager) findViewById(R.id.vp_demo);
        mViewPager.setFreshView(mFreshText);
        mViewPager.setOnRefreshListener(new MyViewPager.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mFreshText.setText("正在刷新");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadMore() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        mViewPager.setPageTransformer(true, new DepthPageTransformer(mViewPager));
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


//        MyImageView a = new MyImageView(this);
//        a.setBackgroundResource(R.drawable.a);
//        mDataImage.add(a);
//
//        MyImageView b = new MyImageView(this);
//        b.setBackgroundResource(R.drawable.b);
//        mDataImage.add(b);
//
//        MyImageView c = new MyImageView(this);
//        c.setBackgroundResource(R.drawable.c);
//        mDataImage.add(c);
//
//        MyImageView d = new MyImageView(this);
//        d.setBackgroundResource(R.drawable.d);
//        mDataImage.add(d);
//
//        MyImageView e = new MyImageView(this);
//        e.setBackgroundResource(R.drawable.e);
//        mDataImage.add(e);

//        MyImageView f = new MyImageView(this);
//        f.setBackgroundResource(R.drawable.f);
//        mDataImage.add(f);
//
//        MyImageView g = new MyImageView(this);
//        g.setBackgroundResource(R.drawable.g);
//        mDataImage.add(g);
//
//        MyImageView h = new MyImageView(this);
//        h.setBackgroundResource(R.drawable.h);
//        mDataImage.add(h);

//        MyImageView i = new MyImageView(this);
//        i.setBackgroundResource(R.drawable.i);
//        mDataImage.add(i);







        MyAdapter adapter = new MyAdapter(mDataImage);

        mViewPager.setAdapter(adapter);
    }

}
