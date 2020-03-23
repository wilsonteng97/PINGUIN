package com.cz2006.fitflop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Notification implements Parcelable {

    private String title;
    private String message;
    private @ServerTimestamp Date timestamp;
    private String user;
    private String class_link;
    private Boolean hasRead;

    public Notification(String user, String title, String notification, Date timestamp, String class_link, Boolean hasRead) {
        this.user = user;
        this.title = title;
        this.message = notification;
        this.timestamp = timestamp;
        this.class_link = class_link;
        this.hasRead = hasRead;
    }

    public Notification() {

    }

    protected Notification(Parcel in) {
        title = in.readString();
        message = in.readString();
        user = in.readString();
        class_link = in.readString();
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    public Boolean getHasRead() { return hasRead; }

    public void setHasRead(Boolean hasRead) { this.hasRead = hasRead; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getClass_link() { return class_link; }

    public void setClass_link(String class_link) { this.class_link = class_link; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(class_link);
    }
}
