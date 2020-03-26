package com.cz2006.fitflop.util;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.geojson.GeoJsonFeature;

import static com.cz2006.fitflop.logic.MapDistanceLogic.getDistance;

public class MapUtils {

    public static LatLng getLatLngFromGeoJsonFeature(GeoJsonFeature feature) {
        String[] coord_str = feature.getGeometry().getGeometryObject().toString().split(",");
        String lat = coord_str[0].substring(10);
        String lng = coord_str[1];
        lng = lng.substring(0, lng.length() - 1);
        LatLng latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        return latlng;
    }

    public static LatLng getLatLngFromGeoJsonFeatureString(String string) {
        String[] coord_str = string.split(",");
        String lat = coord_str[0].substring(10);
        String lng = coord_str[1];
        lng = lng.substring(0, lng.length() - 1);
        LatLng latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        return latlng;
    }

    public static boolean is_feature_near(double metres, LatLng feature_location, LatLng current_user_location) {
        boolean isNear = false;
        double dist;

        dist = getDistance(metres, feature_location, current_user_location);
        if (dist < metres) {
            isNear = true;
        }
        return isNear;
    }
}
