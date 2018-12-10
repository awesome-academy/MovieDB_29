package com.framgia.vhlee.themoviedb.ui.category;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;

import com.framgia.vhlee.themoviedb.data.model.CategoryRequest;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.MovieResponse;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryViewModel extends BaseObservable {
    private static final String TAG = "CategoryViewModel";
    private static final int DEFAULT_PAGE = 1;
    private int mPage;
    private String mType;
    private ObservableInt mTotalResult;
    private ObservableArrayList<Movie> mMovies;
    private MoviesRepository mMoviesRepository;
    private CompositeDisposable mCompositeDisposable;
    private CategoryNavigator mNavigator;

    public CategoryViewModel(CategoryNavigator navigator, MoviesRepository repository, String type) {
        mType = type;
        mNavigator = navigator;
        mTotalResult = new ObservableInt(DEFAULT_PAGE);
        mMovies = new ObservableArrayList<>();
        mMoviesRepository = repository;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void loaMovies(@CategoryRequest int source, int page) {
        mPage = page;
        switch (source) {
            case CategoryRequest.CATEGORY:
                loadCategoryMovies();
                break;
            case CategoryRequest.GENRE:
                loaGenreMovies();
                break;
            case CategoryRequest.CAST:
                loadCastMovies();
                break;
            case CategoryRequest.COMPANY:
                loadCompanyMovies();
                break;
            default:
                break;
        }
    }

    public ObservableInt getTotalResult() {
        return mTotalResult;
    }

    public ObservableArrayList<Movie> getMovies() {
        return mMovies;
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }

    private void loaGenreMovies() {
        Disposable disposable = mMoviesRepository.getMoviesByGenre(mType, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        mMovies.addAll(movieResponse.getResults());
                        mTotalResult.set(movieResponse.getToalResult());
                        mNavigator.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void loadCategoryMovies() {
        Disposable disposable = mMoviesRepository.getMoviesByCategory(mType, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        mMovies.addAll(movieResponse.getResults());
                        mTotalResult.set(movieResponse.getToalResult());
                        mNavigator.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void loadCastMovies() {
        Disposable disposable = mMoviesRepository.getMoviesByCast(mType, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        mMovies.addAll(movieResponse.getResults());
                        mTotalResult.set(movieResponse.getToalResult());
                        mNavigator.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void loadCompanyMovies() {
        Disposable disposable = mMoviesRepository.getMoviesByCompany(mType, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        mMovies.addAll(movieResponse.getResults());
                        mTotalResult.set(movieResponse.getToalResult());
                        mNavigator.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void handleError(String message) {
        //TODO handle error
    }
}
