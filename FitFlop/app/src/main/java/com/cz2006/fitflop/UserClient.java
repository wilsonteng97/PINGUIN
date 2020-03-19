package com.cz2006.fitflop;

import android.app.Application;

import com.cz2006.fitflop.model.User;

public class UserClient extends Application {

    private static User user = null;

    public static User getUser() {
        return user;
    }

    public static void setUser(User givenUser) {
        user = givenUser;
    }

}