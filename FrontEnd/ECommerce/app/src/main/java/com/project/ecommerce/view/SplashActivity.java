package com.project.ecommerce.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.project.ecommerce.R;
import com.project.ecommerce.utils.LoginUtils;


public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, ProductActivity.class);
            startActivity(i);
            finish();
            if(!LoginUtils.getInstance(SplashActivity.this).isLoggedIn()) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, SPLASH_TIME_OUT);
    }
}
