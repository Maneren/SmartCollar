package com.maneren.product2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private ProfileFragment profileFragment;
    private MoreFragment moreFragment;

    private static final String TAG = "MainActivity";

    //Arduino2 mArduino2;
    private ConstraintLayout popup;

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

    void getPopupLayout(ConstraintLayout layout) {
        popup = layout;
    }

    public void open(View view) {
        popup = findViewById(R.id.info_popup);
        Toast.makeText(this.getApplicationContext(), "open/close", Toast.LENGTH_SHORT).show();
        if (popup.getVisibility() == View.VISIBLE) {
            popup.setVisibility(View.GONE);
        } else {
            popup.setVisibility(View.VISIBLE);
        }
        /*ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) popup.getLayoutParams();
        params.height = 0;
        popup.setLayoutParams(params);*/
        /*Space.LayoutParams params = (Space.LayoutParams) someLayout.getLayoutParams();
        params.height = 0;
        someLayout.setLayoutParams(params);*/
    }

}
