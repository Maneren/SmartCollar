package com.maneren.product2;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

class Data {
    private String id;
    private String target;
    private String time;
    private Calendar datetime;
    private String type;
    private Double latitude;
    private Double longitude;
    private Float velocity;
    private int altitude;
    private int accuracy;

    MarkerOptions getMarkerOptions() {
        return new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Dog location");
    }

    LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    @Override
    @NonNull
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", target:'" + target + '\'' +
                ", time:'" + time + '\'' +
                ", datetime:" + datetime +
                ", type:'" + type + '\'' +
                ", latitude:" + latitude +
                ", longitude:" + longitude +
                ", velocity:" + velocity +
                ", heigth:" + altitude +
                ", accuracy:" + accuracy +
                '}';
    }
}
