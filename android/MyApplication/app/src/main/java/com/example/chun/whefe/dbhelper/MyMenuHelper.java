package com.example.chun.whefe.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chun on 2017-05-22.
 */

public class MyMenuHelper extends SQLiteOpenHelper {
    public MyMenuHelper(Context context) {
        super(context, "menu2.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE menulist ( menu_name VARCHAR(30), menu_price VARCHAR(20),menu_image VARCHAR(30), menu_category VARCHAR(20) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE menulist ( menu_name VARCHAR(30), menu_price VARCHAR(20),menu_image VARCHAR(30), menu_category VARCHAR(20) );");
    }
}