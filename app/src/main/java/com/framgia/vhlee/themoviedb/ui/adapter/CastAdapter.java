package com.framgia.vhlee.themoviedb.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Cast;
import com.framgia.vhlee.themoviedb.databinding.ItemCastBinding;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private List<Cast> mCasts;

    public CastAdapter(List<Cast> casts) {
        mCasts = casts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCastBinding itemCastBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_cast, parent, false);
        return new ViewHolder(itemCastBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mCasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mCasts != null ? mCasts.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCastBinding mItemCastBinding;

        public ViewHolder(ItemCastBinding itemCastBinding) {
            super(itemCastBinding.getRoot());
            mItemCastBinding = itemCastBinding;
        }

        public void bindData(Cast cast) {
            mItemCastBinding.setCast(cast);
        }
    }
}
