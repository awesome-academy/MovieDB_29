package com.framgia.vhlee.themoviedb.service;

import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientRequest {
    @GET("genre/movie/list")
    Observable<GenresResponse> getGenre(@Query("api_key") String ApiKey);

    @GET("trending/movie/day")
    Observable<MovieResponse> getMoviesHighLight(@Query("api_key") String ApiKey);

    @GET("discover/movie")
    Observable<MovieResponse> getMoviesByGenre(@Query("api_key") String ApiKey,
                                               @Query("with_genres") String idGenre,
                                               @Query("page") int page);
}
