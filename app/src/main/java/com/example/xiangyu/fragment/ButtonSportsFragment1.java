package com.example.xiangyu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pools;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.xiangyu.R;
import com.example.xiangyu.adapter.SportsAdapter;
import com.example.xiangyu.entity.Button;
import com.example.xiangyu.entity.Sports;
import com.example.xiangyu.overlayutil.MyOverLay;
import com.example.xiangyu.overlayutil.MyTempOverLay;
import com.example.xiangyu.ui.LocationActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 16/12/23.
 */

public class ButtonSportsFragment1 extends Fragment {
    String TAG = "333";
    private List<Sports> mList = new ArrayList<>();
    RecyclerView recyclerView;
    String type, local;
    private PoiSearch poiSearch;
    List<PoiInfo> poiList = new ArrayList<>(); //存储搜索场馆的信息实体；
    SportsAdapter adapter;

    public static Fragment newInstance(String mType, String mLocal) {
        ButtonSportsFragment1 fragment = new ButtonSportsFragment1();
        Bundle bundle = new Bundle();
        bundle.putString("type", mType);
        bundle.putString("local", mLocal);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.type = bundle.getString("type");
            this.local = bundle.getString("local");
        }
        select();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports_buttton, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SportsAdapter(mList, this.getContext());
        recyclerView.setAdapter(adapter);
        // 注：recyclerView初始化放poiSearchResultListener里 这样刚刚好回调一次 白瞎一晚上

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 下面全是和获取周边信息的函数
     */
    //POI检索的监听对象
    OnGetPoiSearchResultListener poiSearchResultListener = new OnGetPoiSearchResultListener() {
        //获得POI的检索结果，一般检索数据都是在这里获取
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {//如果没有错误
                // 会拖累程序速度
                poiList = poiResult.getAllPoi();
                for (int i = 0; i < poiList.size(); i++) {
                    Sports sports = new Sports();
                    sports.setTextTitle(poiList.get(i).name);
                    sports.setTextLocal(poiList.get(i).address);
                    sports.setTextTel(poiList.get(i).phoneNum);
                    sports.setUid(poiList.get(i).uid);
                    mList.add(sports);
                }
                MyTempOverLay temp = new MyTempOverLay(poiSearch, poiResult);
                for (int i = 0; i < poiList.size(); i++) {
                    temp.getDetailPoi(i);
                }
            } else {
                Toast.makeText(getContext(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
            }
        }

        //获得POI的详细检索结果，如果发起的是详细检索，这个方法会得到回调(需要uid)
        @Override
        public void onGetPoiDetailResult(final PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Log.d(TAG, "onGetPoiDetailResult:  error");
            } else {// 正常返回结果的时候，此处可以获得很多相关信息
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            for (int i = 0; i < mList.size(); i++) {
                                Sports sports = mList.get(i);
                                if (sports.getUid().equals(poiDetailResult.getUid())) {
                                    Document doc = Jsoup.connect(poiDetailResult.detailUrl).get();
                                    Element el = doc.select(".meta-img").first();
                                    Element el_img = el.select("img").first();
                                    sports.setImageView(el_img.attr("src"));
                                    sports.setDetailUrl(poiDetailResult.detailUrl);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }

        //获得POI室内检索结果
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    //POI搜索，城市检索
    public void select() {
        //获得Key
        String city = local;
        String key = type + "馆";
        //实例化 poiSearch
        poiSearch = PoiSearch.newInstance();
        //设置Poi监听对象
        poiSearch.setOnGetPoiSearchResultListener(poiSearchResultListener);
        //发起检索
        PoiCitySearchOption poiCity = new PoiCitySearchOption();
        poiCity.keyword(key).city(city);
        poiSearch.searchInCity(poiCity);
    }
}
