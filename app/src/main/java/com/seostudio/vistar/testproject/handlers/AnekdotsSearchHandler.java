package com.seostudio.vistar.testproject.handlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seostudio.vistar.testproject.helpers.SQLiteHelper;

public class AnekdotsSearchHandler {
    private static SQLiteHelper sqLiteHelper;
    private static SQLiteDatabase db;

    private static void init(Context context) {
        sqLiteHelper = SQLiteHelper.getInstance(context);
        db = sqLiteHelper.getWritableDatabase();
    }

    public static void clearResults(Context context) {
        init(context);
        String query = "DELETE FROM search_results";
        db.execSQL(query);
    }

    public static void searchIntoTheme(Context context, int themeId, String searchString) {
        init(context);
        String tableName = "anekdots_" + String.valueOf(themeId);
        String query = " SELECT ant.id " +
                       " FROM " + tableName + " AS ant " +
                       " WHERE ant.active = 1 AND ant.anekdot_text LIKE '%" + searchString + "%' " +
                       " LIMIT 100";
        db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                query = "INSERT INTO search_results " +
                        "(anekdot_id, theme_id) " +
                        "VALUES " +
                        "( " + Integer.toString(id) + ", " + Integer.toString(id) + ") ";
                db.execSQL(query);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public static int resultCount(Context context) {
        int cnt = 0;
        init(context);
        String query = "SELECT count(*) FROM search_results";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cnt = cursor.getInt(0);
        }
        cursor.close();
        return cnt;
    }

}
