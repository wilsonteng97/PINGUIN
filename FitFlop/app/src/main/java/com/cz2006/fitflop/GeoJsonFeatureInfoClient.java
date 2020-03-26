package com.cz2006.fitflop;

import android.app.Application;

import com.cz2006.fitflop.model.GeoJsonFeatureHashMapInfo;

public class GeoJsonFeatureInfoClient extends Application {

    private static GeoJsonFeatureHashMapInfo geoJsonFeatureInfoClient = null;

    public static GeoJsonFeatureHashMapInfo getGeoJsonFeatureInfoClient() {
        return geoJsonFeatureInfoClient;
    }

    public static void setGeoJsonFeatureInfoClient(GeoJsonFeatureHashMapInfo geoJsonFeatureInfoClient) {
        GeoJsonFeatureInfoClient.geoJsonFeatureInfoClient = geoJsonFeatureInfoClient;
    }
}
