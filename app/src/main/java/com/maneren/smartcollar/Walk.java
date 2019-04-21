package com.maneren.smartcollar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Walk extends AppCompatActivity {
    Arduino arduino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        int time = 0;
        int[] locations = {};
    }

    public void useUSB(View view){
        arduino = new Arduino(this);
        arduino.connect();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //arduino.unconnect();
    }
}
