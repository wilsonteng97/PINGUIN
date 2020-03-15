package com.cz2006.fitflop.util;

import android.content.Context;
import android.widget.Toast;

public class InAppNotifications {

    public static Toast toastNotification(Context context, CharSequence text) {
        int duration = Toast.LENGTH_SHORT;
        return Toast.makeText(context, text, duration);
    }
}
