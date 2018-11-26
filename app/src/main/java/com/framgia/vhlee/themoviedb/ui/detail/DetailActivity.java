package com.framgia.vhlee.themoviedb.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.framgia.vhlee.themoviedb.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public static Intent getDetailIntent(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        return intent;
    }
}
