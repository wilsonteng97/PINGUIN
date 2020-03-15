package com.cz2006.fitflop;

import android.app.Application;

import com.cz2006.fitflop.model.User;

public class UserClient extends Application {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}