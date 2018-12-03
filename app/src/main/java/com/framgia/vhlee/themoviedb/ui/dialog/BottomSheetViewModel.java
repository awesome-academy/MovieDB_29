package com.framgia.vhlee.themoviedb.ui.dialog;

import com.framgia.vhlee.themoviedb.data.model.Cast;
import com.framgia.vhlee.themoviedb.data.model.Company;
import com.framgia.vhlee.themoviedb.data.model.Movie;

import java.util.List;

public class BottomSheetViewModel {
    private List<Company> mCompanies;
    private List<Cast> mCasts;
    private boolean mIsCast;

    public BottomSheetViewModel(Movie movie, boolean isCast) {
        mCompanies = movie.getCompanies();
        mCasts = movie.getCredit().getCasts();
        mIsCast = isCast;
    }

    public List<Company> getCompanies() {
        return mCompanies;
    }

    public List<Cast> getCasts() {
        return mCasts;
    }

    public boolean isCast() {
        return mIsCast;
    }
}
