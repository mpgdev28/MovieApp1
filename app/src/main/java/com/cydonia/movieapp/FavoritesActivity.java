package com.cydonia.movieapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.cydonia.movieapp.data.MovieFavoriteContract;
import com.cydonia.movieapp.data.MovieFavoriteDbHelper;

import java.util.List;

public class FavoritesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        List<MovieFavoriteContract> favoriteContractList = MovieFavoriteDbHelper.getInstance(getApplicationContext()).getAllFavorites();

        for(MovieFavoriteContract favorite : favoriteContractList){
            Log.d(MainActivity.TAG, "Favorite Found = " + favorite.getTitle());
        }

    }
}
