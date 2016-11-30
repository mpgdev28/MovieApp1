package com.cydonia.movieapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by cydonia on 10/10/2016.
 */

public class SquareImageView extends ImageView {

    private static int width;
    private static int height;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*if(getMeasuredWidth() > width){
            width = getMeasuredWidth();
        }

        if(getMeasuredHeight() > height){
            height = getMeasuredHeight();
        }*/

        setMeasuredDimension(360, 515);
        //setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }
}
