package com.framgia.vhlee.themoviedb.ui.detail;

import android.databinding.ObservableField;
import android.view.View;

import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel {
    private static final String TAG = "DetailViewModel";
    private static final String APPEND = "videos,credits";
    private ObservableField<Movie> mMovie;
    private MoviesRepository mMoviesRepository;
    private CompositeDisposable mCompositeDisposable;
    private DetailNavigator mNavigator;

    public DetailViewModel(DetailNavigator navigator) {
        mMovie = new ObservableField<>();
        mMoviesRepository = MoviesRepository.getInstance();
        mCompositeDisposable = new CompositeDisposable();
        mNavigator = navigator;
    }

    public void loadDetail(int id) {
        Disposable disposable = mMoviesRepository.getMovieDetail(id, APPEND)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        mMovie.set(movie);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void onButtonClick(View view, Movie movie, boolean isCast) {
        mNavigator.showBottomSheet(movie, isCast);
    }

    private void handleError(String message) {
        //TODO Handle error
    }

    public ObservableField<Movie> getMovie() {
        return mMovie;
    }
}
