package com.slab.minesweeper.util;

import android.util.Log;

import java.util.Random;

public class Generator {
    public static int [][] generate(int bombcounter, final int width, final int height) {
        Random r = new Random();
        int bomb = bombcounter;
        int [][] grid = new int[width][height];
        for(int x = 0;x<width;x++){
            for(int y=0;y<height;y++){
                grid[x][y] = 0;
            }
        }
        while(bomb>0) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            if(grid[x][y] !=-1) {
                grid[x][y] = -1;
                bomb--;
            }
        }
        grid = calculateNeighbor(grid, width, height);
        return grid;
    }

    private static int[][] calculateNeighbor(int[][] grid, final int width, final int height){
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                grid[x][y] = getNeighborNumber(grid, x, y, width, height);
            }
        }
        return grid;
    }

    private static int getNeighborNumber(final int grid[][], final int x, final int y, final int width, final int height) {
        if(grid[x][y] == -1) {
            return -1;
        }
        int counter = 0;
        for(int i = -1; i <= 1;i++) {
            for(int j = -1; j<= 1; j++) {
                if(((x+i>=0) && (x+i<width))&&((y+j>=0)&&(y+j<height))) {
                    if(grid[x+i][y+j]==-1)
                        counter++;
                }
            }
        }
        return counter;
    }
}
