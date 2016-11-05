package com.cydonia.movieapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.cydonia.movieapp.data.MovieListing;

public class MovieDetails extends Activity{

    public static final String INTENT_EXTRA_DETAILS = "details";
    public static final String INTENT_EXTRA_IMAGE = "details_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle extras = getIntent().getExtras();
        ImageView imageView = (ImageView)findViewById(R.id.details_image);
        Bitmap bitmap = extras.getParcelable(MovieDetails.INTENT_EXTRA_IMAGE);
        imageView.setImageBitmap(bitmap);

        MovieListing selectedMovieItem = (MovieListing)extras.getSerializable(MovieDetails.INTENT_EXTRA_DETAILS);

        ((TextView)findViewById(R.id.movie_title)).setText(selectedMovieItem.getTitle());
        ((TextView)findViewById(R.id.details_movie_year)).setText(selectedMovieItem.getReleaseDate());
        ((TextView)findViewById(R.id.details_movie_rating)).setText((int)selectedMovieItem.getVoteAverage() + "/10");
        ((TextView)findViewById(R.id.details_desc)).setText(selectedMovieItem.getOverview());

        getActionBar().setTitle("Movie Details");
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
}
