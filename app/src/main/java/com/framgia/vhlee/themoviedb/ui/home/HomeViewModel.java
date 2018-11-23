package com.framgia.vhlee.themoviedb.ui.home;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;

import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.ui.adapter.HighLightAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;

public class HomeViewModel extends BaseObservable {
    private HighLightAdapter mHighLightAdapter;
    private MovieAdapter mMovieAdapter;
    private ObservableArrayList<Genre> mGenres;

    public HomeViewModel() {
        mHighLightAdapter = new HighLightAdapter();
        mMovieAdapter = new MovieAdapter();
        mGenres = new ObservableArrayList<>();
    }

    public void setHighLightAdapter() {
    }

    public void setMovieAdapter() {
    }

    public void setGenres() {
    }

    public HighLightAdapter getHighLightAdapter() {
        return mHighLightAdapter;
    }

    public MovieAdapter getMovieAdapter() {
        return mMovieAdapter;
    }

    public ObservableArrayList<Genre> getGenres() {
        return mGenres;
    }

    public ObservableArrayList<String> getGenreNames() {
        ObservableArrayList<String> names = new ObservableArrayList<>();
        for (Genre genre : mGenres) {
            names.add(genre.getName());
        }
        return names;
    }
}
