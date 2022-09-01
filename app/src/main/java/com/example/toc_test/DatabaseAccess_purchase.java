package com.example.toc_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess_purchase {

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess_purchase Instance;
    Cursor cursor;

    private DatabaseAccess_purchase(Context context) {
        this.openHelper = new DB_purchase(context);
    }

    public static DatabaseAccess_purchase getInstance(Context context) {
        if (Instance == null) {
            Instance = new DatabaseAccess_purchase(context);
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


    public boolean insert_info_purchase(ArrayList<cart> carts) {
        ContentValues values = new ContentValues();
        long res = 0;
        for (int i = 0; i < carts.size(); i++) {
            cart cart = carts.get(i);
            values.put(DB_purchase.table_cln_NAME, cart.getName());
            values.put(DB_purchase.table_cln_NAME_count, cart.getCount());
            values.put(DB_purchase.table_cln_NAME_date, cart.getDate());
            values.put(DB_purchase.table_cln_NAME_has_potato, cart.getHas_potato());
            values.put(DB_purchase.table_cln_NAME_has_toy, cart.getHas_toy());
            values.put(DB_purchase.table_cln_NAME_name_user, cart.getName_user());
            values.put(DB_purchase.table_cln_NAME_price, cart.getPrice());
            res = database.insert(DB_purchase.table_NAME, null, values);
        }

        return res != -1;
    }

    public ArrayList<cart> getAll_items_purchase() {
        ArrayList<cart> carts = new ArrayList<>();
        cursor = database.rawQuery("select * from " + DB_purchase.table_NAME, null);
        //كود التعامل مع الكيرير و تحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي غلى بيانات ام لا
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DB_purchase.table_cln_NAME_id));
                String name = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(DB_purchase.table_cln_NAME_price));
                String yesToy = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_has_toy));
                String yespotatoes = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_has_potato));
                String date = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_date));
                int quantity = cursor.getInt(cursor.getColumnIndex(DB_purchase.table_cln_NAME_count));

                cart c = new cart(id, name, price, quantity, yesToy, yespotatoes, date);
                //  cart c = new cart(id, name, price, quantity, yesToy, yespotatoes);
                carts.add(c);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return carts;
    }

    public void delete_purchase() {
        database.execSQL(" delete  from " + DB_purchase.table_NAME);
    }

    int max_id() {

        cursor = database.rawQuery("select * from " + DB_purchase.table_NAME, null);
        int max = 0;
        int id = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(DB_purchase.table_cln_NAME_id));
                if (max < id) {
                    max = id;
                }
            }
            while (cursor.moveToNext());

        }
        return id;
    }


    public ArrayList<cart> get_last_item_id() {
        ArrayList<cart> carts = new ArrayList<>();
        int id_max = max_id();
        Cursor cursor = database.rawQuery("select * from " + DB_purchase.table_NAME
                + " WHERE " + DB_purchase.table_cln_NAME_id + "=?", new String[]{String.valueOf(id_max)});
        if (cursor != null && cursor.moveToFirst()) {

            int id1 = cursor.getInt(cursor.getColumnIndex(DB_purchase.table_cln_NAME_id));
            String name = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(DB_purchase.table_cln_NAME_price));
            String yesToy = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_has_toy));
            String yespotatoes = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_has_potato));
            int quantity = cursor.getInt(cursor.getColumnIndex(DB_purchase.table_cln_NAME_count));
            String date = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_date));

            cart c = new cart(id1, name, price, quantity, yesToy, yespotatoes, date);
            carts.add(c);
            cursor.close();

        }
        cursor.close();

        return carts;
    }

    String U_name;

    public String get_last_item_name() {
        int id_max = max_id();
        Cursor cursor = database.rawQuery("select * from " + DB_purchase.table_NAME
                + " WHERE " + DB_purchase.table_cln_NAME_id + "=?", new String[]{String.valueOf(id_max)});
        if (cursor != null && cursor.moveToFirst()) {
           U_name = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_name_user));

            cursor.close();
        }

        return U_name;
    }

    /*public ArrayList<cart> get_last_item() {
        ArrayList<cart> carts = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT  *   FROM " + DB_purchase.table_NAME + " ORDER BY " + DB_purchase.table_cln_NAME_id + " DESC " + " LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {

            int id1 = cursor.getInt(cursor.getColumnIndex(DB_purchase.table_cln_NAME_id));
            String name = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(DB_purchase.table_cln_NAME_price));
            String yesToy = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_has_toy));
            String yespotatoes = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_has_potato));
            int quantity = cursor.getInt(cursor.getColumnIndex(DB_purchase .table_cln_NAME_count));
            String date = cursor.getString(cursor.getColumnIndex(DB_purchase.table_cln_NAME_date));

            cart c = new cart(id1, name, price, quantity, yesToy, yespotatoes, date);
            carts.add(c);
            cursor.close();

        }

        return carts;
    }*/
}