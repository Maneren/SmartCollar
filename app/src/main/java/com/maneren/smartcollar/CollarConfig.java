package com.maneren.smartcollar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CollarConfig extends AppCompatActivity {
    Arduino arduino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collar_config);
        arduino = new Arduino(this);
    }
}
