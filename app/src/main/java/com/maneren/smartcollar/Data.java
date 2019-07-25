package com.maneren.smartcollar;

import androidx.annotation.NonNull;

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
