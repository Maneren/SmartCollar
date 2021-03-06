package com.maneren.smartcollar;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

class SMS {

    private Listener mListener;
    private final Activity activity;
    private final Context context;

    SMS(Activity activityArg){
        activity = activityArg;
        context = activity.getApplicationContext();
        checkForPermission(activity);
        final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
        final String SMS_SENDER = "123456789";
        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                if ((intent != null) &&
                        (intent.getAction() != null) &&
                        (intent.getExtras() != null) &&
                        (ACTION.compareToIgnoreCase(intent.getAction()) == 0)) {
                    Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
                    if (pduArray != null) {
                        SmsMessage[] messages = new SmsMessage[pduArray.length];
                        for (int i = 0; i < pduArray.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
                        }
                        // SMS Sender, example: 123456789
                        String sms_from = messages[0].getDisplayOriginatingAddress();

                        //Lets check if SMS sender is 123456789
                        if (sms_from.equalsIgnoreCase(SMS_SENDER)) {
                            StringBuilder bodyText = new StringBuilder();

                            // If SMS has several parts, lets combine it :)
                            for (SmsMessage message : messages) {
                                bodyText.append(message.getMessageBody());
                            }
                            //SMS Body
                            String body = bodyText.toString();
                            //Send sms to activity via listener
                            mListener.recieveCallback(body);
                            // Lets get SMS Code
                            String code = body.replaceAll("[^0-9]", "");
                        }
                    }
                }
            }
        }, new IntentFilter("SMS_Recieved"));
    }

    void checkForPermission(Activity activity){
        /*new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Permission grant required")
                .setMessage("You have to grant this permission in order to use the SMS connection")
                .setPositiveButton("Grant", null)
                .setNegativeButton("Decline", null)
                .show();*/
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.SEND_SMS)) {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Permission required")
                        .setMessage("You have to grant this permission in order to use the SMS connection")
                        .setPositiveButton("Grant", (dialog, which) -> requestPermission())
                        .setNegativeButton("Decline", null)
                        .show();
            } else {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Permission grant required")
                        .setMessage("You have to grant this permission in order to use the SMS connection")
                        .setPositiveButton("Grant", (dialog, which) -> requestPermission())
                        .setNegativeButton("Decline", null)
                        .show();
            }
        } else Toast.makeText(context,"perm granted",Toast.LENGTH_SHORT).show();
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, 1);
    }

    void send(String message, String ...phoneNumbers)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                new Intent(DELIVERED), 0);

        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        for (String phoneNumber: phoneNumbers) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        }
    }



    public class SmsReceiver extends BroadcastReceiver {
/*
        private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) return;
            if (intent.getAction().equals(SMS_RECEIVED)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    // get sms objects
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus == null || pdus.length == 0) return;

                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        sb.append(messages[i].getMessageBody());
                    }
                    //String sender = messages[0].getOriginatingAddress();
                    String message = sb.toString();
                    mListener.recieveCallback(message);
                }
            }
        }*/

        static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
        private static final String SMS_SENDER = "123456789";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null &&
                    intent.getAction() != null &&
                    intent.getExtras() != null &&
                    ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
                Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
                if (pduArray != null) {
                    SmsMessage[] messages = new SmsMessage[pduArray.length];
                    for (int i = 0; i < pduArray.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
                    }
                    // SMS Sender, example: 123456789
                    String sms_from = messages[0].getDisplayOriginatingAddress();

                    //Lets check if SMS sender is 123456789
                    if (sms_from.equalsIgnoreCase(SMS_SENDER)) {
                        StringBuilder bodyText = new StringBuilder();

                        // If SMS has several parts, lets combine it :)
                        for (SmsMessage message : messages) {
                            bodyText.append(message.getMessageBody());
                        }
                        //SMS Body
                        String body = bodyText.toString();
                        //Send sms to activity via listener
                        mListener.recieveCallback(body);
                        // Lets get SMS Code
                        String code = body.replaceAll("[^0-9]", "");
                    }
                }
            }
        }
    }

    public interface Listener {
        void recieveCallback(String str);
    }

    void setListener(Listener listener) {
        mListener = listener;
    }
}
