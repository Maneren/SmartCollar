package com.maneren.product;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private FenceFragment fenceFragment;
    private MoreFragment moreFragment;

    private static final String TAG = "MainActivity";

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_map:
                displayMapFragment();
                return true;
            case R.id.navigation_fence:
                displayFenceFragment();
                return true;
            case R.id.navigation_more:
                displayMoreFragment();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            mapFragment = MapFragment.newInstance();
            mapFragment.passActivity(this);
            fenceFragment = FenceFragment.newInstance();
            fenceFragment.passActivity(this);
            moreFragment = MoreFragment.newInstance();
            moreFragment.passActivity(this);
        }
        Log.d(TAG, "Started");
        displayMapFragment();
    }

    private void displayMapFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, mapFragment);
        ft.commit();
    }

    private void displayFenceFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, fenceFragment);
        ft.commit();
    }

    private void displayMoreFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, moreFragment);
        ft.commit();
    }

    public void displaySettingsFragment(String type) {
        SettingsFragment settingsFragment = SettingsFragment.newInstance(type);
        settingsFragment.passActivity(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, settingsFragment);
        ft.commit();
    }

    public void share(View view) {
        Communication.shareText(this, "github.com/lambda-collars/Application");
    }

    public void startDemo(View view){
        MapTest mapTest = new MapTest(mapFragment);
    }

}
