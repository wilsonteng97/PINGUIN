package com.cz2006.fitflop;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cz2006.fitflop.model.GeoJsonFeatureHashMapInfo;
import com.cz2006.fitflop.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

/**
 * UserClient is a Application Instance which stores the logged-in user's information.
 * It helps prevent unnecessary reads/writes to the Database.
 */
public class UserClient extends Application {

    private static final String TAG = "UserClient";

    private static final GeoPoint CENTER_OF_SG = new GeoPoint(1.3521, 103.8198);
    private static User user = null;
    private static GeoPoint geoPoint = null;
    private static GeoJsonFeatureHashMapInfo geoJsonFeatureInfo = null;

    private static ArrayList<String> facilitiesNearYou = null;

    /**
     * Retrieves the current Logged-in User.
     * @return the logged-in User object
     */
    public static User getUser() {
        return user;
    }

    /**
     * Sets the Logged-in User and writes the User to database if User has not been set before.
     * @return
     */
    public static void setUser(User givenUser) {
        if (givenUser!=null){
            user = givenUser;
            saveUserToDB(user);
        }
    }

    /**
     * Saves the updatedUser to the Database.
     * @param updatedUser
     */
    private static void saveUserToDB(User updatedUser) {
        DocumentReference newUserRef = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(FirebaseAuth.getInstance().getUid());

        newUserRef.set(updatedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: \ninserted user location into database.");
                }
            }
        });
    }

    /**
     * Retrieves the GeoPoint Object of the current Logged-in User.
     * @return geoPoint
     */
    public static GeoPoint getGeoPoint() {
//        if (geoPoint == null) return CENTER_OF_SG;
        if (geoPoint == null) return new GeoPoint(1.3461952,103.6793612);
        return geoPoint;
    }

    /**
     * Sets the GeoPoint of the current Logged-in User.
     * @param givenGeoPoint
     */
    public static void setGeoPoint(GeoPoint givenGeoPoint) {
        geoPoint = givenGeoPoint;
    }

    /**
     * Retrieves the information of the locations near the user's current location.
     * @return
     */
    public static GeoJsonFeatureHashMapInfo getGeoJsonFeatureInfo() {
        return geoJsonFeatureInfo;
    }

    /**
     * Sets the information of the locations near the user.
     * Updated when either the user's location is changed.
     * @param givenGeoJsonFeatureInfo
     */
    public static void setGeoJsonFeatureInfo(GeoJsonFeatureHashMapInfo givenGeoJsonFeatureInfo) {
        geoJsonFeatureInfo = givenGeoJsonFeatureInfo;
    }

    /**
     * Retrieves the name of the facilities near the user's current location.
     * @return Names of the facilities near the user
     */
    public static ArrayList<String> getFacilitiesNearYou() {
        return facilitiesNearYou;
    }

    /**
     * Sets name of the facilities near the user's current location.
     * @param givenfacilitiesNearYou
     */
    public static void setFacilitiesNearYou(ArrayList<String> givenfacilitiesNearYou) {
        facilitiesNearYou = givenfacilitiesNearYou;
    }
}