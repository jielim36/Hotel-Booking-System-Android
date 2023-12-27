package com.hotel_booking_systems_android.service;
import android.content.Context;
import android.content.SharedPreferences;

public class AccountSharedPreferences {

    private static final String PREFERENCES_NAME = "account";
    private static AccountSharedPreferences instance;
    private final SharedPreferences sharedPreferences;

    private final String KEY_IS_LOGIN = "isLogin";
    private final String KEY_IS_BOOKING = "isBooking";
    private final String KEY_USERNAME = "username";
    private final String KEY_USERID = "userId";

    private AccountSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AccountSharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new AccountSharedPreferences(context);
        }
        return instance;
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void saveInteger(String key, Integer value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Add methods for other data types as needed

    // Clear all data in SharedPreferences
    public void clearPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setLogin(boolean isLogin) {
        saveBoolean(KEY_IS_LOGIN, isLogin);
    }

    public boolean isBooking() {
        return sharedPreferences.getBoolean(KEY_IS_BOOKING, false);
    }

    public void setBooking(boolean isBooking) {
        saveBoolean(KEY_IS_BOOKING, isBooking);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public void setUsername(String username) {
        saveString(KEY_USERNAME, username);
    }

    public Integer getUserId() {
        return sharedPreferences.getInt(KEY_USERID, -1);
    }

    public void setUserId(Integer userId) {
        saveInteger(KEY_USERID, userId);
    }

}
