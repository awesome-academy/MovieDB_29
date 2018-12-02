package com.framgia.vhlee.themoviedb.ui.category;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;
import com.framgia.vhlee.themoviedb.databinding.ActivityCategoryBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;
import com.framgia.vhlee.themoviedb.ui.detail.DetailActivity;

public class CategoryActivity extends AppCompatActivity
        implements CategoryNavigator, MovieAdapter.MovieClickListener {
    private static final String EXTRA_ARGS = "com.framgia.vhlee.themoviedb.extras.EXTRA_ARGS";
    private static final String BUNDLE_TYPE = "BUNDLE_TYPE";
    private static final String BUNDLE_GENRE = "BUNDLE_GENRE";
    private CategoryViewModel mViewModel;
    private ActivityCategoryBinding mBinding;

    public static Intent getCategoryIntent(Context context, Genre genre, boolean isGenre) {
        Intent intent = new Intent(context, CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_GENRE, genre);
        bundle.putBoolean(BUNDLE_TYPE, isGenre);
        intent.putExtra(EXTRA_ARGS, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_category);
        initViewModel();
        initAdapter();
        mBinding.setCategoryVM(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.destroy();
    }

    @Override
    public void startDetailActivity(Movie movie) {
        startActivity(DetailActivity.getDetailIntent(this, movie));
    }

    private void initViewModel() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_ARGS);
        Genre genre = (Genre) bundle.getSerializable(BUNDLE_GENRE);
        MoviesRepository repository = MoviesRepository.getInstance(
                LocalDataSource.getInstance(getApplicationContext()),
                RemoteDataSource.getInstance());
        mViewModel = new CategoryViewModel(this, repository, genre.getId());
        mViewModel.loaMovies(bundle.getBoolean(BUNDLE_TYPE));
    }

    private void initAdapter() {
        RecyclerView recycler = mBinding.recyclerMovies;
        recycler.setAdapter(new MovieAdapter(this));
    }

    @Override
    public void onMovieClick(Movie movie) {
        startDetailActivity(movie);
    }
}
