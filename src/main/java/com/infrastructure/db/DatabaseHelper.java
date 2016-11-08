package com.infrastructure.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.infrastructure.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Administrator on 2016/11/3.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String tag = "DatabaseHelper";
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VSERION);
        this.context = context;
    }

    /**
     * 0->1:first
     * 1-2:add default baaner ab json cache
     */
    public static final int DB_VSERION = 1;
    public static final String DB_NAME = "mycache.db";


    public static final String TABLE_INDEX = "index_cache";

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.e(tag, "====>populating new database");
        onUpgrade(db, 0, DB_VSERION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int version = oldVersion + 1; version <= newVersion; version++) {
            upgradeTo(db, version);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        LogUtil.e(tag, "====>database opened");
    }

    private void upgradeTo(SQLiteDatabase db, int version) {
        LogUtil.e(tag, "====>upgradeTo version " + version);
        switch (version) {
            case 1:
                createIndexTable(db);
                break;
            //case 2:
            //    addDefaultIndexCache(db);
            //    break;
            default:
                throw new IllegalStateException("don't known how to upgrade to " + version);
        }
    }

    private void createIndexTable(SQLiteDatabase db) {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("CREATE TABLE [" + TABLE_INDEX + "] (");
        sBuffer.append("[_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sBuffer.append("[key] TEXT,");
        sBuffer.append("[value] TEXT)");
        db.execSQL(sBuffer.toString());

//        db.execSQL("INSERT INTO " + TABLE_INDEX
//                + " VALUES(null,?,?)", new Object[] { "ads",
//                ""});
//        db.execSQL("INSERT INTO " + TABLE_INDEX
//                + " VALUES(null,?,?)", new Object[] { "news",
//                ""});
/*
        ContentValues cv = new ContentValues();
        //cv.put("_id", 0);
        cv.put("key","ads");
        cv.put("value", "");
        db.insert(TABLE_INDEX, null, cv);

        //cv.put("_id", 0);
        cv.put("key","news");
        cv.put("value", "");
        db.insert(TABLE_INDEX, null, cv);*/

        db.execSQL("INSERT INTO " + TABLE_INDEX + " VALUES(null,?,?)", new String[]{"ads", ""});
        db.execSQL("INSERT INTO " + TABLE_INDEX + " VALUES(null,?,?)", new String[]{"news", ""});
        String strAd = getDefaultIndexAd(context);
        db.execSQL("UPDATE " + DatabaseHelper.TABLE_INDEX + " SET value=? WHERE key=?", new String[]{strAd, "ads"});

        //db.close();
    }

    private String getDefaultIndexAd(Context context) {
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
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
