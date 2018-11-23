package com.framgia.vhlee.themoviedb.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.databinding.FragmentTopBinding;
import com.framgia.vhlee.themoviedb.utils.MovieViewModel;

public class TopFragment extends Fragment {
    private HomeViewModel mHomeViewModel;
    private MovieViewModel mMovieViewModel;

    public TopFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        FragmentTopBinding binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_top, parent, false);
        mHomeViewModel = new HomeViewModel();
        mMovieViewModel = new MovieViewModel();
        binding.setHomeVM(mHomeViewModel);
        binding.setMovieVM(mMovieViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mHomeViewModel.setHighLightAdapter();
    }
}
