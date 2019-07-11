package com.maneren.smartcollar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import com.google.android.gms.maps.CameraUpdate;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapView extends AppCompatActivity
        implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map_view);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng gymik = new LatLng(49.739956, 13.387321);
        LatLng gymik2 = new LatLng(49.739956, 13.4874);
        googleMap.addMarker(new MarkerOptions().position(gymik).title("Dog location"));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(gymik));

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(this.getApplicationContext(), "change", Toast.LENGTH_SHORT).show();
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(gymik2).title("Dog location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(gymik2));
        }, 2000);
    }
}
