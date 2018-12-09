package com.framgia.vhlee.themoviedb.data.repository;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.support.annotation.Nullable;

import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;
import com.framgia.vhlee.themoviedb.data.source.DataSource;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

public class MoviesRepository implements DataSource.Remote, DataSource.Local {
    private static MoviesRepository sRepository;
    private LocalDataSource mLocal;
    private RemoteDataSource mRemote;

    private MoviesRepository(LocalDataSource local, RemoteDataSource remote) {
        mLocal = local;
        mRemote = remote;
    }

    public static synchronized MoviesRepository getInstance(@Nullable LocalDataSource local,
                                                            @Nullable RemoteDataSource remote) {
        if (sRepository == null) {
            sRepository = new MoviesRepository(local, remote);
        }
        return sRepository;
    }

    @Override
    public Observable<GenresResponse> getGenre() {
        return mRemote.getGenre();
    }

    @Override
    public Observable<MovieResponse> getMoviesByCategory(String type, int page) {
        return mRemote.getMoviesByCategory(type, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByGenre(String id, int page) {
        return mRemote.getMoviesByGenre(id, page);
    }

    @Override
    public Observable<MovieResponse> getHighLightMovies() {
        return mRemote.getHighLightMovies();
    }

    @Override
    public Observable<Movie> getMovieDetail(int id, String append) {
        return mRemote.getMovieDetail(id, append);
    }

    @Override
    public Observable<MovieResponse> searchByMovie(String type, String keyword, int page) {
        return mRemote.searchByMovie(type, keyword, page);
    }

    @Override
    public int getCount() {
        return mLocal.getCount();
    }

    @Override
    public ObservableArrayList<Movie> getMovies() {
        return mLocal.getMovies();
    }

    @Override
    public Movie getMovie(int id) {
        return mLocal.getMovie(id);
    }

    @Override
    public boolean insert(Movie movie) {
        return mLocal.insert(movie);
    }

    @Override
    public boolean delete(Movie movie) {
        return mLocal.delete(movie);
    }

    @Override
    public boolean isFavourite(Movie movie) {
        return mLocal.isFavourite(movie);
    }
}
