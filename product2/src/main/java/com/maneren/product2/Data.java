package com.maneren.product2;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

class Data {
    private String id;
    private String target;
    private long timestamp;
    private String type;
    private Double latitude;
    private Double longitude;
    private Float velocity;
    private int altitude;
    private int accuracy;

    public String getId() {
        return id;
    }

    public String getTarget() {
        return target;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Float getVelocity() {
        return velocity;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getAccuracy() {
        return accuracy;
    }

    MarkerOptions getMarkerOptions() {
        return new MarkerOptions()
                .position(getLatLng())
                .title("Dog location");
    }

    LatLng getLatLng() {
        return new LatLng(getLatitude(), getLongitude());
    }


    @NonNull
    public String toJSON() {
        return "{" +
                "id:'" + id + '\'' +
                ", target:'" + target + '\'' +
                ", timestamp:" + timestamp +
                ", type:'" + type + '\'' +
                ", latitude:" + latitude +
                ", longitude:" + longitude +
                ", velocity:" + velocity +
                ", altitude:" + altitude +
                ", accuracy:" + accuracy +
                '}';
    }
}
