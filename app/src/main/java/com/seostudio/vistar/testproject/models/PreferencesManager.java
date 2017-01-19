package com.seostudio.vistar.testproject.models;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private SharedPreferences mSharedPreferences;
    private String packangeName = "com.seostudio.vistar.testproject";
    private int dbVer = 1;
    private String dbName = "main.db";
    private String appDataDir;

    public PreferencesManager(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(this.packangeName, Context.MODE_PRIVATE);
        this.appDataDir = context.getApplicationInfo().dataDir + "/";
    }

    public String getAppDataDir() {
        return this.appDataDir;
    }

    public boolean getIsFirstRun() {
        return this.mSharedPreferences.getBoolean("isFirstRun", true);
    }

    public boolean setIsFirstRun(boolean val) {
        return this.mSharedPreferences.edit().putBoolean("isFirstRun", val).commit();
    }

    public boolean getIsIntroRun() {
        return this.mSharedPreferences.getBoolean("isIntroRun", true);
    }

    public boolean setIsIntoRun(boolean val) {
        return this.mSharedPreferences.edit().putBoolean("isIntroRun", val).commit();
    }

    public float getSingleReadFontSize() {
        return this.mSharedPreferences.getFloat("singleReadFontSize", 13);
    }

    public boolean setSingleReadFontSize(float val) {
        return this.mSharedPreferences.edit().putFloat("singleReadFontSize", val).commit();
    }

    public String getString(String key) {
        return this.mSharedPreferences.getString("key", "");
    }

    public boolean setString(String key, String value) {
        return this.mSharedPreferences.edit().putString(key, value).commit();
    }

    public int getInt(String key) {
        return this.mSharedPreferences.getInt("key", -1);
    }

    public boolean setInt(String key, int value) {
        return this.mSharedPreferences.edit().putInt(key, value).commit();
    }


    public int getDbVer() {
        return this.dbVer;
    }

    public boolean setDbver(int val) {
        return this.mSharedPreferences.edit().putInt("dbVer", val).commit();
    }

    public boolean isDbActual() {
        return this.dbVer == this.mSharedPreferences.getInt("dbVer", 0);
    }

    public String getDbName() {
        return this.dbName;
    }
}
