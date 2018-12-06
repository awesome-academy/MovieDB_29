package com.framgia.vhlee.themoviedb.ui.home;

import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;

public interface HomeNavigator {
    void startCategoryActivity(Genre genre, boolean isGenre);

    void startDetailActivity(Movie movie);

    void startFavoriteActivity();
}
