package com.maneren.product2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class MapFragment extends Fragment {
    private static final String TAG = "MapFragment";

    private Map map;

    private MainActivity activity;
    private Arduino2 mArduino2;
    private Arduino mArduino;
    private Data dogData = new Data();

    private Gson gson = new Gson();

    private Switch switcher;
    private ConstraintLayout popup;

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
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(map == null){
            map = new Map(activity, this);
            map.getLocationPermission();
        }

//        mArduino2 = new Arduino2(activity);
//        mArduino2.setListener(this::onRecieveCallback);
        if(mArduino == null){
            mArduino = new Arduino(activity);
            mArduino.setListener(this::onRecieveCallback);
        }

    }

    void passActivity(MainActivity activity) {
        this.activity = activity;
    }

    private void onRecieveCallback(String received) {
        Log.d(TAG, received);
        dogData = gson.fromJson(received, Data.class);
        map.redrawDogMarker(dogData);
    }

    void testCallback(String received) {
        Log.d(TAG, received);
        dogData = gson.fromJson(received, Data.class);
        //if(dogData.getTarget().equals("m")){}
        map.redrawDogMarker(dogData);
    }
}

