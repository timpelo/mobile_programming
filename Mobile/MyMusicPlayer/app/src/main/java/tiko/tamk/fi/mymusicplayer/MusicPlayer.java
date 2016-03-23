package tiko.tamk.fi.mymusicplayer;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MusicPlayer extends IntentService {
    MediaPlayer mp;
    private String TAG = "MyMusic";

    public MusicPlayer() {
        super("MusicPlayer");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStart()");
        onHandleIntent(intent);
        return  START_STICKY;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mp.start();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "destroyed()");
        mp.stop();
    }

}
