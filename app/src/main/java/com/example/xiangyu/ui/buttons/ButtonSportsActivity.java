package com.example.xiangyu.ui.buttons;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.xiangyu.R;
import com.example.xiangyu.adapter.ButtonSportsAdapter;
import com.example.xiangyu.fragment.ButtonSportsFragment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonSportsActivity extends AppCompatActivity {
    public static final String TAG = "TabActivity";
    public static final String []sTitle = new String[]{"篮球", "网球", "游泳", "乒乓球","羽毛球" };
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_sports);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        Intent intent = getIntent();
        local = intent.getStringExtra("local");
        if (local == null) {
            Toast.makeText(getApplicationContext(), "无法获取位置", Toast.LENGTH_SHORT).show();
        }
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[3]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[4]));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG,"onTabSelected:"+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        List<Fragment> fragments = new ArrayList<>();


        fragments.add(ButtonSportsFragment1.newInstance("篮球", local));
        fragments.add(ButtonSportsFragment1.newInstance("网球", local));
        fragments.add(ButtonSportsFragment1.newInstance("游泳", local));
        fragments.add(ButtonSportsFragment1.newInstance("乒乓球", local));
        fragments.add(ButtonSportsFragment1.newInstance("羽毛球", local));

        ButtonSportsAdapter adapter = new ButtonSportsAdapter(getSupportFragmentManager(),fragments, Arrays.asList(sTitle));
        mViewPager.setAdapter(adapter);

//        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG,"select page:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
