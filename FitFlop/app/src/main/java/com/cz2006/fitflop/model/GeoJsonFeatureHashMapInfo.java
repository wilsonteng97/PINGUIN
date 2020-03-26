package com.cz2006.fitflop.model;

import android.util.Log;

import androidx.core.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.cz2006.fitflop.logic.MapDistanceLogic.getDistance;
import static com.cz2006.fitflop.util.MapUtils.getLatLngFromGeoJsonFeatureString;

/**
 * Stores features generated from the KML file in a HashMap to allow for fast processing of Map data.
 * Getting and Setting of HashMap is O(1).
 */
public class GeoJsonFeatureHashMapInfo {

    private static final String TAG = "GeoJsonFeatureHashMapInfo";
    private LinkedHashMap<String, HashMap<String, String>> featureInfoHashMap;

    /**
     * Constructor
     */
    public GeoJsonFeatureHashMapInfo() {
        this.featureInfoHashMap = new LinkedHashMap<String, HashMap<String, String>>();
    }

    /**
     * Returns firstElement of featureInfoHashMap. Time Complexity is O(1).
     * @returns firstElement of featureInfoHashMap,
     * which is the nearest Facility from User's current location.
     */
    public HashMap<String, HashMap<String, String>> getNearestFacility() {
        if (this.featureInfoHashMap==null) {
            return null;
        }
        return (HashMap<String, HashMap<String, String>>) this.featureInfoHashMap.entrySet().iterator().next();
    }


    /**
     * Returns lastElement of featureInfoHashMap. Time Complexity is O(n).
     * @returns lastElement of featureInfoHashMap,
     * which is the furthest Facility from User's current location.
     */
    public HashMap<String, HashMap<String, String>> getFurthestFacility() {
        Map.Entry<String, HashMap<String, String>> lastElement = null;
        while (this.featureInfoHashMap.entrySet().iterator().hasNext()) {
            lastElement = this.featureInfoHashMap.entrySet().iterator().next();
        }
        return (HashMap<String, HashMap<String, String>>) lastElement;
    }

    /**
     * Sorts featureInfoHashMap by distance, from the nearest to the furthest.
     * Time Complexity is O(n) + O(n.log n) = O(n).
     * @param currentUserLocation
     */
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

    /**
     * Append HashMap<String, HashMap<String, String>> item to featureInfoHashMap
     * @param masterKey
     * @param featureInfo
     */
    public void add(String masterKey, HashMap<String, String> featureInfo) {
        this.featureInfoHashMap.put(masterKey, featureInfo);
    }

    /**
     * Remove HashMap<String, HashMap<String, String>> item to featureInfoHashMap
     * @param masterKey
     * @param featureInfo
     */
    public void remove(String masterKey, HashMap featureInfo) {
        this.featureInfoHashMap.remove(masterKey, featureInfo);
    }

    /**
     * Clears featureInfoHashMap
     */
    public void removeAll() {
        this.featureInfoHashMap.clear();
    }

    /**
     * Getter method for featureInfoHashMap
     * @return featureInfoHashMap
     */
    public HashMap<String, HashMap<String, String>> getFeatureInfoHashMap() {
        return featureInfoHashMap;
    }

    /**
     * Setter method for featureInfoHashMap
     * @return featureInfoHashMap
     */
    public void setFeatureInfoHashMap(LinkedHashMap<String, HashMap<String, String>> featureInfoHashMap) {
        this.featureInfoHashMap = featureInfoHashMap;

    }

    /**
     *
     * @returns keySet of featureInfoHashMap, which is the unique name of all facilities.
     */
    public Set<String> getMasterKeys() {
        return this.featureInfoHashMap.keySet();
    }

    /**
     * Method Overloading
     * To get InfoHashMap for a given masterKey.
     * @param masterKey
     * @return
     */
    public HashMap<String, String> getInfo(String masterKey) {
        try {
            return this.featureInfoHashMap.get(masterKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method Overloading
     * To get Property (String) for a given masterKey & key.
     * @param masterKey
     * @param key
     * @return
     */
    public String getInfo(String masterKey, String key) {
        try {
            return this.featureInfoHashMap.get(masterKey).get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
