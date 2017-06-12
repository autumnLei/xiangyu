package com.example.xiangyu.overlayutil;

import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MyOverLay extends PoiOverlay {
    /**
     * 构造函数
     */
    PoiSearch poiSearch;
    public MyOverLay(BaiduMap baiduMap, PoiSearch poiSearch) {
        super(baiduMap);
        this.poiSearch = poiSearch;
    }
    /**
     * 覆盖物被点击时
     */
    @Override
    public boolean onPoiClick(int i) {
        //获取点击的标记物的数据
        PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
        Log.e("TAG", poiInfo.name + "   " + poiInfo.address + "   " + poiInfo.phoneNum + "   ");
        //  发起一个详细检索,要使用uid
        poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
        return true;
    }
}
