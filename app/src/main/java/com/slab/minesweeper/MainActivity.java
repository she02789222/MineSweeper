package com.slab.minesweeper;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.slab.minesweeper.Timer.ImageTimer;
import com.slab.minesweeper.views.grid.Grid;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    public static final int RC_GAMESET = 1;
    public static final int RC_INFO = 2;
    public static int bombNumber = 50;
    public static int width = 15;
    public static int height = 23;
    public Button button = null;
    public Grid grid = null;
    public static Vibrator vibrator = null;
    public ImageView digitHundredsImage = null;
    public ImageView digitTensImage = null;
    public ImageView digitUnitImage = null;
    public ImageView flagImage_0 = null;
    public ImageView flagImage_1 = null;
    public ImageView flagImage_2 = null;
    private long currentTime = 0;
    private long lastTime = 0;
    //Intent i = new Intent(MainActivity.this, GameSetActivity.class);
    //Intent i2 = new Intent(MainActivity.this, InfoActivity.class);


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            currentTime = System.currentTimeMillis();
            if(currentTime - lastTime >= 2000) {
                Toast.makeText(this, "Press down the back key again to exit.", Toast.LENGTH_SHORT).show();
                lastTime = currentTime;
            }
            else {
//                intentInfo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                Log.e("finish","1");
//                startActivity(intentInfo);
//                Log.e("finish","2");
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
                System.exit(1);
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settingSP = getSharedPreferences("setting", MODE_PRIVATE);
        bombNumber = settingSP.getInt("BOMB", 50);
        width = settingSP.getInt("WIDTH", 15);
        height = settingSP.getInt("HEIGHT", 23);
        button = (Button)findViewById(R.id.button);
        Button gameSetButton = (Button)findViewById(R.id.gameSetButton);
        Button infoButton = (Button)findViewById(R.id.infoButton);
        grid = (Grid) findViewById(R.id.minesweeperGridView);

        digitUnitImage = (ImageView)findViewById(R.id.digit_unit);
        digitTensImage = (ImageView)findViewById(R.id.digit_tens);
        digitHundredsImage = (ImageView)findViewById(R.id.digit_hundreds);

        flagImage_0 = (ImageView)findViewById(R.id.bombNumberCounterImage0);
        flagImage_1 = (ImageView)findViewById(R.id.bombNumberCounterImage1);
        flagImage_2 = (ImageView)findViewById(R.id.bombNumberCounterImage2);


        GameEngine.getInstance().createGrid(this, grid);

        GameEngine.getButton(button);
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);

        GameEngine.getInstance().timerFlag = true;
        final Intent intent = new Intent(this, GameSetActivity.class);
        final Intent intentInfo = new Intent(this, InfoActivity.class);

        Log.e("before findTimerView","working");
        GameEngine.getInstance().setImageTimer(digitUnitImage, digitTensImage, digitHundredsImage);

        GameEngine.getInstance().setBombNumberDisplay(flagImage_0, flagImage_1, flagImage_2);


        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInfoActivity(intentInfo);
            }
        });
        //game set button
        gameSetButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                startGameSetActivity(intent);
            }
        });
        //face button
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //GameEngine.getInstance().playNewGameSound();
                restartGame(button);
            }
        });
    }

    public void startGameSetActivity(Intent intent) {
        startActivityForResult(intent, RC_GAMESET);
    }
    public void startInfoActivity(Intent intent){ startActivityForResult(intent, RC_INFO);}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_GAMESET) {
            if(resultCode == RESULT_OK) {
                bombNumber = data.getIntExtra("BOMB_NUMBER", 50);
                width = data.getIntExtra("WIDTH", 15);
                height = data.getIntExtra("HEIGHT", 23);
                restartGame(button);
            }
        }
    }
    public void restartGame(Button button) {
        button.setBackgroundResource(R.drawable.smile);
        GameEngine.setLose(false);
        GameEngine.setWin(false);
        GameEngine.getInstance().BOMB_NUMBER = bombNumber;
        GameEngine.getInstance().flagCount = bombNumber;
        GameEngine.getInstance().drawBombDisplay();
        GameEngine.getInstance().timeCount = 0;
        GameEngine.getInstance().drawTimeDisplay();
        GameEngine.HEIGHT = height;
        GameEngine.WIDTH = width;
        if(!GameEngine.getInstance().timerFlag) {
            GameEngine.getInstance().countTimer.cancel();
        }
        GameEngine.getInstance().createGrid(MainActivity.this, grid);
        GameEngine.getInstance().timerFlag = true;
    }
}
