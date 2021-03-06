package com.framgia.vhlee.themoviedb.ui.category;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;
import com.framgia.vhlee.themoviedb.databinding.ActivityCategoryBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;
import com.framgia.vhlee.themoviedb.ui.detail.DetailActivity;
import com.framgia.vhlee.themoviedb.ui.search.SearchActivity;
import com.framgia.vhlee.themoviedb.utils.RecyclerScrollController;

public class CategoryActivity extends RecyclerScrollController
        implements CategoryNavigator, MovieAdapter.MovieClickListener {
    private static final String TAG = "CategoryActivity";
    private static final String EXTRA_ARGS = "com.framgia.vhlee.themoviedb.extras.EXTRA_ARGS";
    private static final String BUNDLE_TYPE = "BUNDLE_TYPE";
    private static final String BUNDLE_GENRE = "BUNDLE_GENRE";
    private int mPage;
    private CategoryViewModel mViewModel;
    private ActivityCategoryBinding mBinding;
    private int mSource;

    public static Intent getCategoryIntent(Context context, Genre genre, int source) {
        Intent intent = new Intent(context, CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_GENRE, genre);
        bundle.putInt(BUNDLE_TYPE, source);
        intent.putExtra(EXTRA_ARGS, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        initViewModel();
        initMenu();
        initRecyclerView();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.acitivity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.item_search:
                startActivity(SearchActivity.getSearchIntent(this));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void startDetailActivity(Movie movie) {
        startActivity(DetailActivity.getDetailIntent(this, movie));
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMovieClick(Movie movie) {
        startDetailActivity(movie);
    }

    @Override
    public void onDeleteClick(Movie movie, int position) {
    }

    @Override
    public void loadMore() {
        mIsScrolling = false;
        mProgressBar.setVisibility(View.VISIBLE);
        mViewModel.loaMovies(mSource, ++mPage);
    }

    @Override
    public void initRecyclerView() {
        mProgressBar = mBinding.progressLoadMore;
        mRecycler = mBinding.recyclerMovies;
        mRecycler.setAdapter(new MovieAdapter(this));
        mLinearLayoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();
        setLoadMore();
    }

    private void initViewModel() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_ARGS);
        mPage = 1;
        Genre genre = (Genre) bundle.getSerializable(BUNDLE_GENRE);
        mSource = bundle.getInt(BUNDLE_TYPE);
        getSupportActionBar().setTitle(genre.getName());
        MoviesRepository repository = MoviesRepository.getInstance(
                LocalDataSource.getInstance(getApplicationContext()),
                RemoteDataSource.getInstance());
        mViewModel = new CategoryViewModel(this, repository, genre.getId());
        mBinding.setCategoryVM(mViewModel);
        mViewModel.loaMovies(mSource, mPage);
    }

    private void initMenu() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
