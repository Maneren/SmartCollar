package com.maneren.smartcollar;

import android.os.Handler;

import java.text.DecimalFormat;

class Timer {

    private long startTime = 0;
    private Listener mListener;

    private final Handler timerHandler = new Handler();
    private final Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int hours = seconds / 3600;
            seconds %= 3600;
            int minutes = seconds / 60;
            seconds %= 60;

            DecimalFormat df = new DecimalFormat("00");

            String time = ""
                    .concat(df.format(hours))
                    .concat(":")
                    .concat(df.format(minutes))
                    .concat(":")
                    .concat(df.format(seconds));

            mListener.recieveCallback(time);

            timerHandler.postDelayed(this, 500);
        }
    };

    Timer() {

    }



    void start(){
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    void pause(){
        timerHandler.removeCallbacks(timerRunnable);
    }

    void goOn(){
        timerHandler.postDelayed(timerRunnable, 0);
    }

    void stop() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    public interface Listener {
        void recieveCallback(String str);
    }

    void setListener(Timer.Listener listener) {
        mListener = listener;
    }
}
