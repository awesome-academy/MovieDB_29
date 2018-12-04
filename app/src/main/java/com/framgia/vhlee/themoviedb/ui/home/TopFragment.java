package com.framgia.vhlee.themoviedb.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.databinding.FragmentTopBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.HighLightAdapter;
import com.framgia.vhlee.themoviedb.ui.category.CategoryActivity;
import com.framgia.vhlee.themoviedb.ui.detail.DetailActivity;
import com.framgia.vhlee.themoviedb.utils.MovieViewModel;

public class TopFragment extends Fragment implements HomeNavigator {
    private HomeViewModel mHomeViewModel;

    public TopFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        FragmentTopBinding binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_top, parent, false);
        MovieViewModel movieViewModel = new MovieViewModel();
        mHomeViewModel = new HomeViewModel(this);
        binding.setHomeVM(mHomeViewModel);
        binding.setMovieVM(movieViewModel);
        ViewPager pager = binding.pagerHighlight;
        pager.setAdapter(new HighLightAdapter());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mHomeViewModel.setHighLightMovies();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomeViewModel.destroy();
    }

    @Override
    public void startCategoryActivity(Genre genre, boolean isGenre) {
        startActivity(CategoryActivity.getCategoryIntent(getActivity(), genre, isGenre));
    }

    @Override
    public void startDetailActivity(Movie movie) {
        startActivity(DetailActivity.getDetailIntent(getActivity(), movie));
    }

}
