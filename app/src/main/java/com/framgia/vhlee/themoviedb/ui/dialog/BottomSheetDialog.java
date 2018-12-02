package com.framgia.vhlee.themoviedb.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Cast;
import com.framgia.vhlee.themoviedb.data.model.Company;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.databinding.FragmentBottomSheetBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.CastAdapter;
import com.framgia.vhlee.themoviedb.ui.adapter.CompanyAdapter;

public class BottomSheetDialog extends BottomSheetDialogFragment
        implements CastAdapter.CastClickListener, CompanyAdapter.CompanyClickListener {
    private static final String ARGUMENT_MOVIE = "ARGUMENT_MOVIE";
    private static final String ARGUMENT_IS_CAST = "ARGUMENT_IS_CAST";

    public static BottomSheetDialog newInstance(Movie movie, boolean isCast) {
        BottomSheetDialog fragment = new BottomSheetDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_MOVIE, movie);
        args.putBoolean(ARGUMENT_IS_CAST, isCast);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBottomSheetBinding binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_bottom_sheet, container, false);
        Movie movie = (Movie) getArguments().getSerializable(ARGUMENT_MOVIE);
        boolean isCast = getArguments().getBoolean(ARGUMENT_IS_CAST);
        BottomSheetViewModel viewModel = new BottomSheetViewModel(movie, isCast);
        binding.setBottomSheetVM(viewModel);
        initAdapter(binding);
        return binding.getRoot();
    }

    private void initAdapter(FragmentBottomSheetBinding binding) {
        boolean isCast = getArguments().getBoolean(ARGUMENT_IS_CAST);
        RecyclerView recyclerSheet = binding.recyclerSheet;
        if (isCast) recyclerSheet.setAdapter(new CastAdapter(this));
        else recyclerSheet.setAdapter(new CompanyAdapter(this));
    }

    @Override
    public void onCastClick(Cast cast) {
        //TODO show cast info detail
    }

    @Override
    public void onCompanyClick(Company company) {
        //TODO show list movies by company
    }
}
