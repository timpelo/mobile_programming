package com.tiko.tamk.myrunapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Jani on 11.4.2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = "MyBroadcastReceiver";
    MapActivity host;

    public MyBroadcastReceiver(MapActivity host) {
        this.host = host;
    }

    @Override
    public void onReceive(Context context, final Intent intent) {
        Log.d(TAG, "onReceive()");

            // Gets data from intent.
            double longitude = intent.getExtras().getDouble("lon");
            double latitude = intent.getExtras().getDouble("lat");

            // Updates latest location and map
            host.updateMap(longitude, latitude);
    }
}
