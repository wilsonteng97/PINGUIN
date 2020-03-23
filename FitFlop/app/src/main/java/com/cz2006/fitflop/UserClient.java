package com.cz2006.fitflop;

import android.app.Application;

import com.cz2006.fitflop.model.User;
import com.google.firebase.firestore.GeoPoint;

public class UserClient extends Application {

    private static final GeoPoint CENTER_OF_SG = new GeoPoint(1.3521, 103.8198);
    private static User user = null;
    private static GeoPoint geoPoint = null;

    public static User getUser() {
        return user;
    }

    public static void setUser(User givenUser) {
        user = givenUser;
    }

    public static GeoPoint getGeoPoint() {
        if (geoPoint == null) return CENTER_OF_SG;
        return geoPoint;
    }

    public static void setGeoPoint(GeoPoint givenGeoPoint) {
        geoPoint = givenGeoPoint;
    }

}