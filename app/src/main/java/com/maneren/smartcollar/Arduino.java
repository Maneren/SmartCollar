package com.maneren.smartcollar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialInterface;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Arduino {
    private UsbManager usbManager;
    private Context context;

    Arduino(Activity activity){
        context = activity.getApplicationContext();
        usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        Log.d("USB", "created");
    }

    protected void connect() {
        UsbDevice device;
        //UsbDeviceConnection connection;
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
                } /*else {
                    connection = null;
                    device = null;
                }*/

                if (!keep) break;
            }
        } else Toast.makeText(context, "No USB device detected", Toast.LENGTH_SHORT).show();
    }

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = new String(arg0, StandardCharsets.UTF_8).concat("/n");
            Log.d("USB", data);
        }
    };

}
