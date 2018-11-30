package com.framgia.vhlee.themoviedb.data.source;

import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;

import io.reactivex.Observable;

public interface DataSource {
    interface Local {
    }

    interface Remote {
        Observable<GenresResponse> getGenre();

        Observable<MovieResponse> getMoviesByCategory(String type, int page);

        Observable<MovieResponse> getMoviesByGenre(String id, int page);

        Observable<MovieResponse> getHighLightMovies();

        Observable<Movie> getMovieDetail(int id, String append);
    }
}
