package com.cydonia.movieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.cydonia.movieapp.data.MovieListing;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cydonia on 10/9/2016.
 */

public class FetchMovieDataTask extends AsyncTask<Sort, Integer, List<MovieListing>> {
    private static final String TAG = "CYDONIA";
    private static final String movieDbApiKey = "9a91fc8e2d367b33483c656afa59342f";
    List<MovieDataReceiver> movieDataReceiverList;

    public FetchMovieDataTask() {
        movieDataReceiverList = new ArrayList<>();
    }

    public void addListener(MovieDataReceiver receiver) {
        if (!movieDataReceiverList.contains(receiver)) {
            movieDataReceiverList.add(receiver);
        }
    }

    public void removeListener(MovieDataReceiver receiver){
        if(movieDataReceiverList.contains(receiver)){
            movieDataReceiverList.remove(receiver);
        }
    }

    /**
     * Params that can be passed in will be language, #pages
     *
     * @param params
     * @return
     */
    @Override
    protected List<MovieListing> doInBackground(Sort... params) {
        return requestPopularMovies(params[0]);
    }

    @Override
    protected void onPostExecute(List<MovieListing> result) {
        super.onPostExecute(result);
        for(MovieDataReceiver receiver : movieDataReceiverList){
            receiver.onPopulareMoviesReceieved(result);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    private List<MovieListing> requestPopularMovies(Sort sortVal) {

        List<MovieListing> movieListings = new ArrayList<>();

        Uri.Builder builder = Uri.parse("https://api.themoviedb.org/3/movie/" + sortVal.getQueryStr()).buildUpon();
        builder.appendQueryParameter("api_key", movieDbApiKey);
        builder.appendQueryParameter("language", "en-US");
        builder.appendQueryParameter("page", "2");
        Uri uri = builder.build();
        try {
            URL popularMovieUrl = new URL(uri.toString());
            HttpURLConnection popularMovieConn = (HttpURLConnection) popularMovieUrl.openConnection();
            InputStream popularMovieStream = popularMovieConn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(popularMovieStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {

                stringBuilder.append(line);
            }

            Log.d(TAG, stringBuilder.toString());

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            JsonElement popularMoviesJson = new JsonParser().parse(stringBuilder.toString());
            JsonArray movies = popularMoviesJson.getAsJsonObject().getAsJsonArray("results");
            Iterator<JsonElement> movieIter = movies.iterator();
            while (movieIter.hasNext()) {
                MovieListing movieListing = gson.fromJson(movieIter.next(), MovieListing.class);
                Log.d(TAG, movieListing.toString());
                movieListings.add(movieListing);
            }

        } catch (MalformedURLException mfe) {
            Log.e(TAG, mfe.getMessage());
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
        }

        return movieListings;
    }

    public interface MovieDataReceiver {
        void onPopulareMoviesReceieved(List<MovieListing> movies);
    }
}
