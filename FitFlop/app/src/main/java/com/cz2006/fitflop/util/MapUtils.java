package com.cz2006.fitflop.util;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.geojson.GeoJsonFeature;

import static com.cz2006.fitflop.logic.MapDistanceLogic.getDistance;

/**
 * The MapUtils Class allows for methods commonly used when interacting with location objects.
 */
public class MapUtils {

    /**
     * The method handles the conversion of a GeoJsonFeature object to a LatLng Object.
     * @param feature
     * @return latlng
     */
    public static LatLng getLatLngFromGeoJsonFeature(GeoJsonFeature feature) {
        String[] coord_str = feature.getGeometry().getGeometryObject().toString().split(",");
        String lat = coord_str[0].substring(10);
        String lng = coord_str[1];
        lng = lng.substring(0, lng.length() - 1);
        LatLng latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        return latlng;
    }

    /**
     * The method handles the conversion of a String to a LatLng Object.
     * An example of the String is (101.9080, 20.9072).
     * @param string
     * @return latlng
     */
    public static LatLng getLatLngFromGeoJsonFeatureString(String string) {
        String[] coord_str = string.split(",");
        String lat = coord_str[0].substring(10);
        String lng = coord_str[1];
        lng = lng.substring(0, lng.length() - 1);
        LatLng latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        return latlng;
    }

    /**
     * This method evaluates whether a specified location is within a stipulated distance
     * from another location. If it is, return true.
     * @param metres
     * @param feature_location
     * @param current_user_location
     * @return isNear
     */
    public static boolean is_feature_near(double metres, LatLng feature_location,
                                          LatLng current_user_location) {
        boolean isNear = false;
        double dist;

        dist = getDistance(metres, feature_location, current_user_location);
        if (dist < metres) {
            isNear = true;
        }
        return isNear;
    }
}
