package com.slab.minesweeper.util;

import android.util.Log;
import android.widget.GridView;

public class PrintGrid {

    public static void print(final int[][] grid, final int width, final int height) {
        for(int x = 0; x < width; x++) {
            String printText = "| ";
            for (int y = 0; y < height; y++) {
                printText += String.valueOf(grid[x][y]).replace("-1", "B") + " | ";
            }
        }
    }
}
