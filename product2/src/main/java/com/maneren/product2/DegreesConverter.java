package com.maneren.product2;

import android.util.Log;

import java.text.DecimalFormat;

class DegreesConverter {
    private static DecimalFormat format = new DecimalFormat("##.000");

    static String DecimalToDegMinSec(int type, double degrees) {
        //DMS result = new DMS();
        int deg, min;
        double sec;
        String dms;

        deg = (int) degrees;
        min = (int) (degrees % 1) * 60;
        Log.d("Converting", (int) (degrees % 1 * 60) + "");
        sec = (degrees - deg - min);

        if (type != 1 && type != 0) throw new RuntimeException("Incorrect Lat/Long specification");
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
        return dms + " " + degrees + "\u00b0 " + min + "\' " + format.format(sec) + '"';
        /*result.setValues(dms, deg, min, sec);
        return result;*/
    }
}

/*class DMS
{
    private int degrees, minutes;
    private double seconds;
    private String dmsString;


    void setValues(String dms, int deg, int min, double sec ){
        dmsString = dms;
        degrees = deg;
        minutes = min;
        seconds = sec;
    }

    String getDmsString() {
        ;
    }
}*/
