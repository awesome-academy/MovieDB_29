package com.framgia.vhlee.themoviedb.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        CategoryName.TOP_RATED, CategoryName.NOW_PLAYING,
        CategoryName.POPULAR, CategoryName.UPCOMING
})
public @interface CategoryName {
    String TOP_RATED = "Top rate";
    String NOW_PLAYING = "Now playing";
    String POPULAR = "Popular";
    String UPCOMING = "Upcoming";
}
