package com.framgia.vhlee.themoviedb.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.ui.adapter.HighLightAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;

public class BindingUtils {
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_QUALITY_MAX = "w1280";
    private static final int PROGRESS_UNIT = 10;

    @BindingAdapter("pagerAdapter")
    public static void bindPagerAdapter(ViewPager pager, ObservableArrayList<Movie> movies) {
        HighLightAdapter adapter = new HighLightAdapter();
        adapter.update(movies);
        pager.setAdapter(adapter);
    }

    @BindingAdapter("spinnerAdapter")
    public static void bindSpinner(Spinner spinner, ObservableArrayList<Genre> genreNames) {
        ObservableArrayList<String> strings = new ObservableArrayList<>();
        for (Genre genre : genreNames) {
            strings.add(genre.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(),
                android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @BindingAdapter("bindData")
    public static void bindRecyclerMovies(RecyclerView recycler, ObservableArrayList<Movie> movies) {
        MovieAdapter adapter = new MovieAdapter();
        adapter.update(movies);
        recycler.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String url) {
        String source =
                StringUtils.append(BASE_IMAGE_URL, IMAGE_QUALITY_MAX, url);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.default_poster);
        Glide.with(imageView.getContext())
                .load(source)
                .apply(requestOptions.centerInside())
                .into(imageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @BindingAdapter("progressValue")
    public static void bindProgress(ProgressBar progressBar, float value) {
        progressBar.setProgress((int) (value * PROGRESS_UNIT), true);
    }
}
