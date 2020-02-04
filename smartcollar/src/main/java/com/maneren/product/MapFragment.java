package com.maneren.product;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MapFragment extends Fragment {
    private static final String TAG = "MapFragment";

    private Map map;

    private MainActivity activity;
    private Context context;
    private Arduino mArduino;
    private Data dogData = new Data();

    private final Gson gson = new Gson();

    private Switch switcher;
    private ConstraintLayout popup;

    private TextView popupUpdate;
    private TextView popupLoc;
    private TextView popupSigStr;
    private TextView popupAlt;
    private ImageView popup_share_img;

    static MapFragment newInstance() {
        return new MapFragment();
    }

    public MapFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Inflate the layout for this fragment

        // toggle info popup
        switcher = view.findViewById(R.id.info_popup_toggle);
        popup = view.findViewById(R.id.info_popup);
        switcher.setOnClickListener(v -> {
            Log.d(TAG, "open/close ");
            if (!switcher.isChecked()) {
                popup.setVisibility(View.GONE);
            } else {
                popup.setVisibility(View.VISIBLE);
            }
        });

        popupUpdate = view.findViewById(R.id.text_actual_value);
        popupLoc = view.findViewById(R.id.text_latlng_value);
        popupSigStr = view.findViewById(R.id.text_signal_value);
        popupAlt = view.findViewById(R.id.text_altitude_value);

        popup_share_img = view.findViewById(R.id.img_latlng_share);
        popup_share_img.setOnClickListener(v -> shareDogLatlng());
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(map != null) {
            map.addMapToFrag();
            map.addDemoPoint();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(map == null){
            map = new Map(activity, this, null);
            map.getLocationPermission();
        }

        if(mArduino == null){
            mArduino = new Arduino(activity);
            mArduino.setListener(this::onRecieveCallback);
        }

    }

    void passActivity(MainActivity activity) {
        this.activity = activity;
        context = activity.getApplicationContext();
    }

    private void onRecieveCallback(String received) {
        Log.d(TAG, received);
        dogData = gson.fromJson(received, Data.class);
        map.redrawDogMarker(dogData);
    }

    void testCallback(String received) {
        Log.d(TAG, received);
        handleDogData(received);
    }

    private void handleDogData(String json){
        dogData = gson.fromJson(json, Data.class);
        map.redrawDogMarker(dogData);
        //update popup fields
        String time =
                Instant.ofEpochSecond(
                        0L ,
                        dogData.getTimestamp() * 1000
                )
                .atZone(
                        ZoneId.systemDefault()
                )
                .format(
                        DateTimeFormatter.ofPattern(
                                "HH:mm:ss" ,
                                Locale.getDefault()
                        )
                );
        popupUpdate.setText(time);

        String latlng = String.format(
                "%s %s",
                DegreesConverter.DecimalToDegMinSec(
                        DegreesConverter.LATITUDE,
                        dogData.getLatitude()
                ),
                DegreesConverter.DecimalToDegMinSec(
                        DegreesConverter.LONGITUDE,
                        dogData.getLongitude()
                )
        );
        popupLoc.setText(latlng);

        String signal = Integer.toString(dogData.getAccuracy());
        popupSigStr.setText(signal);

        String altitude = dogData.getAltitude() + " m";
        popupAlt.setText(altitude);
    }

    private void shareDogLatlng(){
        Communication.shareGeo(context, dogData.getLatLng());
    }
}

