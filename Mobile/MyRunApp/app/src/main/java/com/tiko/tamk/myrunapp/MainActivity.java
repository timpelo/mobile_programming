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

    private Location latestLocation;
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private Marker marker;
    private LocationManager locationManager;
    final private String TAG = "MainActivity";

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
                    this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                latestLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            // If latest location is stored, move marker to latest location.
            double longitude = latestLocation.getLongitude();
            double latitude = latestLocation.getLatitude();

            Log.d(TAG, longitude + ", " + latitude);

            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

            // Add marker to map. If there is already one, remove it.
            if(marker != null) {
                marker.remove();
                marker = googleMap.addMarker(new MarkerOptions().position(latLng));
            } else {
                marker = googleMap.addMarker(new MarkerOptions().position(latLng));
            }

            ServerConnection serverConnection = new ServerConnection(this);
            serverConnection.execute(latitude, longitude);
        }
    }

    public void registerLocationListener() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, new LocationListener() {
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
