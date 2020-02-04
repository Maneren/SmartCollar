package com.maneren.product;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FenceFragment extends Fragment {
    private static final String TAG = "FenceFragment";

    private Map map;
    private View view;
    private MainActivity activity;
    private Context context;

    private ArrayList<LatLng> points = new ArrayList<>(0);
    private int i = 0;

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
        // add listeners to buttons
        Button select = view.findViewById(R.id.fence_select);
        select.setOnClickListener(v -> {
            LatLng point = map.getPosition();
            Log.d(TAG, point.toString());
            points.add(i++, point);
            redrawVFence();
        });
        Button deleteLast = view.findViewById(R.id.fence_delete_last);
        deleteLast.setOnClickListener(v -> {
            if(i > 0){
                points.remove(--i);
            }
            redrawVFence();
        });
        Button deleteAll = view.findViewById(R.id.fence_delete_all);
        deleteAll.setOnClickListener(v -> {
            points = new ArrayList<>(0);
            i = 0;
            redrawVFence();
        });
        File file = new File(context.getFilesDir(), "points.txt");
        Button save = view.findViewById(R.id.fence_save);
        save.setOnClickListener(v -> {
            String filename = "points.txt";
            String fileContents = Arrays.toString(points.toArray(new LatLng[0]));
            try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(fileContents.getBytes());
            } catch (Exception e){
                Log.e(TAG, e.getMessage());
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(map != null)
            map.addMapToFrag();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(map == null){
            map = new Map(activity, this, this::onMapReady);
            map.initMap();
        }

    }

    void passActivity(MainActivity activity) {
        this.activity = activity;
        context = activity.getApplicationContext();
    }

    private void onMapReady(){
        // Create drop pin using custom image
        FrameLayout mapWrapper = view.findViewById(R.id.map_container);
        ImageView dropPinView = new ImageView(context);
        dropPinView.setImageResource(R.drawable.ic_add_location);
        // Statically Set drop pin in center of screen
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);
        dropPinView.setLayoutParams(params);
        mapWrapper.addView(dropPinView);
    }

    private void redrawVFence(){
        Log.d(TAG, points.toString());
        map.showPolygon(points);
    }
}

