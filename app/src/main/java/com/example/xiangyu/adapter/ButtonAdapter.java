package com.example.xiangyu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.xiangyu.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ButtonAdapter extends ArrayAdapter<com.example.xiangyu.entity.Button>{
    private int resourceId;

    public ButtonAdapter(Context context, int textViewResourceId, List<com.example.xiangyu.entity.Button> buttons) {
        super(context, textViewResourceId, buttons);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        com.example.xiangyu.entity.Button button = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
        TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        textView1.setText(button.getName());
        textView2.setText(button.getLocation());
        return view;
    }
}
