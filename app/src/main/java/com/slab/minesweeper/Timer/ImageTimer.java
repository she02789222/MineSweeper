package com.slab.minesweeper.Timer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.slab.minesweeper.MainActivity;
import com.slab.minesweeper.R;

public class ImageTimer extends View {
    public ImageView digitUnitImage;
    public ImageTimer(Context context) {
        super(context);
    }
    public int number = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
     super.onMeasure(widthMeasureSpec, heightMeasureSpec);
     }


    public void draw() {
        switch (number) {
            case 0:
                drawZero();
                break;
            case 1:
                drawOne();
                break;
            case 2:
                drawTwo();
                break;
            case 3:
                drawThree();
                break;
            case 4:
                drawFour();
                break;
            case 5:
                drawFive();
                break;
            case 6:
                drawSix();
                break;
            case 7:
                drawSeven();
                break;
            case 8:
                drawEight( );
                break;
            case 9:
                drawNine();
                break;
        }
    }

    public void drawZero( ) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.zero0);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawOne() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.one1);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawTwo( ) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.two2);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawThree() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.three3);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawFour( ) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.four4);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawFive( ) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.five5);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawSix() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.six6);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawSeven() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.seven7);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawEight() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.eight8);
        digitUnitImage.setImageDrawable(drawable);
    }
    public void drawNine() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.nine9);
            digitUnitImage.setImageDrawable(drawable);
    }
}
