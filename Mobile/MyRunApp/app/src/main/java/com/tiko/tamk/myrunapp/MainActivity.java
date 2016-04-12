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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private Marker marker;
    private ConnectionService service;
    private MyBroadcastReceiver receiver;
    private PolylineOptions lineOptions;
    private Polyline line;
    private boolean isMapReady = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineOptions = new PolylineOptions();

        // Finds map fragment.
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        // Creates broadcast receiver and registers it.
        receiver = new MyBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        registerReceiver(receiver, filter);
    }

    public void stopRunning() {
        Intent intent = new Intent(this, ConnectionService.class);
        stopService(intent);
        line = null;
        lineOptions = null;
        Log.d(TAG, "stopRunning()");
    }

    public void startRunning() {
        // Starts service for location updates.
        if(isMapReady) {
            lineOptions = new PolylineOptions();
            Intent intent = new Intent(this, ConnectionService.class);
            startService(intent);
            Log.d(TAG, "startRunning()");
        } else {
            Toast.makeText(this, "Map is not ready yet", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        isMapReady = true;
    }

    public void updateMap(double longitude, double latitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        // Add new position to polyline options and move camera to new position.
        lineOptions.add(latLng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

        // Add marker to map. If there is already one, remove it.
        if(marker != null) {
            marker.remove();
            marker = googleMap.addMarker(new MarkerOptions().position(latLng));
        } else {
            marker = googleMap.addMarker(new MarkerOptions().position(latLng));
        }

        // Draws line to new position.
        line = googleMap.addPolyline(lineOptions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.runmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.start_run:
                startRunning();
                return true;
            case R.id.stop_run:
                stopRunning();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
