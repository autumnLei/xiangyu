package com.example.xiangyu.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xiangyu.R;
import com.example.xiangyu.adapter.XiangYuAdapter;
import com.example.xiangyu.entity.Message;
import com.example.xiangyu.global.MyApplication;
import com.example.xiangyu.loader.GlideImageLoader;
import com.example.xiangyu.ui.begin.LoginActivity;
import com.example.xiangyu.ui.nav.NavCollectionActivity;
import com.example.xiangyu.ui.nav.NavHistoryActivity;
import com.example.xiangyu.ui.nav.NavNewsActivity;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class XiangYuActivity extends AppCompatActivity implements OnBannerListener {
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
    private XiangYuAdapter adapter;
    Banner banner;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    String[] urls = getResources().getStringArray(R.array.url);
                    List list = Arrays.asList(urls);
                    List arrayList = new ArrayList(list);
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
//                        Intent intent = new Intent(MyApplication.getContext(), NavHomePageActivity.class);
//                        startActivity(intent);
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
        //判断是否登录或注册
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        if (login!=null) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.nav_header, navView);
            ImageView imageView = (ImageView)view.findViewById(R.id.icon_image);
            imageView.setImageResource(R.drawable.touxiang);
            imageView.setClickable(false);

        }
        //recyclerView布局初始化之RecyclerView三部f曲
        GridLayoutManager LayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(LayoutManager);
        initMessages();
        adapter = new XiangYuAdapter(messages);
        recyclerView.setAdapter(adapter);
        //渲染header布局
        View header = LayoutInflater.from(this).inflate(R.layout.xiangyu_header, null);
        banner = (Banner) header.findViewById(R.id.banner);
        banner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApplication.H / 4));
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        //为RecyclerView添加HeaderView和FooterView
        adapter.setmHeaderView(banner);
        //setHeaderView(recyclerView);
        setFooterView(recyclerView);
        setButtonView(recyclerView);

        //启动banner
        banner.setImages(MyApplication.images)
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

    //刷新recyclerview
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

    //初始化RecyclerView内所有信息
    private void initMessages() {
        messages.clear();
        Message tianmenshan = new Message(R.drawable.tianmenshan, "对手变成外国人，上级也保不住孔令辉了\n" +
                "这个端午节孔令辉成了新闻人物，既不是因为乒乓球，也不是因为粽子，而是因为他被一家赌场给起诉了。\n" +
                "中国人对于赌场的印象历来都不太好，尽管也有“小赌怡情”的观念，认为逢年过节不打牌带“响”就缺点什么，但是一旦成了经营类场所就显得不那么讨喜了，通常有“输打赢要”的传说，而民间俗语说“劝赌不劝嫖”，于是赌场的声誉还不如妓院。\n" +
                "而作为公众人物，孔令辉的传说历来分成两部分，一部分是乒乓球赛场，另一部分则是现实而普通的生活。赛场上的孔令辉可说成绩斐然，不要说外国选手，中国选手中能与他比肩者也寥寥可数；但是在普通生活中，孔令辉的对手则有出租车司机、酒店保安之流，这次又多了一个赌场。\n" +
                "2006年7月21日，孔令辉在北京酒后驾驶无牌保时捷跑车与一辆的士相撞；2011年5月，孔令辉在北京一写字楼外边拳打脚踢一名保安。这两次生活中的“遭遇战”，前次由于乒羽中心向交通部门提出申请而躲过行政拘留，后一次则是刘国梁赶到派出所与对方达成了和解。可以说，每次都是上级保护了孔令辉。\n" +
                "但这一次事情有所不同，首先中国乒乓球协会决定暂停孔令辉中国女乒主教练工作，要求其深刻反省，立即从杜塞尔多夫的赛场回国接受进一步调查和处理；之后国家体育总局新闻发言人一方面对此事造成的不良社会影响表示深深的歉意，另一方面表示将在中国乒乓球协会已做出初步决定基础上进一步查明情况，依纪依规做出严肃处理。\n" +
                "什么变了呢？在北京时间“锐评”（微信ID：Btimelun）看来，似乎最大的区别是这次孔令辉的对手变成了外国人。\n" +
                "以往，甭管是被撞的出租车司机还是挨揍的保安，尽管都处于绝对占理的地步，但是仍然处于国内这个江湖中，于是孔令辉既不会赔不起钱，也可以由上级出面帮他尽量躲避行政处罚，而对手们或许不满意，但是往往也没有能力在这个江湖里与孔令辉的上级们角力。\n" +
                "\n" +
                "据《成都晚报》报道，孔令辉酒驾事件后，国乒内部就下达“封口令”；而据《新京报》报道，在打保安事件后，多位保安称当地派出所和大厦物业均已下了“封口令”，这些都并非一次普通酒驾、打架之后的能否见到的情况。\n" +
                "而这一次之所以不同，恐怕某种程度上因为起诉的是新加坡赌场，起诉地点又在香港，尽管这个赌场并不绝对占理，甚至有蹭热点、搞事情的嫌疑，但是孔令辉的上级们会发现，以前的江湖规矩在对方身上不管用了。\n" +
                "甭管是乒羽中心还是体育总局，想要对新加坡赌场下达“封口领”恐怕只能自取其辱，至于向香港法院提交申请也只能碰一鼻子灰。于是处理孔令辉似乎成为目前唯一可做的事情。\n" +
                "对此，有人替乒羽中心叫好，有人认为体育总局处理有进步，不过也有人质疑临阵易帅对比赛的影响，认为应该让孔令辉先完成比赛任务。但是如果不这样处理，乒羽中心和体育总局还能怎么办呢？让孔令辉继续带队遭受质疑？让外媒继续质疑上级们对孔令辉包庇？让事情的热度压过比赛？这已经是目前最优的选择。\n" +
                "那么回看以往的处理，或许从乒羽中心、体育总局看来当时的力保无可厚非，但是很多人都诟病这与孔令辉的屡屡“出事”有所关联，甚至怀疑这种“力保”是不是反而助长了孔令辉的某种心理，实际上成了“挖坑”的行为。\n" +
                "所幸，在这次关于孔令辉的纠纷中，上级们第一次不再用“力保”的方式来处理，孔令辉本人自然是服从，舆论主流上也对这种处理表示支持理解，那么看来这样的处理方式不妨延续下去、拓展开来，对于各路体育明星能够依法、依规甚至提升到高于普通民众的标准来要求和对待，或许未来这种事情能少一些，让体育明星们能多点自律，也是可以期待的了。\n");
        messages.add(tianmenshan);
        Message daxiaogu = new Message(R.drawable.daxiagu, "亚冠16强中超德比苏宁VS上港，实力竟不是胜负关键！\n" +
                "什么？端午节已经结束了！不！能！接！受！\n" +
                "由于端午放假，本周是从周三开始。前几天我们经历了各国多场升降级附加赛，见证了沃尔夫斯堡的德甲保级，哈德斯菲尔德的英冠升级，慕尼黑1860的德乙降级，多少欢笑和泪水，多少开怀与辛酸，足球就是如此美丽而残酷，人生也是如此。\n" +
                "猫的薛定谔】对亚冠的中超德比非常感兴趣，但也很纠结。因为关注中超的朋友都知道苏宁对亚冠的重视程度很高，遗憾的是他们的对手上海上港本赛季也很强，今晚比赛结束，必然会有一支中超球队离开亚冠赛场。\n" +
                "会是谁呢，一起来看看DS足球小伙伴【Ayase】怎么说。\n" +
                "江苏苏宁VS上海上港\n" +
                "【Ayase】：DS足球版主，玩球4年有余，惯常以球队定位和预估投注分布结合欧洲赔率对比赛结果进行预测。主攻方向：欧洲联赛。\n" +
                "苏宁在首回合客场比赛中仅仅1球小负，让目前陷入低谷的球队在亚冠的路上还有不小的希望能够前进。\n" +
                "上轮联赛中，苏宁客场雪藏部分主力，导致球队连贵州也未能拿下（2:2）。\n" +
                "\n" +
                "虽然盘面较为正常，看除了威廉希尔给出2.1的客胜外，其余公司均对上港持怀疑态度。谨慎著称的interwetten初盘竟开出客胜2.45。显然上港一方没有那么大的优势。\n" +
                "所以本场比赛的胜负关键很可能是战意和态度，而非球队硬实力，看好苏宁主场不败，3球大，单博平。比分预计2:2\n" +
                "\n");
        messages.add(daxiaogu);
        Message huanglongdong = new Message(R.drawable.huanglongdong, "它是专家眼中的黄金有氧运动!这么多好处你都知道吗?\n" +
                "核心提示:不少人喜欢骑行或者骑自行车上班，不但可以避免交通路况，自由性更强，可以随意观看沿途的风景。\n");
        messages.add(huanglongdong);
        Message kkk = new Message(R.drawable.huanglongdong, "属典型的喀斯特岩溶地貌，被列为国际旅游洞穴会员、全国35个王牌景点之一、中国首批AAAA级旅游区（点）、中国旅游首批知名品牌、湖南省最佳旅游景区、湖南省著名商标、张家界旅游精品线之一，享有绝世奇观之美誉。");
        messages.add(huanglongdong);

    }

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
                startActivity(intent1);
                break;
            case R.id.btn_2:
                Intent intent2 = new Intent(this, ButtonActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_3:
                Intent intent3 = new Intent(this, ButtonActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_4:
                Intent intent4 = new Intent(this, ButtonActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_5:
                Intent intent5 = new Intent(this, ButtonActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_6:
                Intent intent6 = new Intent(this, ButtonActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_7:
                Intent intent7 = new Intent(this, ButtonActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn_8:
                Intent intent8 = new Intent(this, ButtonActivity.class);
                startActivity(intent8);
                break;
            default:
                break;
        }
    }
}
