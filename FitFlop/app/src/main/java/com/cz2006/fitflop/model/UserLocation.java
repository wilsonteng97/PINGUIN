package com.cz2006.fitflop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Class to get the user's current location
 */
public class UserLocation implements Parcelable {

    private User user;
    private GeoPoint geo_point;
    private @ServerTimestamp
    Date timestamp;

    /**
     * Constructor
     * @param user
     * @param geo_point
     * @param timestamp
     */
    public UserLocation(User user, GeoPoint geo_point, Date timestamp) {
        this.user = user;
        this.geo_point = geo_point;
        this.timestamp = timestamp;
    }

    /**
     * Constructor
     */
    public UserLocation() {

    }


    protected UserLocation(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<UserLocation> CREATOR = new Creator<UserLocation>() {
        @Override
        public UserLocation createFromParcel(Parcel in) {
            return new UserLocation(in);
        }

        @Override
        public UserLocation[] newArray(int size) {
            return new UserLocation[size];
        }
    };

    /**
     * Getter method for current user
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter method for current user
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter method for current location
     * @return
     */
    public GeoPoint getGeo_point() {
        return geo_point;
    }

    /**
     * Setter method for current location
     * @param geo_point
     */
    public void setGeo_point(GeoPoint geo_point) {
        this.geo_point = geo_point;
    }

    /**
     * Getter method for time stamp
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Setter method for timestamp
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "user=" + user +
                ", geo_point=" + geo_point +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
    }
}
