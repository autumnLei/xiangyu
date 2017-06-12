package com.example.xiangyu.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.xiangyu.R;
import com.example.xiangyu.entity.Picture;
import com.example.xiangyu.global.MyApplication;
import com.example.xiangyu.ui.buttons.PictureShowActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private List<Picture> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_view);
        }
    }

    public PictureAdapter(List<Picture> coachList) {
        mList = coachList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), PictureShowActivity.class);
                intent.putExtra("Url", mList.get(holder.getAdapterPosition()).getUrl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(MyApplication.getContext()).load(mList.get(position).getHref()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
