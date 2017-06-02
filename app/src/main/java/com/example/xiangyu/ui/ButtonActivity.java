package com.example.xiangyu.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.xiangyu.R;
import com.example.xiangyu.adapter.ButtonAdapter;
import com.example.xiangyu.entity.Button;

import java.util.ArrayList;
import java.util.List;

public class ButtonActivity extends AppCompatActivity {

    private List<Button> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        initButtons();
        ButtonAdapter adapter = new ButtonAdapter(ButtonActivity.this, R.layout.button_item, buttons);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("场地");
        setSupportActionBar(toolbar);
    }

    private void initButtons() {
        Button button = new Button("体校田径基地", "【大桥路】 离你>500m");
        buttons.add(button);
        Button button1 = new Button("大庸桥老年人中心", "【大桥路】 离你>500m");
        buttons.add(button1);
        Button button2 = new Button("老吉大乒乓球室", "【滨河路】 离你>1500m");
        buttons.add(button2);

    }
}
