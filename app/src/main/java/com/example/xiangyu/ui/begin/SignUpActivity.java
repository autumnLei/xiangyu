package com.example.xiangyu.ui.begin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xiangyu.R;
import com.example.xiangyu.ui.XiangYuActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity {

    @InjectView(R.id.imageview)
    ImageView imageview;
    @InjectView(R.id.accountEdit)
    EditText accountEdit;
    @InjectView(R.id.passwordEdit)
    EditText passwordEdit;
    @InjectView(R.id.btn_login)
    AppCompatButton btnLogin;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenByCode();
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);
        Glide.with(this)
                .load(R.drawable.splash)
                .centerCrop()
                .into(imageview);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, XiangYuActivity.class);
                    intent.putExtra("login", "ok");
                    startActivity(intent);
                    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                    finish();
                }
            }
        });
    }


    private void setFullScreenByCode() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
