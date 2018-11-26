package com.framgia.vhlee.themoviedb.data.source.local;

import com.framgia.vhlee.themoviedb.data.source.DataSource;

public class LocalDataSource implements DataSource.Local {
    private static LocalDataSource sLocalDataSource;

    private LocalDataSource() {
    }

    public static LocalDataSource getInstance() {
        if (sLocalDataSource == null) sLocalDataSource = new LocalDataSource();
        return sLocalDataSource;
    }
}
