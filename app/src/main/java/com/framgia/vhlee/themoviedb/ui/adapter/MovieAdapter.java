package com.framgia.vhlee.themoviedb.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
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
    private static final String TAG = "MovieAdapter";
    private List<Movie> mMovies;
    private boolean mIsFavorite;
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

    public void delete(int position) {
        mMovies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mMovies.size());
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private ItemMovieBinding mItemMovieBinding;
        private MovieClickListener mClickListener;

        public ViewHolder(ItemMovieBinding itemMovieBinding, MovieClickListener listener) {
            super(itemMovieBinding.getRoot());
            mItemMovieBinding = itemMovieBinding;
            mClickListener = listener;
            mItemMovieBinding.imageDelete.setOnClickListener(this);
            mItemMovieBinding.textVoteAverage.setOnClickListener(this);
            mItemMovieBinding.cardMovie.setOnClickListener(this);
            mItemMovieBinding.cardMovie.setOnLongClickListener(this);
        }

        public void bindData(Movie movie) {
            if (mItemMovieBinding.getMovieVM() == null) {
                mItemMovieBinding.setMovieVM(new MovieViewModel());
            }
            mItemMovieBinding.getMovieVM().setMovie(movie);
        }

        @Override
        public void onClick(View view) {
            if (view == mItemMovieBinding.imageDelete) {
                mClickListener.onDeleteClick(mItemMovieBinding.getMovieVM().getMovie(), getAdapterPosition());
            }
            if (view == mItemMovieBinding.textVoteAverage && mIsFavorite) {
                mItemMovieBinding.imageDelete.setVisibility(View.VISIBLE);
                mItemMovieBinding.textVoteAverage.setVisibility(View.GONE);
            }
            if (view == mItemMovieBinding.cardMovie) {
                if (mItemMovieBinding.imageDelete.getVisibility() == View.VISIBLE) {
                    mItemMovieBinding.imageDelete.setVisibility(View.GONE);
                    mItemMovieBinding.textVoteAverage.setVisibility(View.VISIBLE);
                } else {
                    mClickListener.onMovieClick(mItemMovieBinding.getMovieVM().getMovie());
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mIsFavorite) {
                mItemMovieBinding.imageDelete.setVisibility(View.VISIBLE);
                mItemMovieBinding.textVoteAverage.setVisibility(View.GONE);
            }
            return true;
        }
    }

    public interface MovieClickListener {
        void onMovieClick(Movie movie);

        void onDeleteClick(Movie movie, int position);
    }
}
