package com.maneren.product2;

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

    static String buildLocationUri (Double latitude, Double longitude){
        return "geo:" + latitude + "," + longitude
                + "?q=" + latitude + "," + longitude;
    }

}
