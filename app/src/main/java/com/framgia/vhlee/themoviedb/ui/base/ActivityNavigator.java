package com.framgia.vhlee.themoviedb.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.framgia.vhlee.themoviedb.utils.Constants;

public class ActivityNavigator implements Navigator {
    protected final Activity mActivity;
    public ActivityNavigator(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    @Override
    public Context getContext() {
        return mActivity;
    }
}
