package com.teamtouch.toucher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Button buttonStart;
    private TextView textViewTime;
    private TextView textViewScore;
    CountDownTimer timer;
    float time = 0;
    int clicks = 0;

    ConstraintLayout rl;
    private float screenHeight = 0;
    private float screenWidth = 0;
    private float countUnitX = 100;
    private float countUnitY = 0;
    private float centerX = 0;
    private float centerY = 0;
    private float unit = 0;
    private  boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl = findViewById(R.id.rl);
        imageButton = findViewById(R.id.imageButton);
        buttonStart = findViewById(R.id.buttonStart);
        textViewTime = findViewById(R.id.textViewTime);
        textViewScore = findViewById(R.id.textViewScore);
        Random random = new Random();
        imageButton.setVisibility(View.INVISIBLE);

        timer = new CountDownTimer(999999999, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (isFirst) {
                    Init();
                    isFirst = false;
                }

                time -=0.5f;
                textViewTime.setText("Time: " + (time > 0?String.valueOf(Math.round(time)):"0"));
                if (time < 0f){
                        timer.onFinish();
                }
            }

            @Override
            public void onFinish() {
                buttonStart.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.INVISIBLE);
                textViewTime.setText("Time: 0");
            }
        };
        timer.start();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time > 0) {
                    clicks++;
                    time += 0.5;
                    textViewScore.setText("Whackes: " + clicks);

                    int sX = ( int)((5 + random.nextFloat() * (countUnitX - 25)) * unit);
                    int sY = ( int)((5 + random.nextFloat() * (countUnitY - 25)) * unit);
                    imageButton.setY(sY);
                    imageButton.setX(sX);
                }
            }
        });



        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStart.setVisibility(View.INVISIBLE);
                imageButton.setVisibility(View.VISIBLE);
                clicks = 0;
                time = 5;
                textViewScore.setText("Whackes: " + clicks);
            }
        });
    }


    private void Init() {
        rl.post(new Runnable() {
            @Override
            public void run() {
                screenHeight = rl.getHeight();
                screenWidth = rl.getWidth();
                unit = screenWidth / countUnitX;
                countUnitY = screenHeight / unit;
                centerX = countUnitX / 2.0f;
                centerY = countUnitY / 2.0f;
            }
        });
    }
}