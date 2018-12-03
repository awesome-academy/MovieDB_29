package com.framgia.vhlee.themoviedb.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.databinding.ActivityDetailBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.GenreAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.VideoAdapter;
import com.framgia.vhlee.themoviedb.ui.category.CategoryActivity;
import com.framgia.vhlee.themoviedb.ui.dialog.BottomSheetDialog;

public class DetailActivity extends AppCompatActivity implements DetailNavigator {
    private static final String TAG_BOTTOM_SHEET = "BottomSheet";
    private static final String EXTRA_ID = "com.framgia.vhlee.themoviedb.extras.EXTRA_ID";
    private DetailViewModel mViewModel;
    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mViewModel = new DetailViewModel(this);
        mBinding.setDetailVM(mViewModel);
        initAdapter();
    }

    private void initAdapter() {
        RecyclerView recyclerGenres = mBinding.viewGenres.recyclerGenresLabel;
        RecyclerView recyclerTrailers = mBinding.viewTrailers.recyclerTrailers;
        recyclerGenres.setAdapter(new GenreAdapter());
        recyclerTrailers.setAdapter(new VideoAdapter());
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

    @Override
    public void startCategoryActivity(Bundle bundle) {
        startActivity(CategoryActivity.getCategoryIntent(this, bundle));
    }

    @Override
    public void showBottomSheet(Movie movie, boolean isCast) {
        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.newInstance(movie, isCast);
        bottomSheetDialog.show(getSupportFragmentManager(), TAG_BOTTOM_SHEET);
    }
}
