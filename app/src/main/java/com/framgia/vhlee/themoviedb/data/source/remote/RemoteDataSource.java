package com.framgia.vhlee.themoviedb.data.source.remote;

import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.Movie;
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
        return mClientRequest.getGenre();
    }

    @Override
    public Observable<MovieResponse> getMoviesByCategory(String type, int page) {
        return mClientRequest.getMoviesCategory(type, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByGenre(String id, int page) {
        return mClientRequest.getMoviesByGenre(id, page);
    }

    @Override
    public Observable<MovieResponse> getHighLightMovies() {
        return mClientRequest.getMoviesHighLight();
    }

    @Override
    public Observable<Movie> getMovieDetail(int id, String append) {
        return mClientRequest.getMovieDetail(id, append);
    }

    @Override
    public Observable<MovieResponse> searchByMovie(String type, String keyword, int page) {
        return mClientRequest.searchByMovie(type, keyword, page);
    }
}
