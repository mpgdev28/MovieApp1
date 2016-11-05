package com.cydonia.movieapp;

import android.os.AsyncTask;

import com.cydonia.movieapp.data.MovieListing;

/**
 * Created by cydonia on 11/2/2016.
 */

public class FetchMovieDetailTask extends AsyncTask<String, Integer, MovieListing> {
    @Override
    protected MovieListing doInBackground(String ...params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(MovieListing o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Integer ...values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(MovieListing o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
