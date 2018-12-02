package com.framgia.vhlee.themoviedb.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.databinding.ItemMovieBinding;
import com.framgia.vhlee.themoviedb.utils.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> mMovies;
    private MovieClickListener mMovieClickListener;

    public MovieAdapter(MovieClickListener listener) {
        mMovieClickListener = listener;
        mMovies = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding itemMovieBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_movie, parent, false);
        return new ViewHolder(itemMovieBinding, mMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    public void append(List<Movie> movies) {
        int lastPosition = mMovies.size();
        mMovies.addAll(movies);
        notifyItemRangeInserted(lastPosition, movies.size());
    }

    public void update(List<Movie> movies) {
        if (mMovies != null) mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemMovieBinding mItemMovieBinding;
        private MovieClickListener mClickListener;

        public ViewHolder(ItemMovieBinding itemMovieBinding, MovieClickListener listener) {
            super(itemMovieBinding.getRoot());
            mItemMovieBinding = itemMovieBinding;
            mClickListener = listener;
            itemView.setOnClickListener(this);
        }

        public void bindData(Movie movie) {
            if (mItemMovieBinding.getMovieVM() == null) {
                mItemMovieBinding.setMovieVM(new MovieViewModel());
            }
            mItemMovieBinding.getMovieVM().setMovie(movie);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onMovieClick(mItemMovieBinding.getMovieVM().getMovie());
        }
    }

    public interface MovieClickListener {
        void onMovieClick(Movie movie);
    }
}
