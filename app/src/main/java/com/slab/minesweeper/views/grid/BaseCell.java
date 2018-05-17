package com.slab.minesweeper.views.grid;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.slab.minesweeper.GameEngine;

public abstract class BaseCell extends View{
    private int value;
    private boolean isBomb;
    private boolean isClicked;
    private boolean isFlagged;

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    private boolean isRevealed;
    private int x, y;
    private int position;

    public BaseCell(Context context) {
        super(context);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        isBomb = false;
        isRevealed = false;
        isClicked = false;
        isFlagged = false;

        if(value == -1 ){
            isBomb = true;
        }

        this.value = value;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }


    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        this.isClicked = clicked;
        //this.isRevealed = true;
        invalidate();
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        if(!(GameEngine.getInstance().flagCount == 0 && !isFlagged)) {
            isFlagged = flagged;
        }

        GameEngine.getInstance().flagCount += flagged == true ? -1 : 1;
        GameEngine.getInstance().drawBombDisplay();
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.position = y * GameEngine.WIDTH + x;
        invalidate();
    }
}
