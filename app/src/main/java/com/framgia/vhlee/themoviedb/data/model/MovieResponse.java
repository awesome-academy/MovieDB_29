package com.framgia.vhlee.themoviedb.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieResponse implements Serializable {
    @SerializedName("page")
    private int mPage;

    @SerializedName("total_page")
    private int mTotalPage;

    @SerializedName("total_results")
    private int mToalResult;

    @SerializedName("results")
    private List<Movie> mResults;

    public MovieResponse() {
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public void setTotalPage(int totalPage) {
        mTotalPage = totalPage;
    }

    public int getToalResult() {
        return mToalResult;
    }

    public void setToalResult(int toalResult) {
        mToalResult = toalResult;
    }

    public List<Movie> getResults() {
        return mResults;
    }

    public void setResults(List<Movie> results) {
        mResults = results;
    }
}
