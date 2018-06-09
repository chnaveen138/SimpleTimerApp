package com.example.naveen.eggtimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.util.Log;
import android.widget.Button;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    TextView timeView;
    SeekBar timeSlider;
    Button startOrStop;
    boolean stopTimer = false;
    CountDownTimer cdt;

    // A method that is used to change the text in TextView that is timeView
    public void changeTimer(int secondsRemaining){
        int minutes = secondsRemaining/60; //Minutes in TextView
        String seconds = Integer.toString(secondsRemaining - minutes*60); //Seconds in TextView
        //Seconds can be calculated using remaining time minus minutes converted into seconds.
        if(seconds.length() < 2){
            seconds = "0" + seconds;
        }
        timeView.setText(minutes + ":" + seconds);
    }

    //When startTimer or stopTimer is pressed this method executes
    public void startOrStopTimer(View view){
        timeView = findViewById(R.id.timeView);
        timeSlider = findViewById(R.id.timeSlider);
        startOrStop = findViewById(R.id.startOrStop);

        //If Timer is running these actions should occur
        if(stopTimer){
            stopTimer = false; //Making timer condition false
            startOrStop.setText("Start Timer"); //Making button text to Start Timer
            timeView.setText("0:30"); //Resetting timer to 30 seconds
            timeSlider.setProgress(30); //Setting the progress of SeekBar to 30
            timeSlider.setEnabled(true); //Enabling the SeekBar
            cdt.cancel(); //Cancelling the timer task
        }
        else{ //If Timer is not running these actions should occur
            stopTimer = true; //Making timer condition true
            startOrStop.setText("Stop Timer"); // Making button text to Stop timer
            timeSlider.setEnabled(false); //Disabling the SeekBar
            /*Reinitializing the timer and for indicating the first parameter of the timer
             we use getProgress method of SeekBar*/
           cdt = new CountDownTimer(timeSlider.getProgress() * 1000 + 100, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.i("Seconds Remaining: ", Long.toString(millisUntilFinished/1000));
                    changeTimer((int) millisUntilFinished/1000);
                    timeSlider.setProgress((int)millisUntilFinished/1000);
                }
                //This method indicates what to do when timer finishes
                @Override
                public void onFinish() {
                    timeView.setText("0:00");
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mp.start();
                    startOrStop.setText("Start Timer");
                    stopTimer = false;
                    timeSlider.setProgress(30);
                    timeSlider.setEnabled(true);
                }
            }.start();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeView = findViewById(R.id.timeView);

        timeSlider = findViewById(R.id.timeSlider);
        //Default initializations of SeekBar
        timeSlider.setMax(600);//Setting the maxvalue of SeekBar to 600 seconds
        timeSlider.setProgress(30);//Default value will be 30 seconds

        //To change text in TextView according to the change in SeekBar
        timeSlider.setOnSeekBarChangeListener(new OnSeekBarChangeListener (){
           @Override
           public void onStartTrackingTouch(SeekBar seekBar){

           }
           @Override
            public void onStopTrackingTouch(SeekBar seekBar){

           }
           @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
               changeTimer(progress);
           }

        });
    }
}
