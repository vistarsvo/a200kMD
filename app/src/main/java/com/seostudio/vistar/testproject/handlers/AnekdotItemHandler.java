package com.seostudio.vistar.testproject.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.seostudio.vistar.testproject.helpers.SQLiteHelper;
import com.seostudio.vistar.testproject.models.AnekdotItem;
import com.seostudio.vistar.testproject.utils.DateUtils;


public class AnekdotItemHandler {

    public static void AnekdotFavoriteAdd(AnekdotItem anekdotItem, Context context) {
        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(context);
        int id = anekdotItem.getId();
        int theme_id = anekdotItem.getTheme_id();
        String date_add = DateUtils.getCurrentDate("yyyy-MM-dd");
        String query = "  INSERT INTO favorites_results (anekdot_id, theme_id, date_add) " +
                        " VALUES (" + id + ", " + theme_id + ", \'" + date_add + "\')";
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL(query);
    }

    public static void AnekdotFavoriteRemove(AnekdotItem anekdotItem, Context context) {
        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(context);
        int id = anekdotItem.getFavorite_id();
        String query = "  DELETE FROM favorites_results WHERE id = " + id;
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL(query);
    }
}
