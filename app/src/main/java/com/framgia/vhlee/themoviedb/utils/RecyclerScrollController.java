package com.framgia.vhlee.themoviedb.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.ProgressBar;

public abstract class RecyclerScrollController extends AppCompatActivity {
    protected RecyclerView mRecycler;
    protected LinearLayoutManager mLinearLayoutManager;
    protected boolean mIsScrolling = false;
    protected int mCurrentItem, mTotalItem, mScrollOutItem;
    protected ProgressBar mProgressBar;

    protected void setLoadMore() {
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recycler, int newState) {
                super.onScrollStateChanged(recycler, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recycler, int dx, int dy) {
                super.onScrolled(recycler, dx, dy);
                mCurrentItem = mLinearLayoutManager.getChildCount();
                mTotalItem = mLinearLayoutManager.getItemCount();
                mScrollOutItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (mIsScrolling && (mCurrentItem + mScrollOutItem == mTotalItem)) {
                    loadMore();
                }
            }
        });
    }

    public abstract void loadMore();

    public abstract void initRecyclerView();
}
