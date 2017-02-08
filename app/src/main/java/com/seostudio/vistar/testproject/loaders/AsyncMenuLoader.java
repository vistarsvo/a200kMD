package com.seostudio.vistar.testproject.loaders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.seostudio.vistar.testproject.helpers.SQLiteHelper;
import com.seostudio.vistar.testproject.models.MenuItem;
import com.seostudio.vistar.testproject.models.collections.MenuItemCollection;

public class AsyncMenuLoader extends AsyncTaskLoader<MenuItemCollection> {
    private SQLiteHelper sqLiteHelper;

    public AsyncMenuLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            //mWord = args.getString(ARG_WORD);
        }
        sqLiteHelper = SQLiteHelper.getInstance(context);
        forceLoad();
    }

    @Override
    public MenuItemCollection loadInBackground() {
        if (MenuItemCollection.lastLoaded == null) {
            String query = "SELECT id, active, cnt, fullname, shortname, srt, theme_id FROM themes WHERE active = 1 ORDER by srt";
            SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            MenuItem menuItem;
            MenuItemCollection menuItemCollection = new MenuItemCollection();
            if (cursor.moveToFirst()) {
                do {
                    menuItem = new MenuItem();
                    menuItem.setId(cursor.getInt(0));
                    menuItem.setActive(cursor.getInt(1));
                    menuItem.setCnt(cursor.getString(2));
                    menuItem.setFullName(cursor.getString(3));
                    menuItem.setShortName(cursor.getString(4));
                    menuItem.setSort(cursor.getInt(5));
                    menuItem.setThemeId(cursor.getInt(6));
                    menuItemCollection.addMenuItem(menuItem);
                } while (cursor.moveToNext());
            }
            MenuItemCollection.lastLoaded = menuItemCollection;
            cursor.close();
            return menuItemCollection;
        } else {
            return MenuItemCollection.lastLoaded;
        }
    }
}
