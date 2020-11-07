package com.example.root.smartmessenger.app;

/**
 * Created by ROOT on 2/3/2018.
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    public static final String TOPIC_12 = "12";
    public static final String TOPIC_13 = "session13";
    public static final String TOPIC_14 = "14";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String NOTIFICATION = "PushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
}
