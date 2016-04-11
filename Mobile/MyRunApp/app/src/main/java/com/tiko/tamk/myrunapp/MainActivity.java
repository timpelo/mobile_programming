package com.tiko.tamk.myrunapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MapActivity{

    final private String TAG = "MainActivity";
    private final String BROADCAST_ACTION = "LOCATION_UPDATE";

    private Location latestLocation;
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private Marker marker;
    private ConnectionService service;
    private MyBroadcastReceiver receiver;
    private PolylineOptions lineOptions;
    private Polyline line;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineOptions = new PolylineOptions();

        // Finds map fragment.
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        // Creates broadcast receiver.
        receiver = new MyBroadcastReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Starts service for location updates.
        Intent intent = new Intent(this, ConnectionService.class);
        startService(intent);
    }

    public void updateMap(double longitude, double latitude) {

        LatLng latLng = new LatLng(latitude, longitude);
        lineOptions.add(latLng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

        // Add marker to map. If there is already one, remove it.
        if(marker != null) {
            marker.remove();
            marker = googleMap.addMarker(new MarkerOptions().position(latLng));
        } else {
            marker = googleMap.addMarker(new MarkerOptions().position(latLng));
        }
        line = googleMap.addPolyline(lineOptions);
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, ConnectionService.class);
        stopService(intent);
        super.onDestroy();
    }
}
