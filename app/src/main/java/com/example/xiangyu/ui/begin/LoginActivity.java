package com.example.xiangyu.ui.begin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xiangyu.R;
import com.example.xiangyu.ui.XiangYuActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.accountEdit)
    EditText accountEdit;
    @InjectView(R.id.passwordEdit)
    EditText passwordEdit;
    @InjectView(R.id.btn_login)
    AppCompatButton btnLogin;
    @InjectView(R.id.link_signup)
    TextView linkSignup;
    @InjectView(R.id.traveler)
    TextView traveler;
    @InjectView(R.id.imageview)
    ImageView imageview;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenByCode();
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        //从SharedPreferrences中读取账号密码
        String account = pref.getString("account", "");
        String password = pref.getString("password", "");
        accountEdit.setText(password);
        passwordEdit.setText(account);
        Glide.with(this)
                .load(R.drawable.splash)
                .centerCrop()
                .into(imageview);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //如果账号密码是admin 且密码是123456 就认为登录成功
                if (account.equals("admin") && password.equals("123456")) {
                    editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putString("account", account);
                    editor.putString("password", password);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, XiangYuActivity.class);
                    intent.putExtra("login", "ok");
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
        traveler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, XiangYuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();
            }
        });
        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();
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
