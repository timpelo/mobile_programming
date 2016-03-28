package fi.tiko.tamk.mysoundapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class MediaPlayerService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();

        final int soundId = extras.getInt("soundId");
        int timer = extras.getInt("timer");

        new CountDownTimer(timer*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d("DEBUG", "" + millisUntilFinished);
                Intent intent = new Intent();
                int timeLeft = (int)millisUntilFinished / 1000;
                intent.putExtra("timeLeft", timeLeft);
                sendBroadcast(intent);
            }

            public void onFinish() {
                playSound(soundId);
            }
        }.start();
        return 0;
    }

    public void playSound(int soundId) {
        MediaPlayer mp = MediaPlayer.create(this, soundId);
        mp.start();
    }
}

