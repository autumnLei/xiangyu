package com.example.xiangyu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiangyu.R;
import com.example.xiangyu.global.MyApplication;
import com.example.xiangyu.ui.XiangYuActivity;

import java.util.Random;

public class SamplePagerAdapter extends PagerAdapter {

    private final Random random = new Random();
    private int mSize;


    private int[] images = {R.drawable.a ,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e, };

    public SamplePagerAdapter() {mSize = 5;}

    @Override public int getCount() {
        return mSize;
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override public Object instantiateItem(ViewGroup view, int position) {
    ImageView imageView = new ImageView(view.getContext());
    //图片太大不能用下面这行
    //imageView.setImageResource(images[position + 1]);
    Glide.with(MyApplication.getContext()).load(images[position]).into(imageView);
    view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    return imageView;
}
}