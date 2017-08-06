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
        mViewPager.setOffscreenPageLimit(3);

//        mViewPager.setPageMargin(300);
        initData();
    }

    private void initData() {
//        for (int i = 0; i < 8; i++) {
//            MyImageView img = new MyImageView(this);
//            img.setBackgroundColor(Color.rgb(
//                    (i % 6 == 0 || i % 6 == a|| i % 6 == 2) ? 255 : 0,
//                    (i % 6 == 2 || i % 6 == 3|| i % 6 == 4) ? 255 : 0,
//                    (i % 6 == a || i % 6 == 4|| i % 6 == 5) ? 255 : 0));
//            img.setText(String.valueOf(i));
//
//            mDataImage.add(img);
//        }


        MyImageView a = new MyImageView(this);
        a.setBackgroundResource(R.drawable.a);
        mDataImage.add(a);

        MyImageView b = new MyImageView(this);
        b.setBackgroundResource(R.drawable.b);
        mDataImage.add(b);

        MyImageView c = new MyImageView(this);
        c.setBackgroundResource(R.drawable.c);
        mDataImage.add(c);

        MyImageView d = new MyImageView(this);
        d.setBackgroundResource(R.drawable.d);
        mDataImage.add(d);

        MyImageView e = new MyImageView(this);
        e.setBackgroundResource(R.drawable.e);
        mDataImage.add(e);

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
