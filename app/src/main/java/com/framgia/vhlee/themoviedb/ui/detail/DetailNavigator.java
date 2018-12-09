package com.framgia.vhlee.themoviedb.ui.detail;

import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;

public interface DetailNavigator {
    void startCategoryActivity(Genre genre, int source);

    void showBottomSheet(Movie movie, boolean isCast);
}
