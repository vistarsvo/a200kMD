package com.seostudio.vistar.testproject.loaders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.util.TimeUtils;
import android.util.Log;

import com.seostudio.vistar.testproject.helpers.SQLiteHelper;
import com.seostudio.vistar.testproject.models.AnekdotItem;
import com.seostudio.vistar.testproject.models.collections.AnekdotItemCollection;

import java.sql.Time;

public class AsyncAnekdotLoader extends AsyncTaskLoader<AnekdotItemCollection> {
    private SQLiteHelper sqLiteHelper;
    private String queryGetAnekdot;
    private int count;
    private int theme_id;

    public AsyncAnekdotLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            queryGetAnekdot = args.getString("query");
            count = args.getInt("count", 1);
            theme_id = args.getInt("theme_id", 1);
        }
        sqLiteHelper = SQLiteHelper.getInstance(context);
        forceLoad();
    }

    @Override
    public AnekdotItemCollection loadInBackground() {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryGetAnekdot, null);
        AnekdotItem anekdotItem;
        AnekdotItemCollection anekdotItemCollection = new AnekdotItemCollection();
        if (cursor.moveToFirst()) {
            do {
                anekdotItem = new AnekdotItem();
                anekdotItem.setId(cursor.getInt(0));
                anekdotItem.setText(cursor.getString(2));
                anekdotItem.setNum(getCurrentNum(anekdotItem.getId()));
                anekdotItem.setTheme_id(theme_id);
                getIsInFavorite(anekdotItem);
                anekdotItemCollection.addAnekdotItem(anekdotItem);

            } while (cursor.moveToNext());
        } else {
            anekdotItem = new AnekdotItem();
            anekdotItem.setId(1);
            anekdotItem.setText("ERROR! NOT FOUND!");
            anekdotItem.setNum(0);
            anekdotItemCollection.addAnekdotItem(anekdotItem);
        }
        AnekdotItemCollection.lastLoaded = anekdotItemCollection;
        cursor.close();
        return anekdotItemCollection;
    }

    private int getCurrentNum(int id) {
        int cnt = 0;
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String queryGetNums = " SELECT COUNT(*) FROM anekdots_" + Integer.toString(theme_id) + " WHERE id < " + Integer.toString(id);
        Cursor cursor = db.rawQuery(queryGetNums, null);
        if (cursor.moveToFirst()) {
            cnt = cursor.getInt(0) + 1;
        }
        cursor.close();
        return cnt;
    }

    private void getIsInFavorite(AnekdotItem anekdotItem) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String queryGetNums = " SELECT id, date_add FROM favorites_results " +
                " WHERE theme_id = " + Integer.toString(theme_id) +
                "   AND anekdot_id = " + Integer.toString(anekdotItem.getId());
        Cursor cursor = db.rawQuery(queryGetNums, null);
        if (cursor.moveToFirst()) {
            anekdotItem.setFavorite_id(cursor.getInt(0));
            anekdotItem.setFavorite_date(cursor.getString(1));
        }
        cursor.close();
    }


}