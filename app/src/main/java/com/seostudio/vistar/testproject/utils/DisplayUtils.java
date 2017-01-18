package com.seostudio.vistar.testproject.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.seostudio.vistar.testproject.models.DisplaySettings;

public class DisplayUtils {
    private static DisplayUtils sInstance;

    public static synchronized DisplayUtils getInstance() {
        if (sInstance == null) {
            sInstance = new DisplayUtils();
        }
        return sInstance;
    }

    public DisplaySettings getDisplayParams(Context context){
        DisplayMetrics  displayMetrics  = new DisplayMetrics();
        DisplaySettings displaySettings = new DisplaySettings();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int screenOrientation = context.getResources().getConfiguration().orientation;

        display.getMetrics(displayMetrics);

        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else {
            display.getSize(size); // correct for devices with hardware navigation buttons
        }
        int density = displayMetrics.densityDpi;

        displaySettings.setDensity(density);
        displaySettings.setWidth(size.x);
        displaySettings.setHeight(size.y);
        displaySettings.setOrientation(screenOrientation);

        return displaySettings;
    }
}


