package com.example.xiangyu.global;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.DisplayMetrics;

import com.baidu.mapapi.SDKInitializer;
import com.example.xiangyu.R;
import com.example.xiangyu.service.LocationService;
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
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initBanner();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
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
//        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList<>(list);
//        titles= Arrays.asList(tips);
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
