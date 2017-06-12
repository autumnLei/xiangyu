package com.example.xiangyu.ui;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiangyu.R;

public class MessageActivity extends AppCompatActivity {

    public static final String MESSAGE_TITLE = "message_name";

    public static final String MESSAGE_CONTENT = "message_content";

    public static final String MESSAGE_IMAGE_URL = "message_image_url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        String title = intent.getStringExtra(MESSAGE_TITLE);
        String content = intent.getStringExtra(MESSAGE_CONTENT);
        String image = intent.getStringExtra(MESSAGE_IMAGE_URL);
        //分别找到对应id
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView messageImage = (ImageView) findViewById(R.id.message_image);
        TextView messageContent = (TextView) findViewById(R.id.message_content);
        //设置toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //分别设置
        collapsingToolbar.setTitle(title);
        Glide.with(this).load(R.drawable.avatar0).into(messageImage);       //暂时还没图片 所以设置为默认图
        messageContent.setText(generateMessageContent(content));
    }

    private String generateMessageContent(String messageName) {
        StringBuilder messageContent = new StringBuilder();

            messageContent.append(messageName);

        return messageContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}