package com.maneren.product;

import android.util.Log;

import java.text.DecimalFormat;

class DegreesConverter {
    private static final DecimalFormat format = new DecimalFormat("##.00");

    static int LATITUDE = 0;
    static int LONGITUDE = 1;

    static String DecimalToDegMinSec(int type, double degrees) {
        int deg, min;
        double sec;
        String dms;

        deg = (int) Math.floor(degrees);
        min = (int) Math.floor((degrees - deg) * 60);
        Log.d("Converting", (int) (degrees % 1 * 60) + "");
        sec = (degrees - deg) * 3600 - (min * 60);

        if (!(type == 1 || type == 0)) throw new RuntimeException("Incorrect Lat/Long specification");
        if (type == 1) {
            if (degrees > 0) {
                dms = "E";
            } else {
                dms = "W";
            }
        } else {
            if (degrees > 0) {
                dms = "N";
            } else {
                dms = "S";
            }
        }
        return dms + " " + deg + "\u00b0 " + min + "\' " + format.format(sec) + '"';

    }
}
