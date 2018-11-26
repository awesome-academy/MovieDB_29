package com.framgia.vhlee.themoviedb.data.source.remote;

import com.framgia.vhlee.themoviedb.BuildConfig;
import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;
import com.framgia.vhlee.themoviedb.data.source.DataSource;
import com.framgia.vhlee.themoviedb.service.ClientRequest;
import com.framgia.vhlee.themoviedb.service.NetworkService;

import io.reactivex.Observable;

public class RemoteDataSource implements DataSource.Remote {
    private static RemoteDataSource sRemote;
    private ClientRequest mClientRequest;

    private RemoteDataSource(ClientRequest clientRequest) {
        mClientRequest = clientRequest;
    }

    public static synchronized RemoteDataSource getInstance() {
        if (sRemote == null) {
            sRemote = new RemoteDataSource(NetworkService.getInstance());
        }
        return sRemote;
    }

    @Override
    public Observable<GenresResponse> getGenre() {
        return mClientRequest.getGenre(BuildConfig.API_KEY);
    }

    @Override
    public Observable<MovieResponse> getMoviesByCategory(String type, int page) {
        return null;
    }

    @Override
    public Observable<MovieResponse> getMoviesByGenre(String id, int page) {
        return mClientRequest.getMoviesByGenre(BuildConfig.API_KEY, id, page);
    }

    @Override
    public Observable<MovieResponse> getHighLightMovies() {
        return mClientRequest.getMoviesHighLight(BuildConfig.API_KEY);
    }
}
