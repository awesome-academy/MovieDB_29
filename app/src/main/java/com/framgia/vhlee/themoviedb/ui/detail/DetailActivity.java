package com.framgia.vhlee.themoviedb.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.CategoryRequest;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.Video;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;
import com.framgia.vhlee.themoviedb.databinding.ActivityDetailBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.GenreAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.VideoAdapter;
import com.framgia.vhlee.themoviedb.ui.category.CategoryActivity;
import com.framgia.vhlee.themoviedb.ui.dialog.BottomSheetDialog;
import com.framgia.vhlee.themoviedb.ui.search.SearchActivity;
import com.google.android.youtube.player.YouTubePlayer;

public class DetailActivity extends AppCompatActivity
        implements DetailNavigator, YouTubePlayer.OnFullscreenListener,
        GenreAdapter.GenreClickListener, VideoAdapter.VideoClickListener {
    private static final String TAG = "DetailActivity";
    private static final String TAG_BOTTOM_SHEET = "BottomSheet";
    private static final String EXTRA_ID = "com.framgia.vhlee.themoviedb.extras.EXTRA_ID";
    private DetailViewModel mViewModel;
    private ActivityDetailBinding mBinding;
    private VideoFragment mVideoFragment;

    public static Intent getDetailIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ID, movie.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        MoviesRepository repository = MoviesRepository.getInstance(
                LocalDataSource.getInstance(getApplicationContext()),
                RemoteDataSource.getInstance());
        mViewModel = new DetailViewModel(this, repository);
        mBinding.setDetailVM(mViewModel);
        initAdapter();
        initMenu();
        initYoutubePlayerView();
        configLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int movieId = getIntent().getIntExtra(EXTRA_ID, 0);
        mViewModel.loadDetail(movieId);
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
    public void startCategoryActivity(Genre genre, int source) {
        startActivity(CategoryActivity.getCategoryIntent(this, genre, source));
    }

    @Override
    public void showBottomSheet(Movie movie, boolean isCast) {
        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.newInstance(movie, isCast);
        bottomSheetDialog.show(getSupportFragmentManager(), TAG_BOTTOM_SHEET);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        configLayout();
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        configLayout();
    }

    @Override
    public void onGenreClick(Genre genre) {
        startCategoryActivity(genre, CategoryRequest.GENRE);
    }

    @Override
    public void onVideoClick(Video video) {
        mVideoFragment.getView().setVisibility(View.VISIBLE);
        mVideoFragment.setVideoId(video.getKey(), this);
        mVideoFragment.play();
    }

    private void initAdapter() {
        RecyclerView recyclerGenres = mBinding.viewGenres.recyclerGenresLabel;
        RecyclerView recyclerTrailers = mBinding.viewTrailers.recyclerTrailers;
        recyclerGenres.setAdapter(new GenreAdapter(this));
        recyclerTrailers.setAdapter(new VideoAdapter(this));
    }

    private void initYoutubePlayerView() {
        mVideoFragment =
                (VideoFragment) getFragmentManager().findFragmentById(R.id.fragment_youtube_view);
        mVideoFragment.getView().setVisibility(View.GONE);
    }

    private void configLayout() {
        //TODO Configure layout changes
    }

    private void initMenu() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
