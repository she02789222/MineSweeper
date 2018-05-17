package com.slab.minesweeper;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class GameSetActivity extends AppCompatActivity {
    static int BASE_BOMB = 10;
    static int BASE_WIDTH = 10;
    static int BASE_HEIGHT = 12;
    //base + progress(0~100) / divider
    static float DIVIDER_BOMB = 1.11f;
    static float DIVIDER_WIDTH = 10f;
    static float DIVIDER_HEIGHT = 4.347826f;
    static boolean bombChanged = false;
    static boolean widthChanged = false;
    static boolean heightChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_set);
        final SeekBar bombSeekBar = (SeekBar)findViewById(R.id.bombNumberSeekBar);
        final SeekBar widthSeekBar = (SeekBar)findViewById(R.id.widthSeekBar);
        final SeekBar heightSeekBar = (SeekBar)findViewById(R.id.heightSeekBar);
        final TextView bombNumberTextView = (TextView)findViewById(R.id.bombNumber);
        final TextView widthTextView = (TextView)findViewById(R.id.widthTextView);
        final TextView heightTextView = (TextView)findViewById(R.id.heightTextView);
        final Button okButton = (Button)findViewById(R.id.okButton);
        final Button cancelButton = (Button)findViewById(R.id.cancelButton);

        bombNumberTextView.setText("Bombs : "+
                MainActivity.bombNumber +
                " / "+
                (int)(bombSeekBar.getMax()/DIVIDER_BOMB + BASE_BOMB));
        bombSeekBar.setProgress((int)((MainActivity.bombNumber-BASE_BOMB)*DIVIDER_BOMB));
        bombSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bombChanged = true;
                bombNumberTextView.setText("Bombs : "
                        +(int)(BASE_BOMB+progress/DIVIDER_BOMB)
                        + " / "
                        +(int)(seekBar.getMax()/DIVIDER_BOMB+BASE_BOMB));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bombNumberTextView.setText("Bombs : "+
                        (int)(BASE_BOMB + seekBar.getProgress()/DIVIDER_BOMB)+
                        " / "+
                        (int)(seekBar.getMax()/DIVIDER_BOMB+BASE_BOMB));
            }

        });

        widthSeekBar.setProgress((int)((MainActivity.width-BASE_WIDTH)*DIVIDER_WIDTH));
        widthTextView.setText("Width : "+
                MainActivity.width +
                " / "+
                (int)(widthSeekBar.getMax()/DIVIDER_WIDTH + BASE_WIDTH));
        widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                widthChanged = true;
                widthTextView.setText("Width : "+
                        (int)(BASE_WIDTH + progress / DIVIDER_WIDTH )+
                        " / "+
                        (int)(seekBar.getMax() / DIVIDER_WIDTH+BASE_WIDTH));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                widthTextView.setText("Width : "+
                        (int)(seekBar.getProgress()/DIVIDER_WIDTH+BASE_WIDTH)+
                        " / "+
                        (int)(seekBar.getMax()/DIVIDER_WIDTH+BASE_WIDTH));
            }
        });

        heightTextView.setText("Height : "+
                MainActivity.height +
                " / "+
                (int)(heightSeekBar.getMax()/DIVIDER_HEIGHT + BASE_HEIGHT));
        heightSeekBar.setProgress((int)((MainActivity.height-BASE_HEIGHT)*DIVIDER_HEIGHT));
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                heightChanged = true;
                heightTextView.setText(
                        "Height : "+
                        (int)(progress / DIVIDER_HEIGHT + BASE_HEIGHT)+
                        " / "+
                        (int)(seekBar.getMax()/DIVIDER_HEIGHT + BASE_HEIGHT));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                heightTextView.setText("Height : "+
                        (int)(seekBar.getProgress()/DIVIDER_HEIGHT+BASE_HEIGHT)+
                        " / "+
                        (int)(seekBar.getMax()/DIVIDER_HEIGHT+BASE_HEIGHT));
            }
        });




        okButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settingSP = getSharedPreferences("setting", MODE_PRIVATE);
                // commit variables
                int bomb = bombChanged ? (int)(bombSeekBar.getProgress() / DIVIDER_BOMB) + BASE_BOMB : MainActivity.bombNumber;
                int width = bombChanged ? (int)(widthSeekBar.getProgress() / DIVIDER_WIDTH) + BASE_WIDTH : MainActivity.width;
                int height = heightChanged ? (int)(heightSeekBar.getProgress() / DIVIDER_HEIGHT) + BASE_HEIGHT : MainActivity.height;
                getIntent().putExtra("BOMB_NUMBER", bomb);
                getIntent().putExtra("WIDTH", width);
                getIntent().putExtra("HEIGHT", height);
                settingSP.edit()
                        .putInt("BOMB", bomb)
                        .putInt("WIDTH", width)
                        .putInt("HEIGHT", height)
                        .commit();
                setResult(RESULT_OK, getIntent());
                bombChanged = false;
                widthChanged = false;
                heightChanged = false;
                finish();
            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
