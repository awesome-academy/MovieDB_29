package com.framgia.vhlee.themoviedb.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.databinding.FragmentBottomBinding;

public class BottomFragment extends Fragment {
    private HomeViewModel mViewModel;

    public BottomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBottomBinding binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_bottom, container, false);
        mViewModel = new HomeViewModel();
        binding.setHomeVM(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.setMovieAdapter();
        mViewModel.setGenres();
    }
}
