package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView displayTimer;
    SeekBar setTimer;
    Button stop;
    Boolean countDownIsActive = false;
    CountDownTimer countDownTimer;

    public void reset (){
        displayTimer.setText("00 : 10");
        setTimer.setProgress(10);
        countDownTimer.cancel();
        stop.setText("START");
        setTimer.setEnabled(true);
        countDownIsActive = false;
    }

    public void Clicked(View view){

        if (countDownIsActive){
            reset();
            Toast.makeText(getApplicationContext(), "Timer STopped!", Toast.LENGTH_SHORT).show();
        } else {
            countDownIsActive = true;
            setTimer.setEnabled(false);
            stop.setText("STOP");

            Toast.makeText(getApplicationContext(), "Timer Started!", Toast.LENGTH_SHORT).show();
            countDownTimer = new CountDownTimer(setTimer.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(getApplicationContext(), "Times UP!!", Toast.LENGTH_LONG).show();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    reset();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);
        String secondString = Integer.toString(seconds);
        String minuteString = Integer.toString(minutes);
        if (secondString.length() == 1)
            secondString = "0" + secondString;
        if (minuteString.length() == 1)
            minuteString = "0" + minuteString;
        displayTimer.setText(minuteString + " : " + secondString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTimer = (SeekBar) findViewById(R.id.setTimer);
        displayTimer = (TextView) findViewById(R.id.displayTimer);
        stop = (Button) findViewById(R.id.start);

        setTimer.setMax(1200);
        setTimer.setProgress(10);

        setTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}