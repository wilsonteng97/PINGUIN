package com.cz2006.fitflop.logic;

import com.google.android.gms.maps.model.LatLng;

/**
 * Calculates the Distance between 2 locations.
 */
public class MapDistanceLogic {
    private static final double EARTH_RADIUS = 6371000; //meters

    /**
     * Gets the accurate distance between 2 locations.
     * @param feature_location
     * @param current_user_location
     * @return distance
     */
    public static double getDistance(LatLng feature_location, LatLng current_user_location) {
        double dist = 0;

        dist = Math.abs(getAccurateDist(feature_location, current_user_location));
        return dist;
    }

    /**
     * Gets the distance between 2 locations.
     * If the distance between the 2 locations is definitely less than 100m,
     * use a faster, but more inaccurate method of calculating the distance.
     * @param metres
     * @param feature_location
     * @param current_user_location
     * @return
     */
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
        double b = (lng1 - lng2) * distPerLng(lng1);
        return Math.sqrt(a * a + b * b);
    }

    /**
     * Approximates the distance per Lat based upon the first Lat point.
     * @param lat
     * @return
     */
    private static double distPerLng(double lat) {
        return 0.0003121092 * Math.pow(lat, 4)
                + 0.0101182384 * Math.pow(lat, 3)
                - 17.2385140059 * lat * lat
                + 5.5485277537 * lat + 111301.967182595;
    }

    /**
     * Approximates the distance per Lng based upon the first Lat point.
     * @param lat
     * @return
     */
    private static double distPerLat(double lat) {
        return -0.000000487305676 * Math.pow(lat, 4)
                - 0.0033668574 * Math.pow(lat, 3)
                + 0.4601181791 * lat * lat
                - 1.4558127346 * lat + 110579.25662316;
    }

    /**
     * Calculates the Distance between 2 points after conversion to radians.
     * Uses the Earth's radius for calculation.
     * @param latLng1
     * @param latLng2
     * @return
     */
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
