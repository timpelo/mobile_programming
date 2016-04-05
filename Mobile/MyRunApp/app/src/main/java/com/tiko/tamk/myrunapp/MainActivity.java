package com.tiko.tamk.myrunapp;

import android.Manifest;
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

import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Final variables for start time and debug tag.
    final private double START_TIME = System.currentTimeMillis();
    final private String TAG = "MainActivity";

    private Location latestLocation;
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private Marker marker;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finds map fragment.
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        registerLocationListener();
    }

    public void updateLocation() {
        if(locationManager != null) {
            // Checks permission and gets last location from gps provider.
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                latestLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            // Gets location of the latest location and moves camera to that part of the
            // Google Map.
            double longitude = latestLocation.getLongitude();
            double latitude = latestLocation.getLatitude();
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

            // Add marker to map. If there is already one, remove it.
            if(marker != null) {
                marker.remove();
                marker = googleMap.addMarker(new MarkerOptions().position(latLng));
            } else {
                marker = googleMap.addMarker(new MarkerOptions().position(latLng));
            }

            // Creates new server connection for storing current location to database.
            ServerConnection serverConnection = new ServerConnection(this);
            serverConnection.execute(latitude, longitude, START_TIME);
        }
    }

    public void registerLocationListener() {

        // Checks permissions for gps.
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            // Registers location listener for location manager.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000,
                    20,
                    new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    updateLocation();
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
            });
        }
    }
}
