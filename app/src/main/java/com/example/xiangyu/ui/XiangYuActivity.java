package com.example.xiangyu.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.xiangyu.R;
import com.example.xiangyu.adapter.XiangYuAdapter;
import com.example.xiangyu.entity.Message;
import com.example.xiangyu.global.MyApplication;
import com.example.xiangyu.loader.GlideImageLoader;
import com.example.xiangyu.ui.begin.LoginActivity;
import com.example.xiangyu.ui.buttons.ButtonSportsActivity;
import com.example.xiangyu.ui.buttons.PictureActivity;
import com.example.xiangyu.ui.buttons.RaceActivity;
import com.example.xiangyu.ui.nav.NavAboutActivity;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class XiangYuActivity extends AppCompatActivity implements OnBannerListener {
    String TAG = "555";
    static final int REFRESH_COMPLETE = 0X1112;
    static final boolean LOGIN = true;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.nav_view)
    NavigationView navView;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private List<Message> messages = new ArrayList<>();
    private List<String> pictures = new ArrayList<>();
    private XiangYuAdapter adapter;
    private LocationClient mLocationClient;
    private String local;
    Banner banner;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    List arrayList = new ArrayList(pictures);
                    //把新的图片地址加载到Banner
                    banner.update(arrayList);
                    //下拉刷新控件隐藏
                    swipeRefresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_yu);
        ButterKnife.inject(this);
        //获取权限和位置
        getPersimmilns();
        //toolba设置
        setSupportActionBar(toolbar);
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
                        Intent intent = new Intent(MyApplication.getContext(), SearchActivity.class);
                        intent.putExtra("Url", "https://github.com/autumnLei/LSports.git");
                        startActivity(intent);
                        break;
                    case R.id.nav_about:
                        Intent intent2 = new Intent(MyApplication.getContext(), NavAboutActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_feedback:
                        Intent intent4 = new Intent(MyApplication.getContext(), SearchActivity.class);
                        intent4.putExtra("Url", "https://github.com/autumnLei/LSports/issues");
                        startActivity(intent4);
                        break;
                    case R.id.nav_quit:
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        //判断是否登录或注册
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        if (login != null) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.nav_header, navView);
            ImageView imageView = (ImageView) view.findViewById(R.id.icon_image);
            imageView.setImageResource(R.drawable.touxiang);
            imageView.setClickable(false);
        }
        //填充button所占的位置
//        if (savedInstanceState.getStringArrayList("") != null){
//            messages = savedInstanceState.get;
//        } else {
            messages.add(new Message());
//        }
        //recyclerView布局初始化之RecyclerView三部f曲  第三部移到定位函数里
        GridLayoutManager LayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(LayoutManager);
        adapter = new XiangYuAdapter(messages);
        recyclerView.setAdapter(adapter);
        //渲染header布局
        View header = LayoutInflater.from(this).inflate(R.layout.xiangyu_header, null);
        banner = (Banner) header.findViewById(R.id.banner);
        banner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApplication.H / 4));
//        banner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(XiangYuActivity.this, "hello", Toast.LENGTH_SHORT).show();
//            }
//        });
        //为RecyclerView添加HeaderView和FooterView
        adapter.setmHeaderView(banner);
        //setHeaderView(recyclerView);
//        setFooterView(recyclerView);
        setButtonView(recyclerView);

        //启动banner //array列表 MyApplication.images
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.race1);
        list.add(R.drawable.race2);
        list.add(R.drawable.race3);
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setBannerAnimation(Transformer.DepthPage)
                .start();
        //下拉刷新的更新操作
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMessages();
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
            }
        });
        //悬浮按钮的点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XiangYuActivity.this, ForumActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取位置的几个函数
     */
    private void getPersimmilns() {
        ArrayList<String> permissionList = new ArrayList<>();
        // 定位精确位置
        if (ContextCompat.checkSelfPermission(XiangYuActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(XiangYuActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        // 读写权限
        if (ContextCompat.checkSelfPermission(XiangYuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(XiangYuActivity.this, permissions, 1);
        }
        if (local == null) {
            requestLocation();
        }
    }

    private void requestLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationlistener());
        //告诉百度需要获取所在城市
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必需同意所有权限才能正常使用", Toast.LENGTH_SHORT).show();
                            local = "长沙市";
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationlistener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            local = bdLocation.getCity();
            Log.d("1111", "onReceiveLocation:" + bdLocation.getCity());
            initMessages();
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 复写activity的方法
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_image:
//                Intent intent3 = new Intent(this, LoginActivity.class);
//                startActivity(intent3);
//                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            case R.id.search:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);
                break;
            case R.id.local:
                Intent intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 刷新recyclerview
     */
    private void refreshMessages() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        adapter.notifyDataSetChanged();
//                        swipeRefresh.setRefreshing(false);
//                    }
//                });
//            }
//        }).start();
        initBanner();
    }

    //初始化RecyclerView内所有信息
    private void initMessages() {
        if (local == null) {
            Toast.makeText(XiangYuActivity.this, "正在加载中", Toast.LENGTH_LONG).show();
        }
        messages.clear();
        new Thread() {
            @Override
            public void run() {
                super.run();
                String shortLocal = null;
                if (local != null) {
                    shortLocal = local.substring(0, local.length() - 1);
                }
                Log.d(TAG, "run: " + shortLocal);
                try {
                    Document doc = Jsoup.connect("https://www.douban.com/search?cat=1015&q=" + shortLocal).get();
                    Log.d(TAG, "run: 连接豆瓣正常");
                    Elements els = doc.select("div.content");
                    for (int i = 0; i < els.size(); i++) {
                        Message message = new Message();
                        Element el = els.get(i);
//                        Log.d(TAG, "run: " + el.select("a").attr("href"));
                        message.setTitle(el.select("a").first().text());
//                        Log.d(TAG, "run: "+el.select("a").text());
                        message.setText(el.select("p").text());
                        message.setContent(el.select("a").attr("href"));
                        messages.add(message);
                    }
                    Log.d(TAG, "run: " + "douban nomal");
                    //                给每个item添加图片
                    Document doc_pic = Jsoup.connect("http://www.ivsky.com/search.php?q=" + shortLocal + "&PageNo=2").get();
                    Element els_temp = doc_pic.select("ul.pli").first();
                    Elements els_pic = els_temp.select("li");
                    for (int i = 0; i < els_pic.size(); i++) {
                        if (i > messages.size()){
                            break;
                        }
                        Element el_pic = els_pic.get(i).select("img").first();
                        messages.get(i).setIamge(el_pic.attr("src"));
                    }
                } catch (IOException e) {
                    Log.d(TAG, "run: " + e.toString());
                }
            }
        }.start();
    }

    public void initBanner() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //                给banner爬取数据
                pictures.clear();
                String shortLocal = null;
                if (local != null) {
                    shortLocal = local.substring(0, local.length() - 1);
                }
                try {
                    Document doc_add = Jsoup.connect("http://www.ivsky.com/search.php?q=" + shortLocal).get();
                    Element els_temp2 = doc_add.select("ul.pli").first();
                    Elements els_add = els_temp2.select("li");
                    for (int i = 0; i < 6; i++) {
                        Element el_add = els_add.get(i).select("a").first();
                        Document doc_detail = Jsoup.connect("http://www.ivsky.com/" + el_add.attr("href")).get();
                        Element el_detail = doc_detail.select("div.left").first();
                        pictures.add(el_detail.select("img").first().attr("src"));
                    }
                } catch (Exception e) {
                }
            }

        }.start();
    }


    /**
     * 设置recyclerview内内容
     *
     * @param view
     */
    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(this).inflate(R.layout.xiangyu_footer, view, false);
        adapter.setmFooterView(footer);
    }

    private void setButtonView(RecyclerView view) {
        View button = LayoutInflater.from(this).inflate(R.layout.xiangyu_header_button, view, false);
        adapter.setmButtonView(button);
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(XiangYuActivity.this, "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }

    public void Login(View view) {
        switch (view.getId()) {
            case R.id.icon_image:
                if (LOGIN) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_1:
                Intent intent1 = new Intent(this, ButtonSportsActivity.class);
                intent1.putExtra("local", local);
                Log.d("789", "Login: " + local);
                startActivity(intent1);
                break;
            case R.id.btn_2:
                Intent intent2 = new Intent(this, PictureActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_3:
                Intent intent3 = new Intent(this, RaceActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_4:
                Toast.makeText(this, "暂未开放", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
