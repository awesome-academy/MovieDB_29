package com.framgia.vhlee.themoviedb.utils;

import android.databinding.BaseObservable;

import com.framgia.vhlee.themoviedb.data.model.Genre;

public class GenreViewModel extends BaseObservable {
    private Genre mGenre;

    public GenreViewModel() {
        mGenre = new Genre();
    }

    public String getId() {
        return mGenre.getId();
    }

    public String getName() {
        return mGenre.getName();
    }

    public void setGenre(Genre genre) {
        mGenre = genre;
        notifyChange();
    }
}
