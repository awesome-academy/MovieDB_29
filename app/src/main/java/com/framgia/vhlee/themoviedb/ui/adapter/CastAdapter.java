package com.framgia.vhlee.themoviedb.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Cast;
import com.framgia.vhlee.themoviedb.databinding.ItemCastBinding;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private List<Cast> mCasts;
    private CastClickListener mCastClickListener;

    public CastAdapter(CastClickListener listener) {
        mCasts = new ArrayList<>();
        mCastClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCastBinding itemCastBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_cast, parent, false);
        return new ViewHolder(itemCastBinding, mCastClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mCasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mCasts != null ? mCasts.size() : 0;
    }

    public void update(List<Cast> casts) {
        if (mCasts != null) mCasts.clear();
        mCasts.addAll(casts);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemCastBinding mItemCastBinding;
        private CastClickListener mClickListener;

        public ViewHolder(ItemCastBinding itemCastBinding, CastClickListener listener) {
            super(itemCastBinding.getRoot());
            mItemCastBinding = itemCastBinding;
            mClickListener = listener;
            mItemCastBinding.constraintCast.setOnClickListener(this);
        }

        public void bindData(Cast cast) {
            mItemCastBinding.setCast(cast);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onCastClick(mItemCastBinding.getCast());
        }
    }

    public interface CastClickListener {
        void onCastClick(Cast cast);
    }
}
