package com.maneren.smartcollar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class CollarConfig extends AppCompatActivity {
    private Arduino arduino;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collar_config);

        context = this.getApplicationContext();

        arduino = new Arduino(this);
        arduino.setListener(this::onRecieveCallback);
        //arduino.send(Communication.CONFIG);
    }

    private void onRecieveCallback(String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy(){
        arduino.send(Communication.END);
        arduino.disconnect();
        super.onDestroy();
    }
}
