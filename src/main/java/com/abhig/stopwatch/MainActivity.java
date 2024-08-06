package com.abhig.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private int second = 0;
    private boolean running ;
    private boolean wasRunning;
    GifImageView gif;
    TextView timeView;
    ImageView play, reset,stBoy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.playImg);
        reset = findViewById(R.id.resetImg);
        timeView = findViewById(R.id.timeTxt);
        gif = findViewById(R.id.gifBoy);
        stBoy = findViewById(R.id.stopboy);
        stBoy.setVisibility(View.VISIBLE);
        gif.setVisibility(View.INVISIBLE);

        if (savedInstanceState != null){
            second = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
       runTime();
    }

    private void runTime() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = second/3600;
                int minutes = (second % 3600) / 60;
                int seconds = second % 100;

                String time = String.format(Locale.getDefault(),"%d:%02d:%03d", hours, minutes, seconds);
                timeView.setText(time);

                if (running){
                    second++;
                }
                handler.postDelayed(this,10);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    play.setImageResource(R.drawable.play);
                    running = false;
                    gif.setVisibility(View.INVISIBLE);
                    stBoy.setVisibility(View.VISIBLE);
                }
                else {
                    play.setImageResource(R.drawable.pause);
                    running = true;
                    gif.setVisibility(View.VISIBLE);
                    stBoy.setVisibility(View.INVISIBLE);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running= false;
                second =0;
                timeView.setText("0:00:000");
                play.setImageResource(R.drawable.play);
                gif.setVisibility(View.INVISIBLE);
                stBoy.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning){
            running= true;

        }
    }
}
