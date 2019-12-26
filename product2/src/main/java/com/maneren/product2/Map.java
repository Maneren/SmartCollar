package com.maneren.product2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class Map {

    static final float DEFAULT_ZOOM = 18f;
    static private final String TAG = "Map";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Activity activity;
    private Context context;
    private Fragment fragment;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private UiSettings mUiSettings;

    Map(Activity act, Fragment frag) {
        activity = act;
        context = act.getApplicationContext();
        fragment = frag;
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");

        SupportMapFragment mapFragment = new SupportMapFragment();

        fragment.getChildFragmentManager().beginTransaction().add(R.id.map_container, mapFragment, "map").commit();

        mapFragment.getMapAsync(googleMap -> {
            Log.d(TAG, "onMapReady: map is ready");
            Toast.makeText(context, "Map is Ready", Toast.LENGTH_SHORT).show();
            mMap = googleMap;

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            mMap.clear(); //clear old markers

            mUiSettings = mMap.getUiSettings();

            // Keep the UI Settings state in sync with the checkboxes.
            mMap.setMyLocationEnabled(true);
            mUiSettings.setZoomControlsEnabled(true);
            mUiSettings.setCompassEnabled(true);
            mUiSettings.setMyLocationButtonEnabled(true);
            mUiSettings.setScrollGesturesEnabled(true);
            mUiSettings.setZoomGesturesEnabled(true);
            mUiSettings.setTiltGesturesEnabled(true);
            mUiSettings.setRotateGesturesEnabled(true);

            if (mLocationPermissionsGranted) {
                getDeviceLocation();

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

            }

            this.addDemoPoint();
        });
        Log.d(TAG, "initMap: initializing map - end");
    }

    void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(context, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mLocationPermissionsGranted = true;
            initMap();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionsGranted = false;
                    Log.d(TAG, "onRequestPermissionsResult: permission failed");
                    return;
                }
            }
            Log.d(TAG, "onRequestPermissionsResult: permission granted");
            mLocationPermissionsGranted = true;
            initMap();
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        try {
            if (mLocationPermissionsGranted) {
                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: found location!");
                        Location currentLocation = task.getResult();
                        if (currentLocation == null) {
                            Log.d(TAG, "Localization process failed");
                            Toast.makeText(context, "Localization process failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                Map.DEFAULT_ZOOM);

                    } else {
                        Log.d(TAG, "onComplete: current location is null");
                        Toast.makeText(context, "Unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    void redrawDogMarker(Data dogData) {
        mMap.clear();
        mMap.addMarker(dogData.getMarkerOptions());
        moveCamera(dogData.getLatLng(), DEFAULT_ZOOM);
    }

    void addDemoPoint() {
        LatLng position = new LatLng(49.7404022, 13.3881837);
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Dog location"));
        moveCamera(position, DEFAULT_ZOOM);
    }
}
