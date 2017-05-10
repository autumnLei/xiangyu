package com.example.xiangyu.global;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.example.xiangyu.R;
import com.example.xiangyu.ui.XiangYuActivity;
import com.zxy.recovery.core.Recovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class MyApplication extends Application {

    private static Context context;
    public static int H;
    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initBanner();
    }

    public static Context getContext() {
        return context;
    }

    private void initBanner() {
        H = getScreenH(this);

        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(XiangYuActivity.class)
                .init(this);

        String[] urls = getResources().getStringArray(R.array.url4);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList<>(list);
        titles= Arrays.asList(tips);
    }

    /**
     * 获取屏幕高度
     * @param aty
     * @return
     */
    public int getScreenH(Context aty){
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
