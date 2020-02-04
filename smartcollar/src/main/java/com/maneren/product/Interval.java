package com.maneren.product;

import android.os.Handler;

class Interval{
    private final int times;
    private int i = 0;
    private boolean started = false;
    private final Handler handler = new Handler();
    private Runnable callback;
    private long interval;

    void stop() {
        started = false;
        handler.removeCallbacks(this::call);
    }

    private void resume() {
        handler.postDelayed(this::call, interval);
    }

    private void call(){
        callback.run();
        if(++i < times)
        resume();
    }

    void run() {
        if(!started) {
            started = true;
            resume();
        }
    }


    /**
     * @param callback method to be called
     * @param interval time between calls
     */
    Interval(Runnable callback, long interval) {
        this(callback, interval, 1);
    }

    /**
     * @param callback method to be called
     * @param interval time between calls
     * @param times how many times should the method called
     */
    Interval(Runnable callback, long interval, int times) {
        this.callback = callback;
        this.interval = interval;
        this.times = times;
    }
}
