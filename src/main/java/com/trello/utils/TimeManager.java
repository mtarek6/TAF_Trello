package com.trello.utils;

public class TimeManager {
    public static String getTimestamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
    }

    public static String getSimpleTimeStamp() {
        return Long.toString(System.currentTimeMillis());
    }
}
