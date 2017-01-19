package com.seostudio.vistar.testproject.handlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seostudio.vistar.testproject.helpers.SQLiteHelper;

public class DBThemesHendler {

    public static void ThemesAnecdotsCountUpdate(Context context) {
        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(context);

        String query = "SELECT id FROM themes";
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                query = "UPDATE themes SET cnt = (select COUNT(*) FROM anekdots_" + Integer.toString(id) + ") where theme_id = " + Integer.toString(id);
                System.out.println(query);
                db.execSQL(query);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
