package com.example.toc_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MY_DB extends SQLiteAssetHelper {
    public static final String DB_NAME = "food.db";
    public static final String table_NAME = " food ";
    public static final String table_cln_NAME_id = "id";
    public static final String table_cln_NAME = "name";
    public static final String table_cln_NAME_img = "img";
    public static final String table_cln_NAME_description = "description";
    public static final String table_cln_NAME_price = "price";

    public static final int DB_VERSION = 2;

    public MY_DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//يتم استدعائها عند تحديث الداتا بيز
        db.execSQL("drop table if exists " + table_NAME + "");
        onCreate(db);
    }

}
