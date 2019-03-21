package com.example.appmusic.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.appmusic.Adapter.LoginAdapter;
import com.example.appmusic.Fragment.Fragment_Login;
import com.example.appmusic.Fragment.Fragment_Register;
import com.example.appmusic.R;

public class LoginActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        init();
    }

    private void addControls() {
        tablayout = findViewById(R.id.tablayoutlogin);
        viewPager = findViewById(R.id.viewpagerlogin);
        toolbar = findViewById(R.id.toolbarlogin);

    }

    private void init(){
        //Set up ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng nhập/Đăng ký");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        //Set up adapter
        LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager());
        loginAdapter.addFragment(new Fragment_Login(),"Đăng nhập");
        loginAdapter.addFragment(new Fragment_Register(), "Đăng ký");
        viewPager.setAdapter(loginAdapter);

        //Set up Tab Layout
        tablayout.setupWithViewPager(viewPager);

    }
}
