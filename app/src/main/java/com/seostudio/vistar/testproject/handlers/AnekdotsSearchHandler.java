package com.seostudio.vistar.testproject.handlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import com.seostudio.vistar.testproject.helpers.SQLiteHelper;
import com.seostudio.vistar.testproject.models.AnekdotItem;
import com.seostudio.vistar.testproject.models.collections.AnekdotItemCollection;

public class AnekdotsSearchHandler {
    private static SQLiteHelper sqLiteHelper;
    private static SQLiteDatabase db;
    private static Context mContext;

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
        query = "INSERT INTO search_results " +
                "(anekdot_id, theme_id) " +
                "VALUES " +
                "( ?, ? ) ";
        SQLiteStatement stmt = db.compileStatement(query);
        if (cursor.moveToFirst()) {
            db.beginTransaction();
            do {
                int id = cursor.getInt(0);
                /*
                query = "INSERT INTO search_results " +
                        "(anekdot_id, theme_id) " +
                        "VALUES " +
                        "( " + Integer.toString(id) + ", " + Integer.toString(themeId) + ") ";
                db.execSQL(query);
                */
                stmt.bindLong(1, id);
                stmt.bindLong(2, themeId);
                long entryID = stmt.executeInsert();
                stmt.clearBindings();
            } while (cursor.moveToNext());
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        cursor.close();
    }

    public static int resultCount(Context context) {
        int cnt = 0;
        init(context);
        String query = "SELECT count(*) as k FROM search_results";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cnt = cursor.getInt(0);
            System.out.println(query);
        }
        cursor.close();
        return cnt;
    }

    public static AnekdotItemCollection getResultCollection(Context context) {
        mContext = context;
        init(context);
        if (resultCount(context) > 0) {
            int[] themes = getCategoriesToRead();
            String[] query = new String[themes.length];
            for (int key = 0; key < themes.length; key++) {
                String tid = Integer.toString(themes[key]);
                query[key] = "SELECT " +
                        " anek.id, anek.anekdot_text, " +
                        " " + tid + " AS tid, tm.fullname, tm.shortname, " +
                        " fr.id AS fid, fr.date_add " +
                        "FROM search_results AS sr " +
                        "JOIN anekdots_" + tid + " AS anek ON anek.id = sr.anekdot_id " +
                        "LEFT JOIN favorites_results AS fr ON fr.theme_id = " + tid + " AND fr.anekdot_id = anek.id " +
                        "LEFT JOIN themes AS tm ON tm.theme_id = " + tid + " " +
                        "WHERE sr.theme_id = " + tid;
            }
            String implodeQuery = TextUtils.join(" UNION ", query);

            AnekdotItemCollection anekdotItemCollection = new AnekdotItemCollection();

            db = sqLiteHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(implodeQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    AnekdotItem anekdotItem = new AnekdotItem();
                    anekdotItem.setId(cursor.getInt(0));
                    anekdotItem.setText(cursor.getString(1));
                    anekdotItem.setTheme_id(cursor.getInt(2));
                    anekdotItem.setFullname(cursor.getString(3));
                    anekdotItem.setShortname(cursor.getString(4));
                    if (cursor.getInt(5) > 0) {
                        anekdotItem.setFavorite_id(cursor.getInt(5));
                        anekdotItem.setFavorite_date(cursor.getString(6));
                    }
                    anekdotItemCollection.addAnekdotItem(anekdotItem);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return anekdotItemCollection;
        } else {
            return null;
        }
    }

    // Select all categies from rearching results for UNION in SQL Query
    private static int[] getCategoriesToRead() {
        String query = " SELECT DISTINCT theme_id FROM search_results";
        db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int[] themesArray = new int[cursor.getCount()];
        if (cursor.moveToFirst()) {
            int iter = 0;
            do {
                int id = cursor.getInt(0);
                themesArray[iter] = id;
                iter++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return themesArray;
    }

}
