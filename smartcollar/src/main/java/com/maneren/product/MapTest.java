package com.maneren.product;

class MapTest {
    private final String[] testCases = {
            "{ id: '8900', target: 'm', timestamp: 1549207478, type: 'std', latitude: 49.7404333, longitude: 13.3881888, velocity: 2, altitude: 320, accuracy: 5 }",
            "{ id: '8900', target: 'm', timestamp: 1549209478, type: 'std', latitude: 49.7404555, longitude: 13.3881666, velocity: 1, altitude: 321, accuracy: 6 }",
            "{ id: '8900', target: 'm', timestamp: 1549211478, type: 'std', latitude: 49.7404777, longitude: 13.3881444, velocity: 2, altitude: 322, accuracy: 2 }",
            "{ id: '8900', target: 'm', timestamp: 1549213478, type: 'std', latitude: 49.7404999, longitude: 13.3881222, velocity: 3, altitude: 310, accuracy: 1 }",
            "{ id: '8900', target: 'm', timestamp: 1549215478, type: 'std', latitude: 49.7405111, longitude: 13.3881000, velocity: 2, altitude: 325, accuracy: 4 }",
            "{ id: '8900', target: 'm', timestamp: 1549217478, type: 'std', latitude: 49.7405222, longitude: 13.3880888, velocity: 0, altitude: 320, accuracy: 5 }",
    };

    private int i = 0;
    private MapFragment map;

    private void sendCase (){
        map.testCallback(testCases[i++]);
    }

    MapTest(MapFragment mapFrag) {
        map = mapFrag;
        Interval mInterval = new Interval(this::sendCase, 3000, testCases.length);
        mInterval.run();
    }
}
