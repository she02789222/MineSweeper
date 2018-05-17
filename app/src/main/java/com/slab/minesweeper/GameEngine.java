package com.slab.minesweeper;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DeviceAdminService;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.slab.minesweeper.BombNumberDisplay.BombNumberDisplay;
import com.slab.minesweeper.Timer.ImageTimer;
import com.slab.minesweeper.util.Generator;
import com.slab.minesweeper.util.PrintGrid;
import com.slab.minesweeper.views.grid.Cell;
import com.slab.minesweeper.views.grid.Grid;

import java.lang.reflect.WildcardType;
import java.util.Set;
import java.util.Timer;

public class GameEngine {
    private static GameEngine instance;
    public Context context;
    public Cell[][] MinesweeperGrid = null;
    public static boolean win = false;
    public static boolean lose = false;
    public Button button;
    private SoundPool clickSound;
    private SoundPool winSound;
    private SoundPool lostSound;
    private int clickSoundID;
    private int winSoundID;
    private int lostSoundID;
    int [][] GenerateGrid = null;
    public static int WIDTH = MainActivity.width;
    public static int HEIGHT = MainActivity.height;
    public int BOMB_NUMBER = MainActivity.bombNumber;
    public boolean timerFlag = true;
    public  int timeCount = 0;
    ImageTimer []imageTimer = null;
    BombNumberDisplay []bombNumberDisplay = null;
    public CountDownTimer countTimer;
    public int flagCount = BOMB_NUMBER;


    public static boolean isWin() {
        return win;
    }
    public static void setWin(boolean win) {
        GameEngine.win = win;
    }
    public static boolean isLose() {
        return lose;
    }
    public static void setLose(boolean lose) {
        GameEngine.lose = lose;
    }


    public static GameEngine getInstance() {
        if(instance==null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public void setBombNumberDisplay(ImageView one, ImageView two, ImageView three) {
        bombNumberDisplay = new BombNumberDisplay[3];
        for(int i = 0; i < 3; i++) {
            bombNumberDisplay[i] = new BombNumberDisplay(context);
        }
        bombNumberDisplay[0].bombImageView = one;
        bombNumberDisplay[1].bombImageView = two;
        bombNumberDisplay[2].bombImageView = three;
        drawBombDisplay();
    }

    public void drawBombDisplay() {
        for(int i = 0; i < 3; i++) {
            bombNumberDisplay[i].number = getNumberOfDigit(flagCount, i + 1);
            bombNumberDisplay[i].draw();
        }
    }

    public void setImageTimer(ImageView one, ImageView two, ImageView three) {
        imageTimer = new ImageTimer[3];
        for(int i = 0; i < 3; i++) {
            imageTimer[i] = new ImageTimer(context);
        }
        imageTimer[0].digitUnitImage = one;
        imageTimer[1].digitUnitImage = two;
        imageTimer[2].digitUnitImage = three;
    }

    public void startTimer() {
        timeCount = 0;
        timerFlag = false;
        countTimer= new CountDownTimer(1000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //MainActivity.counterText.setText(String.valueOf(timeCount));
                if(timeCount == 900 && !isLose()) {
                    onGameLost();
                }
                drawTimeDisplay();
                timeCount++;
            }
            @Override
            public void onFinish() {

            }
        };
        if(!isLose() && !isWin())
           countTimer.start();
        timerFlag = false;
    }
    public void drawTimeDisplay() {
        for(int i = 0; i < 3; i++) {
            imageTimer[i].number = getNumberOfDigit(timeCount, i + 1);
            imageTimer[i].draw();
        }
    }
    public int getNumberOfDigit(int n, int i) {
        int number = 0;
        while(i>0) {
            number = n % 10;
            n = n / 10;
            i--;
        }
        return number;
    }
    public void createGrid(Context context, Grid grid){
        this.context = context;
        initSound();
        GenerateGrid = Generator.generate(BOMB_NUMBER, WIDTH, HEIGHT);
        MinesweeperGrid = new Cell[WIDTH][HEIGHT];
        setGrid(context, GenerateGrid);
        setGridView(grid);
    }

    private void setGridView(Grid grid) {
        grid.setNumColumns(WIDTH);
        grid.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return WIDTH * HEIGHT;
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
                return getCellAt(position);
            }
        });
    }


    private void setGrid(final Context context, final int[][] grid) {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                if(MinesweeperGrid[x][y] == null) {
                    MinesweeperGrid[x][y] = new Cell(context, x, y);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }
    }

    public Cell getCellAt(int position) {
        int x = position % WIDTH;
        int y = position / WIDTH;
        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt(int x, int y) {
        return MinesweeperGrid[x][y];
    }


    public void click(int x, int y, int playSoundTime) {
        if(playSoundTime != 0) {
            playSoundTime--;
            playClickSound();
        }
        if(x>=0 && y>=0 && x<WIDTH && y<HEIGHT && !getCellAt(x, y).isClicked()) {
            getCellAt(x, y).setClicked(true);
            if(getCellAt(x, y).getValue()==0) {
                for(int xt = -1; xt <= 1; xt++) {
                    for(int yt = -1; yt <=1; yt++) {
                        if(!(xt == 0 && yt ==0)) {
                            click(x + xt, y + yt, playSoundTime);
                        }
                    }
                }
            }
            if(getCellAt(x, y).isBomb()) {
                getCellAt(x,y).setRevealed(true);
                onGameLost();
            }
            if(getCellAt(x, y).isFlagged()) {
                getCellAt(x, y).setFlagged(false);
            }
        }
        checkEnd();
    }

    private boolean checkEnd() {
        //int bombNotFound = BOMB_NUMBER;
        int nonBombCellCount = HEIGHT * WIDTH - BOMB_NUMBER;
        for(int x = 0; x < WIDTH; x++) {
            for(int y= 0; y < HEIGHT; y++) {
                if(!getCellAt(x, y).isBomb() && getCellAt(x, y).isClicked()){
                    nonBombCellCount--;
                }
            }
        }
        if(nonBombCellCount == 0 && !checkBombsClicked()) {
            playWinSound();
            setWin(true);
            button.setBackgroundResource(R.drawable.sunglasses);
            flagBombs();
            if(!timerFlag) {
                countTimer.cancel();
            }
        }
        return false;
    }

    private boolean checkBombsClicked() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                if(MinesweeperGrid[x][y].getValue() == -1) {
                    if(MinesweeperGrid[x][y].isClicked()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void onGameLost() {
        playLostSound();
        setLose(true);
        if(!timerFlag)
            countTimer.cancel();
        button.setBackgroundResource(R.drawable.dead);
        clickAll();
    }

    private void flagBombs() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                if(MinesweeperGrid[x][y].getValue() == -1) {
                    getCellAt(x, y).setFlagged(true);
                    getCellAt(x, y).invalidate();
                }
            }
        }
    }


    private void clickAll() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                if(MinesweeperGrid[x][y].isBomb() && !MinesweeperGrid[x][y].isClicked()) {
                    MinesweeperGrid[x][y].setRevealed(false);
                }
                getCellAt(x, y).setClicked(true);
                getCellAt(x, y).invalidate();
            }
        }
    }
    public void flag(int x, int y) {
        boolean isFlagged = getCellAt(x, y).isFlagged();
        //playSetFlagSound();
        getCellAt(x, y).setFlagged(!isFlagged);
        getCellAt(x, y).invalidate();
    }
    public static void getButton(Button button) {
        GameEngine.getInstance().button = button;
    }

    public void playClickSound() {
        clickSound.play(clickSoundID, 0.5f, 0.5f, 0, 0,1);
    }
    public void playWinSound() {
        winSound.play(winSoundID, 1f, 1f, 0 , 0, 1);
    }
    public void playLostSound() {
        lostSound.play(lostSoundID, 0.5f, 0.5f, 0, 0, 1);
    }
    @SuppressLint("NewApi")
    private void initSound() {
        clickSound = new SoundPool.Builder().build();
        clickSoundID = clickSound.load(context, R.raw.sound_click, 1);
        winSound = new SoundPool.Builder().build();
        winSoundID = winSound.load(context, R.raw.sound_game_win, 1);
        lostSound = new SoundPool.Builder().build();
        lostSoundID = lostSound.load(context, R.raw.sound_game_lost,1);
    }
}
