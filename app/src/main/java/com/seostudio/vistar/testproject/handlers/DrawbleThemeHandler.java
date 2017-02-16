package com.seostudio.vistar.testproject.handlers;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

public class DrawbleThemeHandler {
    public static Drawable applyThemeToDrawable(Drawable image, int clr) {
        if (image != null) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(clr,
                    PorterDuff.Mode.SRC_ATOP);
            image.setColorFilter(porterDuffColorFilter);
        }
        return image;
    }
}
