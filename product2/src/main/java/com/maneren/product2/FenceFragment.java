package com.maneren.product2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class FenceFragment extends Fragment {
    private static final String TAG = "FenceFragment";

    private Map map;
    private FrameLayout mapWrapper;
    private View view;
    private MainActivity activity;
    private Context context;

    //private Gson gson = new Gson();

    static FenceFragment newInstance() {
        return new FenceFragment();
    }

    public FenceFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fence, container, false);
        // Inflate the layout for this fragment
        Log.d(TAG, "view created");
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
            map.setListener(this::onMapReady);
        }

    }

    void passActivity(MainActivity activity) {
        this.activity = activity;
        context = activity.getApplicationContext();
    }

    private void onMapReady(){
        // Create drop pin using custom image
        mapWrapper = view.findViewById(R.id.map_container);
        ImageView dropPinView = new ImageView(context);
        dropPinView.setImageResource(R.drawable.ic_add_location);
        // Statically Set drop pin in center of screen
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);
        dropPinView.setLayoutParams(params);
        mapWrapper.addView(dropPinView);
    }
}

