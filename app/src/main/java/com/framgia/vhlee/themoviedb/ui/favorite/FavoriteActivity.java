package com.framgia.vhlee.themoviedb.ui.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.vhlee.themoviedb.R;

public class FavoriteActivity extends AppCompatActivity {

    public static Intent getFavoriteIntent(Context context) {
        return new Intent(context, FavoriteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
    }
}
