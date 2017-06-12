package com.example.xiangyu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiangyu.R;
import com.example.xiangyu.entity.Frend;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class FrendAdapter extends RecyclerView.Adapter<FrendAdapter.ViewHolder> {

    private List<Frend> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView type;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.avatar);
            name = (TextView)view.findViewById(R.id.name);
            type = (TextView)view.findViewById(R.id.type);
            textView = (TextView)view.findViewById(R.id.text);
        }
    }

    public FrendAdapter(List<Frend> frendList) {
        mList = frendList;
    }

    @Override
    public FrendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frend, parent, false);
        FrendAdapter.ViewHolder holder = new FrendAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FrendAdapter.ViewHolder holder, int position) {
        Frend frend = mList.get(position);
        holder.imageView.setImageResource(frend.getAvatar());
        holder.name.setText(frend.getName());
        holder.type.setText(frend.getType());
        holder.textView.setText(frend.getText());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
