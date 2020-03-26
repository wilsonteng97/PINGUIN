package com.cz2006.fitflop.model;

import android.util.Log;

import androidx.core.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import static com.cz2006.fitflop.logic.MapDistanceLogic.getDistance;
import static com.cz2006.fitflop.util.MapUtils.getLatLngFromGeoJsonFeatureString;

public class GeoJsonFeatureHashMapInfo {

    private static final String TAG = "GeoJsonFeatureHashMapInfo";
    private LinkedHashMap<String, HashMap<String, String>> featureInfoHashMap;

    /**
     * Constructor
     */
    public GeoJsonFeatureHashMapInfo() {
        this.featureInfoHashMap = new LinkedHashMap<String, HashMap<String, String>>();
    }

    public void sortByDistance(LatLng currentUserLocation) {

        LinkedHashMap<String, HashMap<String, String>> newHashMap = new LinkedHashMap<String, HashMap<String, String>>();
        ArrayList<Pair<String, Double>> sortedArrayList = new ArrayList<Pair<String, Double>>();

        for (String masterKey : this.featureInfoHashMap.keySet()) {
            String latLngStr = this.featureInfoHashMap.get(masterKey).get("LATLNG");
            LatLng latLng = getLatLngFromGeoJsonFeatureString(latLngStr);
            double latLngDist = getDistance(latLng, currentUserLocation);
            sortedArrayList.add(new Pair<String, Double>(masterKey, latLngDist));
        }

        Collections.sort(sortedArrayList, new Comparator<Pair<String, Double>>() {
            @Override
            public int compare(final Pair<String, Double> o1, final Pair<String, Double> o2) {
                return (o1.second).compareTo(o2.second);
            }
        });

        String masterKey;
        for (Pair<String, Double> pair : sortedArrayList) {
            masterKey = pair.first;
            newHashMap.put(masterKey, this.featureInfoHashMap.get(masterKey));
        }
        Log.e(TAG, String.valueOf(newHashMap));

        this.featureInfoHashMap = newHashMap;
    }

    public void add(String masterKey, HashMap<String, String> featureInfo) {
        this.featureInfoHashMap.put(masterKey, featureInfo);
    }

    public void remove(String masterKey, HashMap featureInfo) {
        this.featureInfoHashMap.remove(masterKey, featureInfo);
    }

    public void removeAll() {
        this.featureInfoHashMap.clear();
    }

    public HashMap<String, HashMap<String, String>> getFeatureInfoHashMap() {
        return featureInfoHashMap;
    }

    public void setFeatureInfoHashMap(LinkedHashMap<String, HashMap<String, String>> featureInfoHashMap) {
        this.featureInfoHashMap = featureInfoHashMap;

    }

    public Set<String> getMasterKeys() {
        return this.featureInfoHashMap.keySet();
    }

    public HashMap<String, String> getInfo(String masterKey) {
        try {
            return this.featureInfoHashMap.get(masterKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInfo(String masterKey, String key) {
        try {
            return this.featureInfoHashMap.get(masterKey).get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
