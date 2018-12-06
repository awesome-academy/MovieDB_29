package com.framgia.vhlee.themoviedb.ui.favorite;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;
import com.framgia.vhlee.themoviedb.databinding.ActivityFavoriteBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;
import com.framgia.vhlee.themoviedb.ui.detail.DetailActivity;

public class FavoriteActivity extends AppCompatActivity
        implements MovieAdapter.MovieClickListener {
    private ActivityFavoriteBinding mBinding;
    private MovieAdapter mMovieAdapter;

    public static Intent getFavoriteIntent(Context context) {
        return new Intent(context, FavoriteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);
        initViewModel();
        initAdapter();
    }

    private void initAdapter() {
        RecyclerView recycler = mBinding.recyclerFavorite;
        mMovieAdapter = new MovieAdapter(this);
        mMovieAdapter.setFavorite(true);
        recycler.setAdapter(mMovieAdapter);
    }

    private void initViewModel() {
        MoviesRepository repository = MoviesRepository.getInstance(
                LocalDataSource.getInstance(getApplicationContext()),
                RemoteDataSource.getInstance());
        FavoriteViewModel viewModel = new FavoriteViewModel(repository);
        mBinding.setFavoriteVM(viewModel);
        viewModel.loadFavorite();
    }

    @Override
    public void onMovieClick(Movie movie) {
        startActivity(DetailActivity.getDetailIntent(this, movie));
    }

    @Override
    public void onDeleteClick(Movie movie, int position) {
        if (mBinding.getFavoriteVM().removeFavorite(movie)) {
            mMovieAdapter.delete(position);
            mBinding.getFavoriteVM().setCount(mMovieAdapter.getItemCount());
        }
    }
}
