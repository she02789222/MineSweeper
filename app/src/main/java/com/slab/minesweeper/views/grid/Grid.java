package com.slab.minesweeper.views.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.slab.minesweeper.GameEngine;

import java.util.jar.Attributes;

public class Grid extends GridView{
    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        //GameEngine.getInstance().createGrid(context);
        //setNumColumns(GameEngine.WIDTH);
        //setAdapter(new GridAdapter());
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /*
    public class GridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return GameEngine.getInstance().WIDTH * GameEngine.getInstance().HEIGHT;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e("getView", "working");
            return GameEngine.getInstance().getCellAt(position);
        }
    }*/
}
