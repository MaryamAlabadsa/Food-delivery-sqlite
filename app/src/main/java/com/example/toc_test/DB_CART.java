package com.example.toc_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DB_CART extends SQLiteAssetHelper {
    public static final String DB_NAME = "cart.db";
    public static final String table_NAME = " cart ";
    public static final String table_cln_NAME_id = "id";
    public static final String table_cln_NAME = "name";
    public static final String table_cln_NAME_count = "count";
    public static final String table_cln_NAME_has_toy = "has_toy";
    public static final String table_cln_NAME_has_potato = "has_potato";
    public static final String table_cln_NAME_name_user = "name_user";
    public static final String table_cln_NAME_date = "date";
    public static final String table_cln_NAME_price = "price";

    public static final int DB_VERSION = 1;

    public DB_CART(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//يتم استدعائها عند تحديث الداتا بيز
        db.execSQL("drop table if exists " + table_NAME + "");
        onCreate(db);
    }

}

