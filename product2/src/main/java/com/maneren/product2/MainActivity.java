package com.maneren.product2;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentTransaction ft;

    private MapFragment mapFragment;
    private ProfileFragment profileFragment;
    private MoreFragment moreFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            mapFragment = MapFragment.newInstance();
            profileFragment = ProfileFragment.newInstance();
            moreFragment = MoreFragment.newInstance();
        }

        displayMapFragment();
    }

    protected void displayMapFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // removes the existing fragment calling onDestroy
        ft.replace(R.id.main_content, mapFragment);
        ft.commit();
    }

    protected void displayProfileFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // removes the existing fragment calling onDestroy
        ft.replace(R.id.main_content, profileFragment);
        ft.commit();
    }

    protected void displayMoreFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // removes the existing fragment calling onDestroy
        ft.replace(R.id.main_content, moreFragment);
        ft.commit();
    }

}
