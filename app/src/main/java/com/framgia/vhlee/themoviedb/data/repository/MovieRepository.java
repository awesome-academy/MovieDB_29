package com.framgia.vhlee.themoviedb.data.repository;

import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;
import com.framgia.vhlee.themoviedb.data.source.DataSource;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;

import io.reactivex.Observable;

public class MovieRepository implements DataSource.Remote, DataSource.Local {
    private static MovieRepository sRepository;
    private LocalDataSource mLocal;
    private RemoteDataSource mRemote;

    private MovieRepository(LocalDataSource local, RemoteDataSource remote) {
        mLocal = local;
        mRemote = remote;
    }

    public static synchronized MovieRepository getInstance() {
        if (sRepository == null) {
            sRepository =
                    new MovieRepository(LocalDataSource.getInstance(), RemoteDataSource.getInstance());
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
}
