package com.seostudio.vistar.testproject.models;

import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class DisplaySettings {
    private int width;
    private int height;
    private int density;
    private int orientation;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getStringDensity() {
        String densityString = "DEFAULT";
        switch (density) {
            case DisplayMetrics.DENSITY_XXXHIGH: densityString = "XXXHDPI"; break;
            case DisplayMetrics.DENSITY_XXHIGH: densityString = "XXHDPI"; break;
            case DisplayMetrics.DENSITY_XHIGH: densityString = "XHDPI"; break;
            case DisplayMetrics.DENSITY_HIGH: densityString = "HDPI"; break;
            case DisplayMetrics.DENSITY_MEDIUM: densityString = "MDPI"; break;
            case DisplayMetrics.DENSITY_LOW: densityString = "LDPI"; break;
            case DisplayMetrics.DENSITY_TV: densityString = "TV"; break;
        }
        return densityString;
    }

    public String getStringOrientation() {
        return orientation == Configuration.ORIENTATION_PORTRAIT ? "PORTRAIT" : "LANDSCAPE";
    }
}
