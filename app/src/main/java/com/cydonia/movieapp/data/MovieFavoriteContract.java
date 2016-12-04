package com.cydonia.movieapp.data;

import android.provider.BaseColumns;

/**
 * Created by cydonia on 11/29/2016.
 */

public class MovieFavoriteContract implements BaseColumns {
    public static final String TABLE_NAME = "movies_fav";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_POSTERPATH  = "posterpath";

    private int id;
    private String title;
    private String posterPath;
    private String overview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
