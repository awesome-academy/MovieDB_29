package com.framgia.vhlee.themoviedb.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.databinding.FragmentTopBinding;
import com.framgia.vhlee.themoviedb.ui.base.ActivityNavigator;
import com.framgia.vhlee.themoviedb.utils.MovieViewModel;

public class TopFragment extends Fragment {
    private HomeViewModel mHomeViewModel;

    public TopFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        FragmentTopBinding binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_top, parent, false);
        MovieViewModel movieViewModel = new MovieViewModel();
        ActivityNavigator navigator = new ActivityNavigator(getActivity());
        mHomeViewModel = new HomeViewModel(navigator);
        binding.setHomeVM(mHomeViewModel);
        binding.setMovieVM(movieViewModel);
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
}
