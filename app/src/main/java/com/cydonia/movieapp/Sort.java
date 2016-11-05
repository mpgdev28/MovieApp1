package com.cydonia.movieapp;

/**
 * Created by cydonia on 10/21/2016.
 */

public enum Sort {
    NOW_PLAYING("now_playing", "Now Playing"),
    POPULAR("popular", "Popular"),
    TOP_RATED("top_rated","Top Rated"),
    UPCOMING("upcoming","Upcoming");

    private String queryStr;
    private String title;

    Sort(String queryStr, String title){
        this.queryStr = queryStr;
        this.title = title;
    }

    public String getQueryStr(){
        return queryStr;
    }

    public String getTitle() {
        return title;
    }
}