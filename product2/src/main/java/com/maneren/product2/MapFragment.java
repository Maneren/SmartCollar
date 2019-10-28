package com.maneren.product2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;

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
    private Arduino2 mArduino;
    private Data dogData = new Data();

    private ConstraintLayout popup;
    private Space marginSpacer;

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

        Log.d(TAG, DegreesConverter.DecimalToDegMinSec(0, 49.739956));
        Log.d(TAG, DegreesConverter.DecimalToDegMinSec(1, 13.999999));
        map.getLocationPermission();

        mArduino = new Arduino2(activity);
        mArduino.setListener(this::onRecieveCallback);

        /*popup = activity.findViewById(R.id.info_popup);
        Log.d(TAG, popup.toString());*/
        marginSpacer = activity.findViewById(R.id.marginSpacer);
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


}

