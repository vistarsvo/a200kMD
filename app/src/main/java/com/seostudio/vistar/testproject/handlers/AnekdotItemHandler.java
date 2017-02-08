package com.seostudio.vistar.testproject.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.seostudio.vistar.testproject.helpers.SQLiteHelper;
import com.seostudio.vistar.testproject.models.AnekdotItem;
import com.seostudio.vistar.testproject.utils.DateUtils;


public class AnekdotItemHandler {
    public static void anekdotFavoriteAdd(AnekdotItem anekdotItem, Context context) {
        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(context);
        int id = anekdotItem.getId();
        int theme_id = anekdotItem.getTheme_id();
        String date_add = DateUtils.getCurrentDate("yyyy-MM-dd");
        String query = "  INSERT INTO favorites_results (anekdot_id, theme_id, date_add) " +
                        " VALUES (" + id + ", " + theme_id + ", \'" + date_add + "\')";
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL(query);
    }

    public static void anekdotFavoriteRemove(AnekdotItem anekdotItem, Context context) {
        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(context);
        int id = anekdotItem.getFavorite_id();
        String query = "  DELETE FROM favorites_results WHERE id = " + id;
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL(query);
    }

    public static String anekdotGetQuery(int last, int vector, int theme_id) {
        String query;
        String field = "an.id, an.active, an.anekdot_text";
        String theme_id_str = Integer.toString(theme_id);
        if (last < 1) {
            // First Time
            query = " SELECT " + field + " " +
                    //"  ( SELECT COUNT(*) FROM anekdots_" + theme_id_str + " WHERE id < an.id ) as num " +
                    " FROM anekdots_" + theme_id_str + " AS an " +
                    " WHERE an.active = 1 " +
                    " ORDER by an.id " +
                    " LIMIT 1";
        } else {
            // Has Remembered value
            if (vector == 1) {
                // NEXT
                query = " SELECT " + field + " " +
                        //"  ( SELECT COUNT(*) FROM anekdots_" + theme_id_str + " WHERE id < an.id ) as num " +
                        " FROM anekdots_" + theme_id_str + " AS an " +
                        " WHERE " +
                        "    (an.id > " + Integer.toString(last) + " AND an.active = 1 ) " +
                        " OR an.id = (SELECT MAX(id) FROM anekdots_" + theme_id_str + " WHERE active = 1) " +
                        " ORDER by an.id LIMIT 1";
            } else if (vector == 0) {
                // EXACT
                query = " SELECT " + field + " " +
                        //"  ( SELECT COUNT(*) FROM anekdots_" + theme_id_str + " WHERE id < an.id ) as num " +
                        " FROM anekdots_" + theme_id_str + " AS an " +
                        " WHERE " +
                        "   an.id >= " + Integer.toString(last) + " AND an.active = 1 " +
                        " ORDER by an.id " +
                        " LIMIT 1";
            } else {
                //PREV
                query = " SELECT " + field + " " +
                        // "  ( SELECT COUNT(*) FROM anekdots_" + theme_id_str + " WHERE id < an.id ) as num " +
                        " FROM anekdots_" + theme_id_str + " AS an " +
                        " WHERE " +
                        "   (an.id < " + Integer.toString(last) + " AND an.active = 1) " +
                        " OR an.id = (SELECT MIN(id) FROM anekdots_" + theme_id_str + " WHERE active = 1) " +
                        " ORDER by -an.id LIMIT 1";
            }
        }
        return query;
    }
}
