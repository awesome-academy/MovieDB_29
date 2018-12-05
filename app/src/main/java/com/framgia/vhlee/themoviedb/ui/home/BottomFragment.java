package com.framgia.vhlee.themoviedb.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;
import com.framgia.vhlee.themoviedb.databinding.FragmentBottomBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;
import com.framgia.vhlee.themoviedb.ui.category.CategoryActivity;
import com.framgia.vhlee.themoviedb.ui.detail.DetailActivity;

public class BottomFragment extends Fragment implements HomeNavigator, MovieAdapter.MovieClickListener {
    private HomeViewModel mViewModel;

    public BottomFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBottomBinding binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_bottom, container, false);
        MoviesRepository repository = MoviesRepository.getInstance(
                LocalDataSource.getInstance(getContext()),
                RemoteDataSource.getInstance());
        mViewModel = new HomeViewModel(this, repository);
        binding.setHomeVM(mViewModel);
        RecyclerView recycler = binding.recyclerGenres;
        recycler.setAdapter(new MovieAdapter(this));
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.setGenreMovies();
        mViewModel.loadGenres();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.destroy();
    }

    @Override
    public void startCategoryActivity(Genre genre, boolean isGenre) {
        startActivity(CategoryActivity.getCategoryIntent(getActivity(), genre, isGenre));
    }

    @Override
    public void startDetailActivity(Movie movie) {
        startActivity(DetailActivity.getDetailIntent(getActivity(), movie));
    }

    @Override
    public void onMovieClick(Movie movie) {
        startDetailActivity(movie);
    }
}
