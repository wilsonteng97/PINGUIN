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

public class UserClient extends Application {

    private static final String TAG = "UserClient";

    private static final GeoPoint CENTER_OF_SG = new GeoPoint(1.3521, 103.8198);
    private static User user = null;
    private static GeoPoint geoPoint = null;
    private static GeoJsonFeatureHashMapInfo geoJsonFeatureInfo = null;

    private static ArrayList<String> facilitiesNearYou = null;

    public static User getUser() {
        return user;
    }

    public static void setUser(User givenUser) {
        if (givenUser!=null) {
            user = givenUser;
            saveUserToDB(user);
        }
    }

    public static GeoPoint getGeoPoint() {
//        if (geoPoint == null) return CENTER_OF_SG;
        if (geoPoint == null) return new GeoPoint(1.3461952,103.6793612);
        return geoPoint;
    }

    public static void setGeoPoint(GeoPoint givenGeoPoint) {
        geoPoint = givenGeoPoint;
    }

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

    public static GeoJsonFeatureHashMapInfo getGeoJsonFeatureInfo() {
        return geoJsonFeatureInfo;
    }

    public static void setGeoJsonFeatureInfo(GeoJsonFeatureHashMapInfo givenGeoJsonFeatureInfo) {
        geoJsonFeatureInfo = givenGeoJsonFeatureInfo;
    }

    public static ArrayList<String> getFacilitiesNearYou() {
        return facilitiesNearYou;
    }

    public static void setFacilitiesNearYou(ArrayList<String> givenfacilitiesNearYou) {
        facilitiesNearYou = givenfacilitiesNearYou;
    }
}