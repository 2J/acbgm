package io.httpj2.bostonhacks;

import android.app.Notification;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;



public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;

    private int getHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
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

        return bgm_n[hour];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        mp = mp.create(this, getSong("n",this.getHour()) );
        mp.setLooping(true);
        mp.start();
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mp.release();
        super.onDestroy();
    }

}
