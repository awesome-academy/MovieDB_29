package com.framgia.vhlee.themoviedb.ui.detail;

import android.os.Bundle;

import com.framgia.vhlee.themoviedb.BuildConfig;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public final class VideoFragment extends YouTubePlayerFragment
        implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "VideoFragment";
    private static final String ARGUMENT_ID = "ARGUMENT_ID";
    private YouTubePlayer mYouTubePlayer;
    private String mVideoId;
    private YouTubePlayer.OnFullscreenListener mListener;

    public static VideoFragment newInstance(String videoId) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_ID, videoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(BuildConfig.YOUTUBE_API, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mYouTubePlayer != null) mYouTubePlayer.release();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean restored) {
        mYouTubePlayer = player;
        mYouTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        if (!restored && mVideoId != null) {
            mYouTubePlayer.cueVideo(mVideoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult result) {
        this.mYouTubePlayer = null;
    }

    public void setVideoId(String videoId, YouTubePlayer.OnFullscreenListener listener) {
        mListener = listener;
        mVideoId = videoId;
        if (mYouTubePlayer != null) {
            mYouTubePlayer.cueVideo(videoId);
        }
    }

    public void play() {
        if (mYouTubePlayer != null) mYouTubePlayer.play();
    }

    public void pause() {
        if (mYouTubePlayer != null) mYouTubePlayer.pause();
    }
}
