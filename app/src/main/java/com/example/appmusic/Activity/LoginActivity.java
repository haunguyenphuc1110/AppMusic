package com.example.appmusic.Activity;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appmusic.Adapter.LoginAdapter;
import com.example.appmusic.Fragment.Fragment_Login;
import com.example.appmusic.R;

public class LoginActivity extends AppCompatActivity {

    ViewPager viewPager;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        init();
        addEvents();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    private void addControls() {
        viewPager = findViewById(R.id.viewpagerlogin);
        btnBack = findViewById(R.id.btnloginback);

    }

    private void init(){
        //Set up adapter
        LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager());
        loginAdapter.addFragment(new Fragment_Login(),"Đăng nhập");
        viewPager.setAdapter(loginAdapter);

    }
}
