package com.framgia.vhlee.themoviedb.ui.category;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;

import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.repository.MovieRepository;

public class CategoryViewModel extends BaseObservable {
    private static final String TAG = "CategoryViewModel";
    private static final int DEFAULT_PAGE = 1;
    private String mType;
    private ObservableArrayList<Movie> mMovies;
    private MovieRepository mMovieRepository;

    public CategoryViewModel() {
        mMovies = new ObservableArrayList<>();
        mMovieRepository = MovieRepository.getInstance();
    }

    public void loaMovies() {
    }

    public ObservableArrayList<Movie> getMovies() {
        return mMovies;
    }
}
