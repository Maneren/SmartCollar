package com.maneren.smartcollar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Walk extends AppCompatActivity {
    Arduino arduino;
    SMS sms;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        arduino = null;
        sms = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        context = this.getApplicationContext();
        int time = 0;
        String[] locations = {};
    }

    public void onRecieveCallback(String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public void useUSB(View view){
        arduino = new Arduino(this);
        arduino.setListener(new Arduino.Listener() {
            public void recieveCallback(String data) {
                onRecieveCallback(data);
            }
        });
        arduino.connect();
    }

    public void useSMS(View view){
        sms = new SMS(this, context);
        sms.setDefaultNum("737710634");
        //sms.sendSMS("TEST", null);
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
        super.onDestroy();
        if (arduino != null) arduino.disconnect();
        //if (sms)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else sms.checkForPermission(this);
                break;
            }
        }
    }
}
