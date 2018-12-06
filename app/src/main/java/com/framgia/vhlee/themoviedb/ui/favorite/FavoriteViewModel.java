package com.framgia.vhlee.themoviedb.ui.favorite;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.util.Log;

import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;

public class FavoriteViewModel {
    private static final String TAG = "FavoriteViewModel";
    private ObservableInt mCount;
    private ObservableArrayList<Movie> mMovies;
    private MoviesRepository mMoviesRepository;

    public FavoriteViewModel(MoviesRepository moviesRepository) {
        mCount = new ObservableInt();
        mMovies = new ObservableArrayList<>();
        mMoviesRepository = moviesRepository;
    }

    public void loadFavorite() {
        mCount.set(mMoviesRepository.getCount());
        mMovies = mMoviesRepository.getMovies();
    }

    public ObservableArrayList<Movie> getMovies() {
        return mMovies;
    }

    public ObservableInt getCount() {
        return mCount;
    }
}
