package com.example.xiangyu.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.xiangyu.R;
import com.example.xiangyu.adapter.MessageAdapter;
import com.example.xiangyu.entity.Message;
import com.example.xiangyu.global.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XiangYuActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private List<Message> messages = new ArrayList<>();

    private SwipeRefreshLayout swipeRefresh;

    private MessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_yu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view) ;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.own);
        }
        navView.setCheckedItem(R.id.nav_own);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_own:
                        Intent intent = new Intent(MyApplication.getContext(), NavHomePageActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_news:
                        Intent intent2 = new Intent(MyApplication.getContext(), NavNewsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_collection:
                        Intent intent3 = new Intent(MyApplication.getContext(), NavCollectionActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_history:
                        Intent intent4 = new Intent(MyApplication.getContext(), NavHistoryActivity.class);
                        startActivity(intent4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        //recyclerView布局初始化之RecyclerView三部曲
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager LayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(LayoutManager);
        initMessages();
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);
        //为RecyclerView添加HeaderView和FooterView
        setHeaderView(recyclerView);
        setFooterView(recyclerView);
        //下拉刷新的更新操作
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMessages();
            }
        });
        //悬浮按钮的点击事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XiangYuActivity.this, ForumActivity.class);
                startActivity(intent);
            }
        });
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //ViewPager滑动冲突解决
        mViewPager.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float mLastX = 0;

                if (action == MotionEvent.ACTION_DOWN) {
                    // 记录点击到ViewPager时候，手指的X坐标
                    mLastX = event.getX();

                }
                if(action == MotionEvent.ACTION_MOVE) {
                    // 超过阈值
                    if(Math.abs(event.getX() - mLastX) > 31f) {
                        swipeRefresh.setEnabled(false);
                        recyclerView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                if(action == MotionEvent.ACTION_UP) {
                    // 用户抬起手指，恢复父布局状态
                    recyclerView.requestDisallowInterceptTouchEvent(false);
                    swipeRefresh.setEnabled(true);
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);
                break;
            case R.id.place:
                Intent intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.gonglue:
                Intent intent3 = new Intent(this, GongLueActivity.class);
                startActivity(intent3);
            default:
                break;
        }
        return true;
    }

    //初始化RecyclerView内所有信息
    private void initMessages() {
        messages.clear();
        Random random = new Random();
        int index = random.nextInt(3);
        for (int i = index; i < 6; i++) {
            Message tianmenshan = new Message(R.drawable.tianmenshan, "天门山景区，位于张家界市城区南郊8公里，国家5A级旅游区，是山岳型自然景区。总面积96平方公里，山顶面积达2平方公里。天门山景区主峰海拔 1518.6米，是张家界海拔最高的山，古称嵩梁山，又名梦山、方壶山。");
            messages.add(tianmenshan);
            Message daxiaogu = new Message(R.drawable.daxiagu, "张家界大峡谷原来有两个名字，一个叫做烂船峡：来源与神泉溪，整个大峡谷和南方红旗渠的水流都来源于这里，传说以前从泉眼中涌出过很多烂了的船板，当地人们又无法知晓烂船板从何而来，所以这里得名“烂船峡”；另外一个名字叫做乱泉峡：是指峡谷中的两面石壁，溪泉众多，满峡飞流。");
            messages.add(daxiaogu);
            Message huanglongdong = new Message(R.drawable.huanglongdong, "属典型的喀斯特岩溶地貌，被列为国际旅游洞穴会员、全国35个王牌景点之一、中国首批AAAA级旅游区（点）、中国旅游首批知名品牌、湖南省最佳旅游景区、湖南省著名商标、张家界旅游精品线之一，享有绝世奇观之美誉。");
            messages.add(huanglongdong);
            Message xianrenxi = new Message(R.drawable.xianrenxi, "相传曾有人看到仙女在溪涧中淋浴梳妆，故称仙人溪，又名仙女溪。溪涧分布着200余种树木，180多种动物，森林覆盖率达98%左右，空气中的芬多精特别丰富，具有杀菌作用，对治疗疾病有良好的效用，是进行“森林浴”的好去处。");
            messages.add(xianrenxi);
        }
    }

    //刷新recycl
    private void refreshMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initMessages();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.message_header, view, false);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.);
        adapter.setmHeaderView(header);
    }

    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(this).inflate(R.layout.message_footer, view, false);
        adapter.setmFooterView(footer);
    }


}
