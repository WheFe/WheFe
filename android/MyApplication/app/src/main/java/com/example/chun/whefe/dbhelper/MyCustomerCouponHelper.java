package com.example.chun.whefe.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chun on 2017-05-30.
 */

public class MyCustomerCouponHelper extends SQLiteOpenHelper {
    public MyCustomerCouponHelper(Context context) {
        super(context, "customer_coupon.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE customercoupon (cafe_id VARCHAR(20), cafe_name VARCHAR(40), coupon_name VARCHAR(40), coupon_price VARCHAR(20), usable Boolean, coupon_start VARCHAR(20), coupon_end VARCHAR(20));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE customercoupon (cafe_id VARCHAR(20), cafe_name VARCHAR(40), coupon_name VARCHAR(40), coupon_price VARCHAR(20), usable Boolean, coupon_start VARCHAR(20), coupon_end VARCHAR(20));");

    }
}
