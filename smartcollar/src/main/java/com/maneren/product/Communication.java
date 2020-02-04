package com.maneren.product;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Contains Comunication codes as Strings and methods used to communicate with outside of app
 */
class Communication {

    static class Main{

    }
    static String[] IDs = getIDs();
    static final String END = "0";

    static class Commands {
        static final String START_ALARM = "A";
        static final String SEND_LOCATION = "L";
    }

    static final String TARGET_COLLAR = "c";
    static final String TARGET_PHONE = "r";
    static final String CONFIG_WALK_INTERVAL = "cwi";
    static final String CONFIG_ALARM_INTERVAL = "cai";
    static final String LOCATION_DATA = "p";

    static private String[] getIDs(){
        return new String[]{"8900"};
    }

    static void shareText(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }

    static void shareGeo(Context context, LatLng latLng){
        Uri gmmIntentUri = Uri.parse("geo:" + latLng.latitude + latLng.longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }
    }

    static  void shareURL(Context context, String url){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }
}
