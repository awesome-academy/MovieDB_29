package com.framgia.vhlee.themoviedb.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_ID = "com.framgia.vhlee.themoviedb.extras.EXTRA_ID";
    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mViewModel = new DetailViewModel();
        binding.setDetailVM(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int movieId = getIntent().getIntExtra(EXTRA_ID, 0);
        mViewModel.loadDetail(movieId);
    }

    public static Intent getDetailIntent(Context context, int movieId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ID, movieId);
        return intent;
    }
}
