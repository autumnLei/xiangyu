package com.example.xiangyu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xiangyu.R;
import com.example.xiangyu.adapter.ButtonAdapter;
import com.example.xiangyu.entity.Button;
import com.example.xiangyu.ui.ButtonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 16/12/23.
 */

public class ButtonSportsFragment extends Fragment {

    private List<Button> buttons = new ArrayList<>();

     public static Fragment newInstance(){
         ButtonSportsFragment fragment = new ButtonSportsFragment();
         return fragment;
     }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports_buttton,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
