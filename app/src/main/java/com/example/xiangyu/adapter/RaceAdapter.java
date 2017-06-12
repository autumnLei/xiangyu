package com.example.xiangyu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiangyu.R;
import com.example.xiangyu.entity.Race;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class RaceAdapter extends RecyclerView.Adapter<RaceAdapter.ViewHolder> {

    private List<Race> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.avatar);
            textView = (TextView)view.findViewById(R.id.text);
        }
    }

    public RaceAdapter(List<Race> raceList) {
        mList = raceList;
    }

    @Override
    public RaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_race  , parent, false);
        RaceAdapter.ViewHolder holder = new RaceAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RaceAdapter.ViewHolder holder, int position) {
        Race race = mList.get(position);
        holder.imageView.setImageResource(race.getImage());
        holder.textView.setText(race.getText());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
