package com.example.chun.whefe.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chun on 2017-05-10.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    public MyOpenHelper(Context context) {
        super(context, "shopping_list.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE shoppinglist (_id INTEGER PRIMARY KEY autoincrement, name VARCHAR(20), hot VARCHAR(20), size VARCHAR(20), option VARCHAR(20),  price VARCHAR(20), image INTEGER, cafe_name VARCHAR(30));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE shoppinglist (_id INTEGER PRIMARY KEY autoincrement, name VARCHAR(20), hot VARCHAR(20), size VARCHAR(20), option VARCHAR(20),  price VARCHAR(20), image INTEGER, cafe_name VARCHAR(30));");
    }
}
