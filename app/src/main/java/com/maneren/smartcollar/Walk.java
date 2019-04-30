package com.maneren.smartcollar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Walk extends AppCompatActivity {
    TextView timerTextView;
    Arduino arduino;
    SMS sms;
    Context context;
    HashMap <String, String> locations;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        arduino = null;
        sms = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        timerTextView = findViewById(R.id.walk_time);
        context = this.getApplicationContext();
        if (locations != null) locations.clear();

        timer = new Timer();
        timer.setListener(new Timer.Listener() {
            public void recieveCallback(String time) {
                timerUpdate(time);
            }
        });
    }

    public void timerUpdate(String time){
        timerTextView.setText(time);
    }

    public void onRecieveCallback(String recieved){
        String[] data = recieved.split("/-/");
        String header = data[0];
        String body = data[1];
        Toast.makeText(context, header + ": " + body, Toast.LENGTH_SHORT).show();
        if(header.equals(Communication.LOCATION_DATA)){
            String[] coordinates = body.split("\\w");
            String lat = coordinates[0];
            String lon = coordinates[1];
            locations.put(lat,lon);
        }
    }

    public void useUSB(View view){
        timer.start();
        arduino = new Arduino(this);
        arduino.setListener(new Arduino.Listener() {
            public void recieveCallback(String data) {
                onRecieveCallback(data);
            }
        });
        arduino.connect();
    }

    public void useSMS(View view){
        sms = new SMS(this);
        sms.setListener(new SMS.Listener() {
            public void recieveCallback(String data) {
                onRecieveCallback(data);
            }
        });
        sms.setDefaultNum("737710634");
        sms.send("TEST" + "ic-config:c545a5b41;param:9557");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Finish Walk")
                .setMessage("Are you sure you want to finish the walk?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
