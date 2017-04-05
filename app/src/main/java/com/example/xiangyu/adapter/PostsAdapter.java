package com.example.xiangyu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/27.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private long POSTS_TOUXIANG;

    private String POSTS_NAME;

    private Date POSTS_TIME;

    //第二行LinearLayout
    private String POSTS_NEWS;

    //第三行LinearLayout
    private int POSTS_BTN;

    private int POSTS_PRAISE_NUM;

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
