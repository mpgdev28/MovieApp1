package com.cydonia.movieapp.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.cydonia.movieapp.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by cydonia on 11/9/2016.
 */

public class MovieImageTarget implements Target {

    private MovieListing theMovieListing;
    private ImageView imageView;

    public MovieImageTarget(ImageView imageView, MovieListing movieListing) {
        this.theMovieListing = movieListing;
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public MovieListing getTheMovieListing() {
        return theMovieListing;
    }

    public void setTheMovieListing(MovieListing theMovieListing) {
        this.theMovieListing = theMovieListing;
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        //theMovieListing.setMoviePoster(bitmap);
        //imageView.setImageBitmap(bitmap);
        Log.d(MainActivity.TAG, "Picasso width = " + bitmap.getWidth() + ", height = " + bitmap.getHeight());
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
