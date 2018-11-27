package com.framgia.vhlee.themoviedb.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.ui.adapter.HighLightAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;

public class BindingUtils {
    @BindingAdapter("pagerAdapter")
    public static void bindPagerAdapter(ViewPager pager, HighLightAdapter adapter) {
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
    public static void bindRecyclerMovies(RecyclerView recycler, MovieAdapter adapter) {
        recycler.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String url) {
        String source =
                StringUtils.append(Constants.BASE_IMAGE_URL, Constants.IMAGE_QUALITY_MAX, url);
        Glide.with(imageView.getContext())
                .load(source)
                .into(imageView);
    }
}
