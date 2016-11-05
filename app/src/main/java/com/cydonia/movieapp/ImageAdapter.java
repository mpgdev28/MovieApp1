package com.cydonia.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.cydonia.movieapp.data.MovieListing;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cydonia on 10/9/2016.
 */

public class ImageAdapter extends BaseAdapter{

    private Context context;
    private final LayoutInflater mInflater;
    private List<MovieListing> nowPlayingList;
    private String movieImagePath = "https://image.tmdb.org/t/p/w185/";
    public ImageAdapter(Context ctx, List<MovieListing> nowPlayingList){
        context = ctx;
        mInflater = LayoutInflater.from(context);
        this.nowPlayingList = nowPlayingList;
    }

    @Override
    public int getCount() {
        return nowPlayingList.size();
    }

    @Override
    public Object getItem(int position) {
        return nowPlayingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nowPlayingList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ImageView imageView;
        if (v == null) {
            // if it's not recycled, initialize some attributes
            v = mInflater.inflate(R.layout.movie_item, parent, false);
            //imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            v.setTag(R.id.movie_image, v.findViewById(R.id.movie_image));
        }

        imageView = (ImageView)v.getTag(R.id.movie_image);
        imageView.setAdjustViewBounds(true);

        MovieListing listing = nowPlayingList.get(position);
        Picasso.with(context).load(movieImagePath + listing.getPosterPath()).into(imageView);

        return v;
    }
}
