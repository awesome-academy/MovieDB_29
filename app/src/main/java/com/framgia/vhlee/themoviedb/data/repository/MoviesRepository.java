package com.framgia.vhlee.themoviedb.data.repository;

import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;
import com.framgia.vhlee.themoviedb.data.source.DataSource;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;

import io.reactivex.Observable;

public class MoviesRepository implements DataSource.Remote, DataSource.Local {
    private static MoviesRepository sRepository;
    private LocalDataSource mLocal;
    private RemoteDataSource mRemote;

    private MoviesRepository(LocalDataSource local, RemoteDataSource remote) {
        mLocal = local;
        mRemote = remote;
    }

    public static synchronized MoviesRepository getInstance() {
        if (sRepository == null) {
            sRepository =
                    new MoviesRepository(LocalDataSource.getInstance(), RemoteDataSource.getInstance());
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
}
