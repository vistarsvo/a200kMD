package com.seostudio.vistar.testproject.loaders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.seostudio.vistar.testproject.helpers.SQLiteHelper;
import com.seostudio.vistar.testproject.models.CensorItem;
import com.seostudio.vistar.testproject.models.collections.CensorItemCollection;

public class AsyncCensorLoader extends AsyncTaskLoader<CensorItemCollection> {
    private SQLiteHelper sqLiteHelper;

    public AsyncCensorLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            //mWord = args.getString(ARG_WORD);
        }
        sqLiteHelper = SQLiteHelper.getInstance(context);
        forceLoad();
    }

    @Override
    public CensorItemCollection loadInBackground() {
        String query = "SELECT id, is_system, message FROM censor";
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CensorItem censorItem;
        CensorItemCollection censorItemCollection = new CensorItemCollection();
        if (cursor.moveToFirst()) {
            do {
                censorItem = new CensorItem();
                censorItem.setId(cursor.getInt(0));
                censorItem.setIs_system(cursor.getInt(1));
                censorItem.setMessage(cursor.getString(2));
                censorItemCollection.addCensorItem(censorItem);
            } while (cursor.moveToNext());
        }

        CensorItemCollection.lastLoaded = censorItemCollection;

        cursor.close();
        //db.close();
        return censorItemCollection;
    }
}
