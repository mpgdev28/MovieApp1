package com.cydonia.movieapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.cydonia.movieapp.data.MovieListing;
import com.cydonia.movieapp.dialogs.MovieSortFragment;
import com.cydonia.movieapp.settings.SettingsActivity;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends Activity implements MovieSortFragment.MovieSortListener {

    public static final String TAG = "CYDONIA";
    private GridView gridView;
    private ImageAdapter movieImageAdapter;
    private FetchMovieDataTask.MovieDataReceiver movieDataReceiver;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_prefs:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_sort:
                MovieSortFragment fragment = new MovieSortFragment();
                fragment.setSortListener(this);
                fragment.setCancelable(false);
                fragment.show(getFragmentManager(), TAG);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayManager dm = (DisplayManager)getSystemService(DISPLAY_SERVICE);
        Point point = new Point();
        dm.getDisplay(0).getSize(point);

        Log.d(TAG,"Screen size = " + point.x + ", " + point.y);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.d(TAG,"Portrait Orientation");
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG,"Landscape Orientation");
        }else{
            Log.d(TAG,"Unknown Orientation");
        }

        setContentView(R.layout.activity_main);
        movieDataReceiver = new MovieReceiver();
        gridView = (GridView) findViewById(R.id.movie_grid);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageAdapter adapter = (ImageAdapter)parent.getAdapter();
                Intent detailsIntent = new Intent(getApplicationContext(), MovieDetails.class);
                ImageView img = (ImageView) view.findViewById(R.id.movie_image);
                //don't do this - remove
                detailsIntent.putExtra(MovieDetails.INTENT_EXTRA_IMAGE, ((BitmapDrawable) img.getDrawable()).getBitmap());
                //use a parcelable is probably better
                detailsIntent.putExtra(MovieDetails.INTENT_EXTRA_DETAILS, (Serializable)adapter.getItem(position));
                View decor = getWindow().getDecorView();
                View statusBar = decor.findViewById(android.R.id.statusBarBackground);
                View navBar = decor.findViewById(android.R.id.navigationBarBackground);

                String transitionName = getString(R.string.transition_movie_image);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                        new Pair<View, String>(img, transitionName),
                        new Pair<View, String>(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME),
                        new Pair<View, String>(navBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
                startActivity(detailsIntent, options.toBundle());
            }
        });

        loadSelectedSort();
    }

    private void loadSelectedSort() {

        int selectedSort = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("selected_option", R.id.nowplaying_movies);
        Sort selectedSortEnum = Sort.NOW_PLAYING;
        switch (selectedSort) {
            case R.id.popular_movies:
                selectedSortEnum = Sort.POPULAR;
                break;
            case R.id.nowplaying_movies:
                selectedSortEnum = Sort.NOW_PLAYING;
                break;
            case R.id.toprated_movies:
                selectedSortEnum = Sort.TOP_RATED;
                break;
            case R.id.upcoming_movies:
                selectedSortEnum = Sort.UPCOMING;
                break;
            default:
                break;
        }

        getActionBar().setTitle(selectedSortEnum.getTitle());
        FetchMovieDataTask movieFetch = new FetchMovieDataTask();
        movieFetch.addListener(movieDataReceiver);
        movieFetch.execute(selectedSortEnum);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void sortSelected(Sort sortSelection) {
        Log.d(TAG, "Sort Selected = " + sortSelection);
        getActionBar().setTitle(sortSelection.getTitle());
        FetchMovieDataTask movieFetch = new FetchMovieDataTask();
        movieFetch.addListener(movieDataReceiver);
        movieFetch.execute(sortSelection);
    }

    private class MovieReceiver implements FetchMovieDataTask.MovieDataReceiver {
        @Override
        public void onPopulareMoviesReceieved(List<MovieListing> movies) {
            Log.d(TAG, "Received Movie List");
            movieImageAdapter = new ImageAdapter(MainActivity.this, movies);
            gridView.setAdapter(movieImageAdapter);
            gridView.invalidate();

        }
    }
}
