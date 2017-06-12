package com.example.xiangyu.ui.buttons;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.xiangyu.R;
import com.example.xiangyu.adapter.RaceAdapter;
import com.example.xiangyu.entity.Race;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class RaceActivity extends AppCompatActivity{

    private List<Race> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        initRace();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        RaceAdapter adapter = new RaceAdapter(mList);
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

    private void initRace() {
        Race Race1 = new Race(R.drawable.race1, "张家界市民乒乓球友谊赛\n\n" +
                "比赛时间：2017年7月25日\n" +
                "报名截止：2017年7月10日\n");
        mList.add(Race1);
        Race Race2 = new Race(R.drawable.race2, "千人徙步登天门（张家界）\n\n" +
                "比赛时间：2017年10月28日（重阳节）\n" +
                "报名截止：2017年10月10日\n");
        mList.add(Race2);
        Race Race3 = new Race(R.drawable.race3, "快乐骑行～梅溪湖（长沙）\n\n" +
                "活动时间：2017年9月15日\n" +
                "报名截止：2017年7月15日\n");
        mList.add(Race3);
        Race Race4 = new Race(R.drawable.race4, "第十一届全国少数民族传统体育运动会选拔赛（吉首）\n\n" +
                "比赛时间：（待定）\n" +
                "报名截止：（待定）\n");
        mList.add(Race4);
    }
}
