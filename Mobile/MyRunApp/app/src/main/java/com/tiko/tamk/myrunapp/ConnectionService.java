package com.tiko.tamk.myrunapp;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ConnectionService extends Service implements Runnable {

    private final String BROADCAST_ACTION = "LOCATION_UPDATE";
    private final String TAG = "ConnectionService";
    private double START_TIME = System.currentTimeMillis();
    private LocationManager locationManager;
    private Location latestLocation;
    private MyListener locationListener;


    public ConnectionService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand()");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new MyListener();
        registerLocationListener();


        new Thread(this).start();
        return START_STICKY;
    }

    @Override
    public void run() {
        Log.d(TAG, "run()");

        while (true) {
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");

        return null;
    }

    public void registerLocationListener() {
        Log.d(TAG, "registerLocationListener()");
        // Checks permissions for gps.
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            // Registers location listener for location manager.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000,
                    20,
                    locationListener);
        }
    }

    public void updateLocation() {
        if (locationManager != null) {
            // Checks permission and gets last location from gps provider.
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                latestLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            // Gets location of the latest location.
            double longitude = latestLocation.getLongitude();
            double latitude = latestLocation.getLatitude();

            // Creates new server connection for storing current location to database.
            ServerConnection serverConnection = new ServerConnection(getApplicationContext()
                    .getResources()
                    .getString(R.string.mykey));

            serverConnection.execute(latitude, longitude, START_TIME);

            // Creates intent and sends broadcast.
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            intent.putExtra("lon", longitude);
            intent.putExtra("lat", latitude);
            sendBroadcast(intent);
        }
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy()");

        // Checks permission and unregisters location listener.
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "listener unregistered");
            locationManager.removeUpdates(locationListener);
        }
    }

    public class MyListener implements LocationListener {

        public MyListener() {

        }
        @Override
        public void onLocationChanged(Location location) {
            updateLocation();
            Log.d(TAG, "location updated");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
