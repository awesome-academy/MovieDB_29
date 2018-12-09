package com.framgia.vhlee.themoviedb.ui.search;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.view.View;

import com.framgia.vhlee.themoviedb.data.model.CategoryName;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.GenresKey;
import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel {
    private static final String TAG = "SearchViewModel";
    private static final String DEFAULT_TYPE_KEY = "movie";
    private static final String DEFAULT_TYPE_NAME = "All";
    private ObservableArrayList<Movie> mMovies;
    private ObservableInt mTotalResult;
    private ObservableArrayList<Genre> mGenres;
    private SearchNavigator mSearchNavigator;
    private MoviesRepository mMoviesRepository;
    private CompositeDisposable mCompositeDisposable;

    public SearchViewModel(SearchNavigator navigator, MoviesRepository repository) {
        mSearchNavigator = navigator;
        mMoviesRepository = repository;
        mMovies = new ObservableArrayList<>();
        mTotalResult = new ObservableInt();
        mGenres = new ObservableArrayList<>();
        mCompositeDisposable = new CompositeDisposable();
    }

    public void search(String type, String keyword, int page) {
        Disposable disposable = mMoviesRepository.searchByMovie(type, keyword, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        mMovies.addAll(movieResponse.getResults());
                        mTotalResult.set(movieResponse.getToalResult());
                        mSearchNavigator.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.toString());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void loadGenres() {
        initGenres();
        Disposable disposable = mMoviesRepository.getGenre()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenresResponse>() {
                    @Override
                    public void accept(GenresResponse genresResponse) throws Exception {
                        mGenres.addAll(genresResponse.getGenres());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void initGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(DEFAULT_TYPE_KEY, DEFAULT_TYPE_NAME));
        genres.add(new Genre(GenresKey.POPULAR, CategoryName.POPULAR));
        genres.add(new Genre(GenresKey.TOP_RATED, CategoryName.TOP_RATED));
        genres.add(new Genre(GenresKey.UPCOMING, CategoryName.UPCOMING));
        genres.add(new Genre(GenresKey.NOW_PLAYING, CategoryName.NOW_PLAYING));
        mGenres.addAll(genres);
    }

    public void clear() {
        mMovies.clear();
    }

    public void onBackButtonClick(View view) {
        mSearchNavigator.onBackClick();
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }

    public ObservableArrayList<Movie> getMovies() {
        return mMovies;
    }

    public ObservableInt getTotalResult() {
        return mTotalResult;
    }

    public ObservableArrayList<Genre> getGenres() {
        return mGenres;
    }

    private void handleError(String message) {
        //TODO handle error
    }
}
