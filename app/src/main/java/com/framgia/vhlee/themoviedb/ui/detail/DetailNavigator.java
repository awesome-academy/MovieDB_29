package com.framgia.vhlee.themoviedb.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.framgia.vhlee.themoviedb.data.model.Movie;

public interface DetailNavigator {
    void startCategoryActivity(Bundle bundle);

    void showBottomSheet(Movie movie, boolean isCast);
}
