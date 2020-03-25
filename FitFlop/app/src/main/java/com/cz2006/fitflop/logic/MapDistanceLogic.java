package com.cz2006.fitflop.logic;

import com.google.android.gms.maps.model.LatLng;

public class MapDistanceLogic {
    private static final double EARTH_RADIUS = 6371000; //meters

    public static double getDistance(double metres, LatLng feature_location, LatLng current_user_location) {
        double dist = 0;
        if (metres <= 100) {
            dist = Math.abs(getInaccurateDist(feature_location, current_user_location));
        } else {
            dist = Math.abs(getAccurateDist(feature_location, current_user_location));
        }
        return dist;
    }

    /**
     * Inaccurate for distances more than 100m but very fast. (Assumes the world is Flat)
     * Works by approximating the distance of a single latitude/longitude at the given latitude and
     *
     * @param latLng1
     * @param latLng2
     * @returns Pythagorean distance in metres
     */
    private static double getInaccurateDist(LatLng latLng1, LatLng latLng2) {
        double lat1 = latLng1.latitude;
        double lng1 = latLng1.longitude;
        double lat2 = latLng2.latitude;
        double lng2 = latLng2.longitude;

        double a = (lat1 - lat2) * distPerLat(lat1);
        double b = (lng1 - lng2) * distPerLng(lat1);
        return Math.sqrt(a * a + b * b);
    }

    private static double distPerLng(double lat) {
        return 0.0003121092 * Math.pow(lat, 4)
                + 0.0101182384 * Math.pow(lat, 3)
                - 17.2385140059 * lat * lat
                + 5.5485277537 * lat + 111301.967182595;
    }

    private static double distPerLat(double lat) {
        return -0.000000487305676 * Math.pow(lat, 4)
                - 0.0033668574 * Math.pow(lat, 3)
                + 0.4601181791 * lat * lat
                - 1.4558127346 * lat + 110579.25662316;
    }


    private static double getAccurateDist(LatLng latLng1, LatLng latLng2) {
        double lat1 = latLng1.latitude;
        double lng1 = latLng1.longitude;
        double lat2 = latLng2.latitude;
        double lng2 = latLng2.longitude;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

//        double height = h1 - h2;
//        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }
}
