package com.cydonia.movieapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cydonia.movieapp.data.MovieFavoriteDbHelper;
import com.cydonia.movieapp.data.MovieListing;

public class MovieDetails extends Activity{

    public static final String INTENT_EXTRA_DETAILS = "details";
    public static final String INTENT_EXTRA_IMAGE = "details_image";

    private CheckBox favoriteCheckBox;
    private MovieListing selectedMovieItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);
        Bundle extras = getIntent().getExtras();
        ImageView imageView = (ImageView)findViewById(R.id.details_image);
        //shouldn't pass this via extras
        Bitmap bitmap = extras.getParcelable(MovieDetails.INTENT_EXTRA_IMAGE);
        imageView.setImageBitmap(bitmap);

        selectedMovieItem = (MovieListing)extras.getSerializable(MovieDetails.INTENT_EXTRA_DETAILS);

        ((TextView)findViewById(R.id.movie_title)).setText(selectedMovieItem.getTitle());
        ((TextView)findViewById(R.id.details_movie_year)).setText(selectedMovieItem.getReleaseDate());
        ((TextView)findViewById(R.id.details_movie_rating)).setText((int)selectedMovieItem.getVoteAverage() + "/10");
        ((TextView)findViewById(R.id.details_desc)).setText(selectedMovieItem.getOverview());

        favoriteCheckBox = (CheckBox)findViewById(R.id.details_isfavorite);
        addSelectionListenerForFavorite();

        getActionBar().setTitle("Movie Details");
        postponeEnterTransition();

        final View decor = getWindow().getDecorView();
        decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                decor.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home :
                finishAfterTransition();
                return  true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addSelectionListenerForFavorite() {
        favoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(MainActivity.TAG, "Check Box for Favorite changed");
                MovieFavoriteDbHelper dbHelper = MovieFavoriteDbHelper.getInstance(getApplicationContext());
                dbHelper.addMovieFavorite(selectedMovieItem);
            }
        });
    }
}
