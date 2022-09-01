package com.example.toc_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess_fav {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess_fav Instance;
    Cursor cursor;

    private DatabaseAccess_fav(Context context) {
        this.openHelper = new MY_DB_FAV(context);
    }

    public static DatabaseAccess_fav getInstance(Context context) {
        if (Instance == null) {
            Instance = new DatabaseAccess_fav(context);
        }
        return Instance;
    }


    public void open() {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close() {
        if (this.database != null) {
            this.database.close();
        }
    }

    //FAV DB
    public boolean insert_info_FAV(item item,int fav) {

        ContentValues values = new ContentValues();
        values.put(MY_DB_FAV.table_cln_NAME_id, item.getId());
        values.put(MY_DB_FAV.table_cln_NAME_img, item.getImg());
        values.put(MY_DB_FAV.table_cln_NAME_description, item.getDesc());
        values.put(MY_DB_FAV.table_cln_NAME, item.getName());
        values.put(MY_DB_FAV.table_cln_NAME_price, item.getPrice());
        values.put(MY_DB_FAV.table_cln_NAME_fav, fav);

        long res = database.insert(MY_DB_FAV.table_NAME, null, values);
        return res != -1;
    }

    public ArrayList<item> getAll_items_FAV() {
        ArrayList<item> items = new ArrayList<>();
        cursor = database.rawQuery("select * from " + MY_DB_FAV.table_NAME, null);
        //كود التعامل مع الكيرير و تحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي غلى بيانات ام لا
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MY_DB_FAV.table_cln_NAME_id));
                String name = cursor.getString(cursor.getColumnIndex(MY_DB_FAV.table_cln_NAME));
                String description = cursor.getString(cursor.getColumnIndex(MY_DB_FAV.table_cln_NAME_description));
                String img = cursor.getString(cursor.getColumnIndex(MY_DB_FAV.table_cln_NAME_img));
                double price = cursor.getDouble(cursor.getColumnIndex(MY_DB_FAV.table_cln_NAME_price));
                item i = new item(id, price, name, description, img);
                items.add(i);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }

    public int get_items_FAV_(int id) {
        int fav=0;
        Cursor cursor = database.rawQuery("select * from " + MY_DB_FAV.table_NAME
                + " WHERE " + MY_DB_FAV.table_cln_NAME_id + "=?", new String[]{String.valueOf(id)});
        //كود التعامل مع الكيرير و تحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي غلى بيانات ام لا
        if (cursor != null && cursor.moveToFirst()) {
            do {
      fav = cursor.getInt(cursor.getColumnIndex(MY_DB_FAV.table_cln_NAME_fav));

            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return fav;
    }



}
