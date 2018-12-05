package com.framgia.vhlee.themoviedb.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.vhlee.themoviedb.BuildConfig;
import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Cast;
import com.framgia.vhlee.themoviedb.data.model.Company;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.model.Video;
import com.framgia.vhlee.themoviedb.ui.adapter.CastAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.CompanyAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.GenreAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.HighLightAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.VideoAdapter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

public class BindingUtils {
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_QUALITY_MAX = "w1280";
    private static final int PROGRESS_UNIT = 10;

    @BindingAdapter("pagerAdapter")
    public static void bindPagerAdapter(ViewPager pager, ObservableArrayList<Movie> movies) {
        HighLightAdapter adapter = (HighLightAdapter) pager.getAdapter();
        if (adapter != null) {
            adapter.update(movies);
        }
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
        MovieAdapter adapter = (MovieAdapter) recycler.getAdapter();
        if (adapter != null) {
            adapter.update(movies);
        }
    }

    @BindingAdapter("bindGenres")
    public static void bindGenres(RecyclerView recycler, List<Genre> genres) {
        if (genres == null) return;
        GenreAdapter adapter = (GenreAdapter) recycler.getAdapter();
        if (adapter != null) {
            adapter.update(genres);
        }
    }

    @BindingAdapter("bindVideos")
    public static void bindVideos(RecyclerView recycler, List<Video> videos) {
        if (videos == null) return;
        VideoAdapter adapter = (VideoAdapter) recycler.getAdapter();
        if (adapter != null) {
            adapter.update(videos);
        }
    }

    @BindingAdapter("videoThumbnail")
    public static void bindThumbnail(YouTubeThumbnailView view, final String videoId) {
        YouTubeThumbnailView.OnInitializedListener listener =
                new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView view,
                                                        final YouTubeThumbnailLoader loader) {
                        loader.setVideo(videoId);
                        loader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                loader.release();
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView view,
                                                         YouTubeThumbnailLoader.ErrorReason error) {
                                //TODO Handle load thumbnail error
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView view,
                                                        YouTubeInitializationResult result) {
                        //TODO Handle init error
                    }
                };
        view.initialize(BuildConfig.YOUTUBE_API, listener);
    }

    @BindingAdapter({"checkData", "bindCompanies", "bindCasts"})
    public static void bindCompanies(RecyclerView recycler, boolean isCast,
                                     @Nullable List<Company> companies, @Nullable List<Cast> casts) {
        if (isCast) {
            CastAdapter castAdapter = (CastAdapter) recycler.getAdapter();
            if (castAdapter != null) {
                castAdapter.update(casts);
            }
        } else {
            CompanyAdapter companyAdapter = (CompanyAdapter) recycler.getAdapter();
            if (companyAdapter != null) {
                companyAdapter.update(companies);
            }
        }
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

    @BindingAdapter("progressValue")
    public static void bindProgress(ProgressBar progressBar, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress((int) (value * PROGRESS_UNIT), true);
        } else {
            progressBar.setProgress((int) (value * PROGRESS_UNIT));
        }
    }
}
