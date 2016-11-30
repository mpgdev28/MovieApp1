package com.cydonia.movieapp.data;

import android.provider.BaseColumns;

/**
 * Created by cydonia on 11/29/2016.
 */

public class MovieListingContract implements BaseColumns {
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_POSTERPATH  = "posterpath";
}
