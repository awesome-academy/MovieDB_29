package com.framgia.vhlee.themoviedb.ui.base;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public interface Navigator {
    void startActivity(@NonNull Intent intent);

    Context getContext();
}
