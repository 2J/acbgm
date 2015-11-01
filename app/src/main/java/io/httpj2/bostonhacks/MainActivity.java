package io.httpj2.bostonhacks;

import android.app.Notification;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private int playing_now = -1;
    protected static boolean foreground = true;

    private int getHour() {
        Calendar cal = Calendar.getInstance();
        //return cal.get(Calendar.HOUR_OF_DAY);
        return cal.get(Calendar.MINUTE) % 24;
    }

    private int getSong(String theme, int hour) {
        int[] bgm_n = new int[24];
        bgm_n[0]=R.raw.n00;
        bgm_n[1]=R.raw.n01;
        bgm_n[2]=R.raw.n02;
        bgm_n[3]=R.raw.n03;
        bgm_n[4]=R.raw.n04;
        bgm_n[5]=R.raw.n05;
        bgm_n[6]=R.raw.n06;
        bgm_n[7]=R.raw.n07;
        bgm_n[8]=R.raw.n08;
        bgm_n[9]=R.raw.n09;
        bgm_n[10]=R.raw.n10;
        bgm_n[11]=R.raw.n11;
        bgm_n[12]=R.raw.n12;
        bgm_n[13]=R.raw.n13;
        bgm_n[14]=R.raw.n14;
        bgm_n[15]=R.raw.n15;
        bgm_n[16]=R.raw.n16;
        bgm_n[17]=R.raw.n17;
        bgm_n[18]=R.raw.n18;
        bgm_n[19]=R.raw.n19;
        bgm_n[20]=R.raw.n20;
        bgm_n[21]=R.raw.n21;
        bgm_n[22]=R.raw.n22;
        bgm_n[23]=R.raw.n23;

        playing_now = bgm_n[hour];

        return playing_now;
    }

    private float fo_rate = (float).95;
    private boolean fo_play = true;
    private boolean fo_running = false;

    private void fadeOut(boolean play){
        fo_running = true;
        fo_play = play;
        if(fo_rate<0.1) {
            fo_rate = (float).95;
            if(mp != null) mp.release();
            if(fo_play) {
                mp = MediaPlayer.create(this, getSong("n", this.getHour()));
                mp.setVolume(1, 1);
                mp.start();
            }
            fo_running = false;
            return;
        }
        fo_rate = fo_rate*fo_rate;
        mp.setVolume(fo_rate, fo_rate);

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        fadeOut(fo_play);
                    }
                },
                100
        );
    }

    private void play_loop(){
        if(!fo_running && playing_now != getSong("n", this.getHour())) {
            fadeOut(true);
        }

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        play_loop();
                    }
                },
                (foreground? 10000 : 300000)
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        if(mp == null || !mp.isPlaying())
            mp = mp.create(this, getSong("n",this.getHour()) );
        mp.setLooping(true);
        if(!mp.isPlaying()) mp.start();
        super.onStart();
    }

    @Override
    protected void onResume(){
        play_loop();
        foreground=true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        foreground=false;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mp.release();
        super.onDestroy();
    }

}
