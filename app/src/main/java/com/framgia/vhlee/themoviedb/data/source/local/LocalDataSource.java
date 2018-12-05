package com.framgia.vhlee.themoviedb.data.source.local;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.source.DataSource;
import com.framgia.vhlee.themoviedb.data.source.local.sqlite.DbHelper;
import com.framgia.vhlee.themoviedb.data.source.local.sqlite.DbManager;

import java.util.List;

public class LocalDataSource implements DataSource.Local {
    private static LocalDataSource sLocalDataSource;
    private DbManager mManager;

    public LocalDataSource(DbManager manager) {
        mManager = manager;
    }

    public static LocalDataSource getInstance(Context context) {
        if (sLocalDataSource == null) {
            sLocalDataSource =
                    new LocalDataSource(
                            DbManager.getInstance(DbHelper.getInstance(context)));
        }
        return sLocalDataSource;
    }

    @Override
    public int getCount() {
        return mManager.getCount();
    }

    @Override
    public ObservableArrayList<Movie> getMovies() {
        return mManager.getMovies();
    }

    @Override
    public Movie getMovie(int id) {
        return mManager.getMovie(id);
    }

    @Override
    public boolean insert(Movie movie) {
        return mManager.insert(movie);
    }

    @Override
    public boolean delete(Movie movie) {
        return mManager.delete(movie);
    }

    @Override
    public boolean isFavourite(Movie movie) {
        return mManager.isFavourite(movie);
    }
}
