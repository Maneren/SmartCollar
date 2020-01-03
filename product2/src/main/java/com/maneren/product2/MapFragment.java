package com.maneren.product2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class MapFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String TAG = "MapFragment";

    private Map map;

    private Context context;
    private MainActivity activity;
    private Arduino2 mArduino2;
    private Arduino mArduino;
    private Data dogData = new Data();

    private ConstraintLayout popup;
    //private Space marginSpacer;

    private Gson gson = new Gson();

    static MapFragment newInstance() {
        return new MapFragment();
    }

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        map = new Map(activity, this);
        map.getLocationPermission();

//        mArduino2 = new Arduino2(activity);
//        mArduino2.setListener(this::onRecieveCallback);

        mArduino = new Arduino(activity);
        mArduino.setListener(this::onRecieveCallback);
    }

    void passActivity(MainActivity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    void setOnFragmentInteractionListener(OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    private void onRecieveCallback(String received) {
        Log.d(TAG, received);
        dogData = gson.fromJson(received, Data.class);
        map.redrawDogMarker(dogData);
    }

    public void testCallback(String received) {
        Log.d(TAG, received);
        dogData = gson.fromJson(received, Data.class);
        //if(dogData.getTarget().equals("m")){}
        map.redrawDogMarker(dogData);
    }
}

