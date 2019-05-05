package com.maneren.smartcollar;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class Walk extends AppCompatActivity {
    TextView timerTextView;
    Arduino arduino;
    SMS sms;
    Context context;
    HashMap <String, Data> locations;
    Timer timer;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sms = new SMS(this);
        sms.setListener(this::onRecieveCallback);
        arduino = new Arduino(this);
        arduino.setListener(this::onRecieveCallback);

        arduino = null;
        sms = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        timerTextView = findViewById(R.id.walk_time);
        context = this.getApplicationContext();

        if (locations != null) locations.clear();

        timer = new Timer();
        timer.setListener(this::timerUpdate);
    }

    public void timerUpdate(String time){
        timerTextView.setText(time);
    }

    public void onRecieveCallback(String recieved){
        Toast.makeText(context, recieved, Toast.LENGTH_SHORT).show();
        /*Data data = gson.fromJson(recieved, Data.class);
        locations.put(Integer.toString(locations.size()), data);*/
    }

    public void useUSB(View view){
        timer.start();

        arduino.connect();
    }

    public void useSMS(View view){
        Toast.makeText(this.getApplicationContext(),"sending",Toast.LENGTH_SHORT).show();
        sms.send("TEST" + "ic-config:c545a5b41;param:9559", /*"739323482"*/"737710634");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Finish Walk")
                .setMessage("Are you sure you want to finish the walk?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onDestroy(){
        if (arduino != null) {
            arduino.disconnect();
        }
        if (sms != null) {
            sms.send(Communication.END);
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1)
            if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                sms.checkForPermission(this);
            }
    }

    @Override
    public void onPause(){
        timer.pause();
        super.onPause();
    }
}
