package com.maneren.smartcollar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialDevice;

import java.util.HashMap;
import java.util.Map;


public class Arduino {
    private UsbManager usbManager;
    private Context context;
    private UsbDevice device;

    Arduino(Activity activity){
        context = activity.getApplicationContext();
        usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        Log.d("USB", "created");
    }

    void connect() {
        final String ACTION_USB_PERMISSION = "com.maneren.smartcollar.USB_PERMISSION";
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                if (deviceVID == 0x2341)
                {
                    PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pi);
                    keep = false;
                }
                if (!keep) break;
            }
        } else Toast.makeText(context, "No USB device detected", Toast.LENGTH_SHORT).show();
    }

    public UsbDevice getDevice (){
        return device;
    }

    void send(UsbSerialDevice serialPort, String msg){
        serialPort.write(msg.getBytes());
    }

    void disconnect(UsbSerialDevice serialPort){
        serialPort.close();
    }
}
