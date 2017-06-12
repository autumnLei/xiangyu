package com.example.xiangyu.loader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.xiangyu.global.MyApplication;
import com.example.xiangyu.overlayutil.MyOverLay;
import com.example.xiangyu.service.Utils;
import com.example.xiangyu.ui.LocationActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class BaiduLoader extends Activity{
    String type;
    private PoiSearch poiSearch;
    private String currentPosition;
    List<PoiInfo> poiList;

    public BaiduLoader(String teyp) {
        this.type = type;
        String city = currentPosition.toString();
        String key = type;
        //搜索对象的创建
        poiSearch = PoiSearch.newInstance();
        //设置Poi监听对象
        poiSearch.setOnGetPoiSearchResultListener(poiSearchResultListener);
        //发起检索
        PoiCitySearchOption poiCity = new PoiCitySearchOption();
        poiCity.keyword(key).city(city);
        poiSearch.searchInCity(poiCity);
        InputMethodManager imm = (InputMethodManager) getSystemService(BaiduLoader.this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //POI检索的监听对象
    OnGetPoiSearchResultListener poiSearchResultListener = new OnGetPoiSearchResultListener() {
        //获得POI的检索结果，一般检索数据都是在这里获取
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {//如果没有错误
                // 会严重拖累程序速度
                poiList =  poiResult.getAllPoi();
                for (int i = 0; i < poiList.size(); i++){
                    Log.d("777", "onGetPoiResult: "+ poiList.get(i).name);
                }
                return;
            } else {
                Toast.makeText(MyApplication.getContext(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
            }
        }

        //获得POI的详细检索结果，如果发起的是详细检索，这个方法会得到回调(需要uid)
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(MyApplication.getContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            } else {// 正常返回结果的时候，此处可以获得很多相关信息
                Toast.makeText(MyApplication.getContext(), poiDetailResult.getName() + ": " + poiDetailResult.getAddress(), Toast.LENGTH_LONG).show();
                Log.d("666", "onGetPoiDetailResult: " + poiDetailResult.detailUrl);
            }
        }

        //获得POI室内检索结果
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    };
}
