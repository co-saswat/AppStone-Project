package com.anshutiwari.telemedico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import common.LoginActivity;
import ui.DashBoardActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView mAppName;
    private static int SPLASH_TIME = 3000;
    private Animation animAppName;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAppName = findViewById(R.id.tv_app_name);
        animAppName = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.app_name);
        mAppName.setAnimation(animAppName);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

                boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);

                if (isFirstTime) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    startActivity(new Intent(SplashActivity.this, OnBoardActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME);
    }
}