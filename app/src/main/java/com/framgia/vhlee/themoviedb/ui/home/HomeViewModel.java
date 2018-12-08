package com.framgia.vhlee.themoviedb.ui.home;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.AdapterView;

import com.framgia.vhlee.themoviedb.data.model.CategoryName;
import com.framgia.vhlee.themoviedb.data.model.CategoryRequest;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.GenresKey;
import com.framgia.vhlee.themoviedb.data.model.GenresResponse;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseObservable {
    private static final String TAG = "HomeViewModel";
    private static final String BUNDLE_TYPE = "BUNDLE_TYPE";
    private static final String BUNDLE_CODE = "BUNDLE_CODE";
    private static final int DEFAULT_PAGE = 1;
    private int mPage;
    private ObservableField<Genre> mGenre;
    private ObservableArrayList<Movie> mHighLightMovies;
    private ObservableArrayList<Movie> mGenreMovies;
    private ObservableArrayList<Genre> mGenres;
    private MoviesRepository mMoviesRepository;
    private CompositeDisposable mCompositeDisposable;
    private HomeNavigator mNavigator;

    public HomeViewModel(HomeNavigator navigator, MoviesRepository repository) {
        mPage = DEFAULT_PAGE;
        mNavigator = navigator;
        mMoviesRepository = repository;
        mGenre = new ObservableField<>();
        mGenres = new ObservableArrayList<>();
        mHighLightMovies = new ObservableArrayList<>();
        mGenreMovies = new ObservableArrayList<>();
        mCompositeDisposable = new CompositeDisposable();
    }

    public void setHighLightMovies() {
        Disposable disposable = mMoviesRepository.getHighLightMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        if (!mHighLightMovies.isEmpty()) mHighLightMovies.clear();
                        mHighLightMovies.addAll(movieResponse.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void setGenreMovies() {
        Disposable disposable = mMoviesRepository.getMoviesByGenre(mGenre.get().getId(), mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        if (!mGenreMovies.isEmpty()) mGenreMovies.clear();
                        mGenreMovies.addAll(movieResponse.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void loadGenres() {
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

    public void onItemSpinnerSelected(AdapterView<?> parent, View view, int position, long id) {
        mGenre.set(mGenres.get(position));
        setGenreMovies();
    }

    public void onCategoryClick(View view, String type) {
        Genre genre = new Genre();
        genre.setId(type);
        genre.setName(convertType(type));
        mNavigator.startCategoryActivity(genre, CategoryRequest.CATEGORY);
    }

    public void onGenreClick(View view, Genre genre) {
        mNavigator.startCategoryActivity(genre, CategoryRequest.GENRE);
    }

    public void onFavoriteClick(View view) {
        mNavigator.startFavoriteActivity();
    }

    private String convertType(String type) {
        switch (type) {
            case GenresKey.TOP_RATED:
                return CategoryName.TOP_RATED;
            case GenresKey.NOW_PLAYING:
                return CategoryName.NOW_PLAYING;
            case GenresKey.POPULAR:
                return CategoryName.POPULAR;
            case GenresKey.UPCOMING:
                return CategoryName.UPCOMING;
            default:
                return CategoryName.TOP_RATED;
        }
    }

    public ObservableArrayList<Movie> getHighLightMovies() {
        return mHighLightMovies;
    }

    public ObservableArrayList<Movie> getGenreMovies() {
        return mGenreMovies;
    }

    public ObservableArrayList<Genre> getGenres() {
        return mGenres;
    }

    public ObservableField<Genre> getGenre() {
        return mGenre;
    }

    private void handleError(String message) {
        //TODO handle error
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }
}
