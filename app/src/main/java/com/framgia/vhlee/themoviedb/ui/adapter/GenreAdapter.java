package com.framgia.vhlee.themoviedb.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Genre;
import com.framgia.vhlee.themoviedb.databinding.ItemLabelBinding;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private List<Genre> mGenres;

    public GenreAdapter() {
        mGenres = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemLabelBinding itemLabelBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_label, parent, false);
        return new ViewHolder(itemLabelBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mGenres.get(position));
    }

    public void update(List<Genre> genres) {
        if (mGenres != null) mGenres.clear();
        mGenres.addAll(genres);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLabelBinding mItemLabelBinding;

        public ViewHolder(ItemLabelBinding itemLabelBinding) {
            super(itemLabelBinding.getRoot());
            mItemLabelBinding = itemLabelBinding;
        }

        public void bindData(Genre genre) {
            mItemLabelBinding.setGenre(genre);
        }
    }
}
