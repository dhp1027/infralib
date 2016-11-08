package com.infrastructure.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.infrastructure.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/11/3.
 */

public class DBManager {
    private static final String tag = "DBManager";

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void updateIndexAd(String json) {
        db.execSQL("UPDATE " + DatabaseHelper.TABLE_INDEX + " SET value = ? WHERE key = ?", new Object[]{json, "ads"});
    }

    public String getIndexAd() {
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_INDEX + " WHERE key=?", new String[]{"ads"}); //执行本地SQL语句查询
        c.moveToFirst();
        String strAd = c.getString(2);
        c.close();
        return strAd;
    }

    public void updateIndexNews(String json) {
        db.execSQL("UPDATE " + DatabaseHelper.TABLE_INDEX + " SET value=? WHERE key=?", new String[]{json, "news"});
    }

    public String getIndexNews() {
        String strNews = "";
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_INDEX + " WHERE key=?", new String[]{"news"});
        if (c != null) {
            c.moveToFirst();
            strNews = c.getString(2);
        }
        return strNews;
    }


    public void closeDB() {
        if (db != null) {
            db.close();
        }
    }

    public String getDefaultIndexAd(Context context) {
        try {
            AssetManager am = context.getAssets();
            InputStream ins = am.open("adimg/default");
            InputStreamReader reader = new InputStreamReader(ins);
            char[] buffer = new char[4096];
            int c;
            StringBuffer sb = new StringBuffer();
            while ((c = reader.read(buffer)) > 0) {
                sb.append(buffer, 0, c);
            }
            //db.execSQL("UPDATE " + DatabaseHelper.TABLE_INDEX + " SET value=? WHERE key=?", new String[]{sb.toString(), "ads"});
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
