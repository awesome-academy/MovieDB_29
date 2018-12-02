package com.framgia.vhlee.themoviedb.data.source;

import android.databinding.ObservableArrayList;

import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;

import io.reactivex.Observable;

public interface DataSource {
    interface Local {
        int getCount();

        ObservableArrayList<Movie> getMovies();

        Movie getMovie(int id);

        boolean insert(Movie movie);

        boolean delete(Movie movie);

        boolean isFavourite(Movie movie);
    }

    interface Remote {
        Observable<GenresResponse> getGenre();

        Observable<MovieResponse> getMoviesByCategory(String type, int page);

        Observable<MovieResponse> getMoviesByGenre(String id, int page);

        Observable<MovieResponse> getHighLightMovies();

        Observable<Movie> getMovieDetail(int id, String append);
    }
}
