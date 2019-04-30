package com.maneren.smartcollar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


class Arduino {
    private UsbManager usbManager;
    private Context context;
    private UsbDevice device;
    private UsbSerialDevice serialPort;
    private Listener mListener;
    private final String ACTION_USB_PERMISSION = "com.hariharan.arduinousb.USB_PERMISSION";

    Arduino(Activity activityArg){
        context = activityArg.getApplicationContext();
        usbManager = (UsbManager) activityArg.getSystemService(Context.USB_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        context.registerReceiver(broadcastReceiver, filter);
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

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        UsbDeviceConnection connection;
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case ACTION_USB_PERMISSION:
                    boolean granted = Objects.requireNonNull(intent.getExtras()).getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
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
                    connect();
                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED:
                    disconnect();
                    break;
            }
        }
    };

    private UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = new String(arg0, StandardCharsets.UTF_8).concat("/n");
            mListener.recieveCallback(data);
            Log.d("USB", data);
        }
    };

    public interface Listener {
        void recieveCallback(String str);
    }

    void setListener(Listener listener) {
        mListener = listener;
    }

    void send(String msg){
        if (serialPort != null) {
            serialPort.write(msg.getBytes());
        }
    }

    void disconnect(){
        if (serialPort != null) {
            send(Communication.END);
            serialPort.close();
        }
        context.unregisterReceiver(broadcastReceiver);
    }
}
