package com.example.xiangyu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiangyu.R;
import com.example.xiangyu.entity.Sports;
import com.example.xiangyu.global.MyApplication;
import com.example.xiangyu.ui.SearchActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/2.
 */

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder>{

    private List<Sports> mList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textTitle;
        TextView textLocal;
        TextView textTel;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.imageView_sports);
            textTitle = (TextView)view.findViewById(R.id.textTitle);
            textLocal = (TextView)view.findViewById(R.id.textLocal);
            textTel = (TextView)view.findViewById(R.id.textTel);
        }

    }

    public SportsAdapter(List<Sports> sportsList, Context context){
        mList = sportsList;
        this.context = context;
    }

    @Override
    public SportsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sports, parent, false);
        final SportsAdapter.ViewHolder holder = new SportsAdapter.ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), SearchActivity.class);
                intent.putExtra("Url", mList.get(holder.getAdapterPosition()).getDetailUrl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SportsAdapter.ViewHolder holder, int position) {
        Sports sports = mList.get(position);
        //加载图片
        if (sports.getImageView() != null)
          Glide.with(context).load(sports.getImageView()).into(holder.imageView);
        //作者名字加粗
        holder.textTitle.setText(sports.getTextTitle());
        TextPaint tp = holder.textTitle.getPaint();
        tp.setFakeBoldText(true);
        //
        holder.textLocal.setText(sports.getTextLocal());
        holder.textTel.setText(sports.getTextTel());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
