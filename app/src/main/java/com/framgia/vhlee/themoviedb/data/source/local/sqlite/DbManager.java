package com.framgia.vhlee.themoviedb.data.source.local.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableArrayList;

import com.framgia.vhlee.themoviedb.data.model.Movie;
import com.framgia.vhlee.themoviedb.data.source.DataSource;
import com.framgia.vhlee.themoviedb.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DbManager implements DataSource.Local {
    private static final String STRING_QUERY = " = ? ";
    private static final String QUERY_SELECT = "SELECT  * FROM ";
    private static DbManager sInstance;
    private DbHelper mDbHelper;

    public DbManager(DbHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    public static DbManager getInstance(DbHelper dbHelper) {
        if (sInstance == null) {
            sInstance = new DbManager(dbHelper);
        }
        return sInstance;
    }

    @Override
    public int getCount() {
        String countQuery = StringUtils.append(QUERY_SELECT, DbHelper.TABLE_NAME);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @Override
    public ObservableArrayList<Movie> getMovies() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DbHelper.TABLE_NAME,
                createProjection(),
                null,
                null,
                null,
                null,
                null
        );
        ObservableArrayList<Movie> movies = new ObservableArrayList<>();
        movies.addAll(createMovies(cursor));
        db.close();
        return movies;
    }

    @Override
    public Movie getMovie(int id) {
        Movie movie = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME,
                createProjection(),
                StringUtils.append(DbHelper.COLLUM_ID, STRING_QUERY),
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            movie = createMovie(cursor);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return movie;
    }

    @Override
    public boolean insert(Movie movie) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLLUM_ID, movie.getId());
        values.put(DbHelper.COLLUM_TITLE, movie.getTitle());
        values.put(DbHelper.COLLUM_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(DbHelper.COLLUM_STATUS, movie.getStatus());
        values.put(DbHelper.COLLUM_BACKDROP_PATH, movie.getBackdropPath());
        values.put(DbHelper.COLLUM_OVER_VIEW, movie.getOverview());
        values.put(DbHelper.COLLUM_RELEASE_DATE, movie.getReleaseDate());
        db.insert(DbHelper.TABLE_NAME, null, values);
        db.close();
        return true;
    }

    @Override
    public boolean delete(Movie movie) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DbHelper.TABLE_NAME,
                StringUtils.append(DbHelper.COLLUM_ID, STRING_QUERY),
                new String[]{String.valueOf(movie.getId())});
        db.close();
        return true;
    }

    @Override
    public boolean isFavourite(Movie movie) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME,
                null,
                StringUtils.append(DbHelper.COLLUM_ID, STRING_QUERY),
                new String[]{String.valueOf(movie.getId())},
                null,
                null,
                null);
        try {
            return cursor.moveToFirst();
        } finally {
            db.close();
        }
    }

    private List<Movie> createMovies(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        if (cursor.moveToFirst()) {
            movies = new ArrayList<>();
            do {
                movies.add(createMovie(cursor));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return movies;
    }

    private Movie createMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(DbHelper.COLLUM_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(DbHelper.COLLUM_TITLE)));
        movie.setVoteAverage(cursor.getFloat(cursor.getColumnIndex(DbHelper.COLLUM_VOTE_AVERAGE)));
        movie.setStatus(cursor.getString(cursor.getColumnIndex(DbHelper.COLLUM_STATUS)));
        movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(DbHelper.COLLUM_BACKDROP_PATH)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(DbHelper.COLLUM_OVER_VIEW)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(DbHelper.COLLUM_RELEASE_DATE)));
        return movie;
    }

    private String[] createProjection() {
        return new String[]{
                DbHelper.COLLUM_ID,
                DbHelper.COLLUM_TITLE,
                DbHelper.COLLUM_VOTE_AVERAGE,
                DbHelper.COLLUM_STATUS,
                DbHelper.COLLUM_BACKDROP_PATH,
                DbHelper.COLLUM_OVER_VIEW,
                DbHelper.COLLUM_RELEASE_DATE,
        };
    }
}
