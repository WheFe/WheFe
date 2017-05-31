package com.example.chun.whefe.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chun on 2017-05-31.
 */

public class MyCafeInfoHelper extends SQLiteOpenHelper {
    public MyCafeInfoHelper(Context context) {
        super(context, "cafe_info.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE cafeinfo ( cafe_id VARCHAR(30), cafe_name VARCHAR(30) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE cafeinfo ( cafe_id VARCHAR(30), cafe_name VARCHAR(30) );");
    }
}
