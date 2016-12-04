package com.cydonia.movieapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cydonia.movieapp.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cydonia on 11/29/2016.
 */

public class MovieFavoriteDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MoviesFav.db";
    private static MovieFavoriteDbHelper instance;

    public static MovieFavoriteDbHelper getInstance(Context context) {
        if(instance == null){
            instance = new MovieFavoriteDbHelper(context);
        }

        return instance;
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieFavoriteContract.TABLE_NAME + " (" +
                    MovieFavoriteContract._ID + " INTEGER PRIMARY KEY," +
                    MovieFavoriteContract.COLUMN_ID + " INT," +
                    MovieFavoriteContract.COLUMN_TITLE + " TEXT," +
                    MovieFavoriteContract.COLUMN_OVERVIEW + " TEXT, " +
                    MovieFavoriteContract.COLUMN_POSTERPATH + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieFavoriteContract.TABLE_NAME;

    public MovieFavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(MainActivity.TAG," Creating SQLite DB");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(MainActivity.TAG," Updating SQLite DB");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addMovieFavorite(MovieListing listing){
        Log.d(MainActivity.TAG, "Adding Movie to Database : " + listing.getTitle());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieFavoriteContract.COLUMN_ID, listing.getId());
        values.put(MovieFavoriteContract.COLUMN_TITLE, listing.getTitle());
        values.put(MovieFavoriteContract.COLUMN_OVERVIEW, listing.getOverview());
        values.put(MovieFavoriteContract.COLUMN_POSTERPATH, listing.getPosterPath());
        db.insert(MovieFavoriteContract.TABLE_NAME, null, values);
        db.close();
    }

    public void removeMovieFavorite(MovieListing listing){

    }

    public void clearDatabase(){
        Log.d(MainActivity.TAG," Clearing SQLite DB");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public List<MovieFavoriteContract> getAllFavorites(){
        List<MovieFavoriteContract> favoritesList = new ArrayList<>();

        String selectQuery = "SELECT * from " + MovieFavoriteContract.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                MovieFavoriteContract fave = new MovieFavoriteContract();
                fave.setId(cursor.getInt(cursor.getColumnIndex(MovieFavoriteContract.COLUMN_ID)));
                fave.setOverview(cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.COLUMN_OVERVIEW)));
                fave.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.COLUMN_POSTERPATH)));
                fave.setTitle(cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.COLUMN_TITLE)));

                favoritesList.add(fave);

            }while(cursor.moveToNext());
        }

        return favoritesList;
    }

}
