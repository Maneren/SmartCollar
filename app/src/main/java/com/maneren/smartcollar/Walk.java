package com.maneren.smartcollar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Walk extends AppCompatActivity {
    Arduino arduino;
    Context context;
    private Walk instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        arduino = null;
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
        arduino = new Arduino(instance);
        arduino.setListener(new Arduino.Listener() {
            public void recieveCallback(String data) {
                onRecieveCallback(data);
            }
        });
        arduino.connect();
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
        arduino.disconnect();
        //if (sms)
    }


}
