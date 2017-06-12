package com.example.xiangyu.ui.buttons;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.xiangyu.R;
import com.example.xiangyu.adapter.FrendAdapter;
import com.example.xiangyu.entity.Frend;

import java.util.ArrayList;
import java.util.List;

public class FrendActivity extends AppCompatActivity {

    private List<Frend> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        initFrend();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FrendAdapter adapter = new FrendAdapter(mList);
        recyclerView.setAdapter(adapter);
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

    private void initFrend() {
        Frend coach1 = new Frend(R.drawable.touxiang, "dsklf", "lanqi", "签名：带你飞");
        mList.add(coach1);
        Frend coach2 = new Frend(R.drawable.touxiang, "夏煊泽", "中国羽毛球队教练", "签名：世上无难事，只要肯登攀");
        mList.add(coach2);
        Frend coach3 = new Frend(R.drawable.touxiang, "张宁", "中国羽毛球队教练", "签名：不求做的最好，但求做的更好");
        mList.add(coach3);
        Frend coach4 = new Frend(R.drawable.touxiang, "张军", "中国羽毛球队教练", "签名：勤奋是学习的枝叶，当然很苦，智慧是学习的花朵，当然香郁。");
        mList.add(coach4);
    }
}
