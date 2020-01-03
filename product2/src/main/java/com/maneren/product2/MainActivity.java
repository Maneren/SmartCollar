package com.maneren.product2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private ProfileFragment profileFragment;
    private MoreFragment moreFragment;

    private static final String TAG = "MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_map:
                displayMapFragment();
                return true;
            case R.id.navigation_profile:
                displayProfileFragment();
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
            profileFragment = ProfileFragment.newInstance();
            moreFragment = MoreFragment.newInstance();
        }
        Log.d(TAG, "Started");
        displayMapFragment();
    }

    protected void displayMapFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, mapFragment);
        ft.commit();

        /*popup = this.findViewById(R.id.info_popup);
        Log.d(TAG, popup.toString());*/
    }

    protected void displayProfileFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, profileFragment);
        ft.commit();
    }

    protected void displayMoreFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, moreFragment);
        ft.commit();
    }

    public void share(View view) {
        Communication.shareText(this, "github.com/lambda-collars/Application");
    }

    public void toggleInfoPopup(View view) {
        //Arduino2 mArduino2;
        ConstraintLayout popup = findViewById(R.id.info_popup);
        Switch switcher = findViewById(R.id.info_popup_toggle);
        Log.d(TAG, "open/close ");
        if (!switcher.isChecked()) {
            popup.setVisibility(View.GONE);
        } else {
            popup.setVisibility(View.VISIBLE);
        }
    }

}
