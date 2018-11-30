package com.framgia.vhlee.themoviedb.utils;

import android.databinding.BaseObservable;

import com.framgia.vhlee.themoviedb.data.model.Movie;

public class MovieViewModel extends BaseObservable {
    private Movie mMovie;

    public MovieViewModel() {
        mMovie = new Movie();
    }

    public String getBackdropPath() {
        return mMovie.getBackdropPath();
    }

    public String getTitle() {
        return mMovie.getTitle();
    }

    public String getReleaseDate() {
        return mMovie.getReleaseDate();
    }

    public String getStatus() {
        return mMovie.getStatus();
    }

    public float getVoteAverage() {
        return mMovie.getVoteAverage();
    }

    public String getOverview() {
        return mMovie.getOverview();
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        notifyChange();
    }

    public Movie getMovie() {
        return mMovie;
    }
}
