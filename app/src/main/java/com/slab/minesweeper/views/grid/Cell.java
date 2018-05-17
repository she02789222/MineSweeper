package com.slab.minesweeper.views.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.slab.minesweeper.BombNumberDisplay.BombNumberDisplay;
import com.slab.minesweeper.GameEngine;
import com.slab.minesweeper.MainActivity;
import com.slab.minesweeper.R;

public class Cell extends BaseCell implements View.OnClickListener, View.OnLongClickListener{

    public Cell(Context context, int x, int y) {
        super(context);
        setPosition(x, y);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public boolean onLongClick(View v) {
        if(!(GameEngine.getInstance().flagCount == 0 && !isFlagged())) {
            if (!isClicked() && !GameEngine.isWin()) {
                GameEngine.getInstance().flag(getXPos(), getYPos());
                MainActivity.vibrator.vibrate(VibrationEffect.createOneShot(50, 1));
                //GameEngine.getInstance().playSetFlagSound();
            } //else if (isClicked() && !GameEngine.isLose() && !GameEngine.isWin()) {
            //}
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if((!isFlagged() && !isClicked())) {
            GameEngine.getInstance().click(getXPos(), getYPos(), 1);
        }
        //else if(isClicked() && !GameEngine.isLose() && !GameEngine.isWin()) {
        //    MainActivity.vibrator.vibrate(VibrationEffect.createOneShot(50, 1));
        //}
        if(GameEngine.getInstance().timerFlag) {
            GameEngine.getInstance().startTimer();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawButton(canvas);
        if(isFlagged()) {
            drawFlag(canvas);
        }
        else {
            if(isClicked()) {
                if(getValue() == -1 && !isRevealed() && !isFlagged()) {
                    drawNormalBomb(canvas);
                }
                else if(getValue() == -1 && !isFlagged() && isRevealed()){
                    drawExplodedBomb(canvas);
                }
                else {
                    drawNumber(canvas);
                }
            }
            else {
                drawButton(canvas);
            }
        }
        if(isClicked() && !isBomb() && isFlagged() && GameEngine.isLose()) {
            drawWrongFlag(canvas);
        }
    }

    private void drawFlag(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.flag);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawButton(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.button);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawNormalBomb(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_normal);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }
    private void drawExplodedBomb(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_exploded);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }
    private void drawWrongFlag(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.wrong_flag);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawNumber(Canvas canvas) {
        Drawable drawable = null;

        switch(getValue()) {
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_0);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_8);
                break;
        }
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

}
