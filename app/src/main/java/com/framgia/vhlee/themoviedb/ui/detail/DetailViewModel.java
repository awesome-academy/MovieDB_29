package com.framgia.vhlee.themoviedb.ui.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private ObservableBoolean mIsFavorite;
    private ObservableField<Movie> mMovie;
    private MoviesRepository mMoviesRepository;
    private CompositeDisposable mCompositeDisposable;
    private DetailNavigator mNavigator;

    public DetailViewModel(DetailNavigator navigator, MoviesRepository repository) {
        mIsFavorite = new ObservableBoolean();
        mMovie = new ObservableField<>();
        mMoviesRepository = repository;
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
                        mIsFavorite.set(mMoviesRepository.isFavourite(movie));
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

    public void onFavoriteClick(View view, Movie movie) {
        if (mIsFavorite.get()) {
            if (mMoviesRepository.delete(movie)) mIsFavorite.set(false);
        } else {
            if (mMoviesRepository.insert(movie)) mIsFavorite.set(true);
            mNavigator.showSnackBar();
        }
    }

    public ObservableBoolean getIsFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(ObservableBoolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    private void handleError(String message) {
        //TODO Handle error
    }

    public ObservableField<Movie> getMovie() {
        return mMovie;
    }
}
