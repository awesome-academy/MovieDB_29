package com.framgia.vhlee.themoviedb.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Video;
import com.framgia.vhlee.themoviedb.databinding.ItemTrailerBinding;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> mVideos;
    private VideoClickListener mVideoClickListener;

    public VideoAdapter(VideoClickListener listener) {
        mVideos = new ArrayList<>();
        mVideoClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTrailerBinding itemTrailerBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_trailer, parent, false);
        return new ViewHolder(itemTrailerBinding, mVideoClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mVideos.get(position));
    }

    public void update(List<Video> videos) {
        if (mVideos != null) mVideos.clear();
        mVideos.addAll(videos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mVideos != null ? mVideos.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemTrailerBinding mItemTrailerBinding;
        private VideoClickListener mClickListener;

        public ViewHolder(ItemTrailerBinding itemTrailerBinding, VideoClickListener listener) {
            super(itemTrailerBinding.getRoot());
            mItemTrailerBinding = itemTrailerBinding;
            mClickListener = listener;
            mItemTrailerBinding.youtubeThumbnail.setOnClickListener(this);
        }

        public void bindData(Video video) {
            mItemTrailerBinding.setVideo(video);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onVideoClick(mItemTrailerBinding.getVideo());
        }
    }

    public interface VideoClickListener {
        void onVideoClick(Video video);
    }
}
