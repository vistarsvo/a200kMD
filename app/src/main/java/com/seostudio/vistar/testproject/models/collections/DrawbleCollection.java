package com.seostudio.vistar.testproject.models.collections;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.handlers.DrawbleThemeHandler;

import java.util.HashMap;

public class DrawbleCollection {
    private HashMap<String, Drawable> list = new HashMap<>();
    private static DrawbleCollection sInstance;
    private Context mContext;
    private int defaultColor = Color.BLACK;

    public static synchronized DrawbleCollection getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DrawbleCollection();
            sInstance.mContext = context;
        }
        return sInstance;
    }

    public Drawable getDrawble(String name) {
        return list.get(name);
    }

    public void initDrawbles() {
        list.put("ic_tab_menu", DrawbleThemeHandler.applyThemeToDrawable(loadDrawble(R.drawable.ic_tab_menu), defaultColor));
        list.put("ic_tab_favorites", DrawbleThemeHandler.applyThemeToDrawable(loadDrawble(R.drawable.ic_tab_favorites), defaultColor));
        list.put("ic_tab_search", DrawbleThemeHandler.applyThemeToDrawable(loadDrawble(R.drawable.ic_tab_search), defaultColor));
        list.put("ic_tab_options", DrawbleThemeHandler.applyThemeToDrawable(loadDrawble(R.drawable.ic_tab_options), defaultColor));
    }

    public void colorizedCollection(int color) {
        for (String key : list.keySet()) {
            list.put(key, DrawbleThemeHandler.applyThemeToDrawable(list.get(key), color));
        }
    }

    private Drawable loadDrawble(int resourceDrawble) {
        return ResourcesCompat.getDrawable(mContext.getResources(), resourceDrawble, null);
    }
}
