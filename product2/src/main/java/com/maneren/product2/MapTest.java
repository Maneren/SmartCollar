package com.maneren.product2;

import android.os.Handler;
import android.util.Log;

class MapTest {
    private String[] testCases = {
            "{ id: '8900', target: 'm', timestamp: 1, type: 'std', latitude: 49.7404333, longitude: 13.3881888, velocity: 2, altitude: 320, accuracy: 5 }",
            "{ id: '8900', target: 'm', timestamp: 1, type: 'std', latitude: 49.7404555, longitude: 13.3881666, velocity: 1, altitude: 321, accuracy: 6 }",
            "{ id: '8900', target: 'm', timestamp: 1, type: 'std', latitude: 49.7404777, longitude: 13.3881444, velocity: 2, altitude: 322, accuracy: 2 }",
            "{ id: '8900', target: 'm', timestamp: 1, type: 'std', latitude: 49.7404999, longitude: 13.3881222, velocity: 3, altitude: 310, accuracy: 1 }",
            "{ id: '8900', target: 'm', timestamp: 1, type: 'std', latitude: 49.7405111, longitude: 13.3881000, velocity: 2, altitude: 325, accuracy: 4 }",
            "{ id: '8900', target: 'm', timestamp: 1, type: 'std', latitude: 49.7405222, longitude: 13.3880888, velocity: 0, altitude: 320, accuracy: 5 }",
    };

    private int i = 0;
    private MapFragment map;
    private String TAG = "MapTest";

    private boolean started = false;
    private Handler handler = new Handler();

    private Runnable runnable = () -> {
        if(i == testCases.length){
            stop();
            Log.d(TAG, "end");
            return;
        }
        map.testCallback(testCases[i++]);
        if(started) start();
    };

    private void stop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    private void start() {
        started = true;
        handler.postDelayed(runnable, 3000);
    }

    MapTest(MapFragment mapFrag){
        map = mapFrag;
    }

    void run() {
        Log.d(TAG, "start");
        if(!started) start();
    }
}
