package com.cydonia.movieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cydonia on 11/29/2016.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Movies.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieListingContract.TABLE_NAME + " (" +
                    MovieListingContract._ID + " INTEGER PRIMARY KEY," +
                    MovieListingContract.COLUMN_ID + " INT," +
                    MovieListingContract.COLUMN_TITLE + " TEXT," +
                    MovieListingContract.COLUMN_OVERVIEW + " TEXT, " +
                    MovieListingContract.COLUMN_POSTERPATH + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieListingContract.TABLE_NAME;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
