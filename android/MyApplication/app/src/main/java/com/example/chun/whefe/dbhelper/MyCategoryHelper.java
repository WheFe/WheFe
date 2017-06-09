package com.example.chun.whefe.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chun on 2017-05-22.
 */

public class MyCategoryHelper extends SQLiteOpenHelper {
    public MyCategoryHelper(Context context) {
        super(context, "category2.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE categorylist ( category_name VARCHAR(20), cafe_id VARCHAR(20));");
        // create table ~~~~ _id integer PRIMARY KEY autoincrement
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("CREATE TABLE categorylist ( category_name VARCHAR(20), cafe_id VARCHAR(20));");
    }
}