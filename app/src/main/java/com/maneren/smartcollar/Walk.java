package com.maneren.smartcollar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.nio.charset.StandardCharsets;

public class Walk extends AppCompatActivity {
    Arduino arduino;
    Context context;
    UsbManager usbManager;
    UsbSerialDevice serialPort;
    final String ACTION_USB_PERMISSION = "com.hariharan.arduinousb.USB_PERMISSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        context = this.getApplicationContext();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        context.registerReceiver(broadcastReceiver, filter);
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
        arduino.disconnect(serialPort);
        context.unregisterReceiver(broadcastReceiver);
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        UsbDeviceConnection connection;
        @Override
        public void onReceive(Context context, Intent intent) {
            UsbDevice device = arduino.getDevice();
            switch (intent.getAction()) {
                case ACTION_USB_PERMISSION:
                    boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                    if (granted) {
                        connection = usbManager.openDevice(device);
                        serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                        if (serialPort != null) {
                            if (serialPort.open()) {
                                serialPort.setBaudRate(9600);
                                serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                                serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                                serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                                serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                                serialPort.read(mCallback);
                                Toast.makeText(context, "USB reciever connected", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.d("SERIAL", "PORT NOT OPEN");
                            }
                        } else {
                            Log.d("SERIAL", "PORT IS NULL");
                        }
                    } else {
                        Log.d("SERIAL", "PERM NOT GRANTED");
                    }
                    break;
                case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                    arduino.connect();
                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED:
                    arduino.disconnect(serialPort);
                    break;
            }
        }
    };

    private UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = new String(arg0, StandardCharsets.UTF_8).concat("/n");
            Log.d("USB", data);
        }
    };
}
