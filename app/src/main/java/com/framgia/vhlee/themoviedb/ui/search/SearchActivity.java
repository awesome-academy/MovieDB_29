package com.framgia.vhlee.themoviedb.ui.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.repository.MoviesRepository;
import com.framgia.vhlee.themoviedb.data.source.local.LocalDataSource;
import com.framgia.vhlee.themoviedb.data.source.remote.RemoteDataSource;
import com.framgia.vhlee.themoviedb.databinding.ActivitySearchBinding;
import com.framgia.vhlee.themoviedb.ui.adapter.MovieAdapter;
import com.framgia.vhlee.themoviedb.ui.detail.DetailActivity;
import com.framgia.vhlee.themoviedb.utils.RecyclerScrollController;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchActivity extends RecyclerScrollController
        implements SearchNavigator, MovieAdapter.MovieClickListener {
    private static final String TAG = "SearchActivity";
    private static final String DEFAULT_TYPE = "movie";
    private static final int DEFAULT_TIMEOUT = 500;
    private static final int DEFAULT_PAGE = 1;
    private ActivitySearchBinding mBinding;
    private SearchViewModel mViewModel;
    private String mType;
    private String mKeyword;
    private int mPage;
    private CompositeDisposable mCompositeDisposable;

    public static Intent getSearchIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        MoviesRepository repository = MoviesRepository.getInstance(LocalDataSource.getInstance(this),
                RemoteDataSource.getInstance());
        mViewModel = new SearchViewModel(this, repository);
        mBinding.setSearchVM(mViewModel);
        mViewModel.loadGenres();
        initRecyclerView();
        search();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.destroy();
        mCompositeDisposable.dispose();
    }

    @Override
    public void loadMore() {
        mIsScrolling = false;
        mProgressBar.setVisibility(View.VISIBLE);
        mViewModel.search(mType, mKeyword, ++mPage);
    }

    @Override
    public void initRecyclerView() {
        mPage = DEFAULT_PAGE;
        mType = DEFAULT_TYPE;
        mProgressBar = mBinding.progressSearchMore;
        mRecycler = mBinding.recyclerSearch;
        mRecycler.setAdapter(new MovieAdapter(this));
        mLinearLayoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();
        setLoadMore();
    }

    @Override
    public void onMovieClick(Movie movie) {
        startActivity(DetailActivity.getDetailIntent(this, movie));
    }

    @Override
    public void onDeleteClick(Movie movie, int position) {
        //TODO ignore on this activity
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackClick() {
        super.onBackPressed();
    }

    private void search() {
        mCompositeDisposable = new CompositeDisposable();
        final PublishSubject<String> subject = PublishSubject.create();
        initObservable(subject);
        EditText editTextSearch = mBinding.editSearch;
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subject.onNext(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initObservable(PublishSubject<String> subject) {
        Disposable disposable = subject.debounce(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mKeyword = s;
                        mViewModel.clear();
                        mViewModel.search(DEFAULT_TYPE, mKeyword, mPage);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
