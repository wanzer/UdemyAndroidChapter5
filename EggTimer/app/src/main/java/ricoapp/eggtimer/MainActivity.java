package ricoapp.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static int progress = 0;
    static int minCount = 0;
    TextView textSec;
    TextView textMin;
    MediaPlayer media;
    SeekBar seekBar;
    CountDownTimer countDownTimer;
    Boolean counterIsActive=false;
    Button button;
    static long start = 0;
    int secLeft;
    public void time(int fullTime){
        int minutes=(int)fullTime/60;
        int seconds=fullTime-minutes*60;
        textMin.setText(Integer.toString(minutes)+":");
        if (fullTime<10)
            textSec.setText("0"+Integer.toString(seconds));
        else
            textSec.setText(Integer.toString(seconds));
    }
    public void play(){
       media = MediaPlayer.create(this,R.raw.airhorn);
       media.start();
    }

    public void resetTimer(){
        textMin.setText("0:");
        textSec.setText("00");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        seekBar.setEnabled(true);
        counterIsActive=false;
        button.setText("Go");
    }

    public void startTimer(View view) {
       if (counterIsActive==false){
            counterIsActive=true;
            seekBar.setEnabled(false);
            button.setText("Stop");

           countDownTimer = new CountDownTimer(start+100, 1000) {
                public void onTick(long millisecondsUntilDone) {
                    time((int) millisecondsUntilDone / 1000);
                }

                public void onFinish() {
                   resetTimer();
                    play();
                }
            }.start();
        }else  {
            resetTimer();
       }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.timer);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        textSec = (TextView)findViewById(R.id.showSeconds);
        textMin = (TextView)findViewById(R.id.showMinutes);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 progress=seekBar.getProgress();
                start=progress*1000;
                minCount=(int)progress/60;    //total minutes
                secLeft=progress - minCount*60; //sec left after minute
                textMin.setText(Integer.toString(minCount)+":");
                textSec.setText(Integer.toString(secLeft));
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
