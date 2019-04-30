package com.maneren.smartcollar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openMap(View view) {
        Intent intent = new Intent(this, MapView.class);
        startActivity(intent);
    }

    public void startWalk(View view) {
        Intent intent = new Intent(this, Walk.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openConfig(View view) {
        Intent intent = new Intent(this, CollarConfig.class);
        startActivity(intent);
    }
}
