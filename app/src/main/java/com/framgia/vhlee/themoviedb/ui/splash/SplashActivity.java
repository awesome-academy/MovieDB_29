package com.framgia.vhlee.themoviedb.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.framgia.vhlee.themoviedb.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.getMainIntent(SplashActivity.this));
                finish();
            }
        });
    }
}
