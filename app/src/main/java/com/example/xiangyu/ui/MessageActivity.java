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

    public static final String MESSAGE_NAME = "message_name";

    public static final String MESSAGE_IMAGE_ID = "message_image_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        String messageName = intent.getStringExtra(MESSAGE_NAME);
        int messageImgeId = intent.getIntExtra(MESSAGE_IMAGE_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView messageImageView = (ImageView) findViewById(R.id.message_image_view);
        TextView messageContentText = (TextView) findViewById(R.id.message_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(messageName);
        Glide.with(this).load(messageImgeId).into(messageImageView);
        String messageContent = generateMessageContent(messageName);
        messageContentText.setText(messageContent);
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