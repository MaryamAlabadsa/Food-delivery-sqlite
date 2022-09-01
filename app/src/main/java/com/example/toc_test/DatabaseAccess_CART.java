package com.example.toc_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess_CART {

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess_CART Instance;
    Cursor cursor;

    private DatabaseAccess_CART(Context context) {
        this.openHelper = new DB_CART(context);
    }

    public static DatabaseAccess_CART getInstance(Context context) {
        if (Instance == null) {
            Instance = new DatabaseAccess_CART(context);
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


    public boolean insert_info_cart(cart cart) {
        ContentValues values = new ContentValues();
        values.put(DB_CART.table_cln_NAME, cart.getName());
        values.put(DB_CART.table_cln_NAME_count, cart.getCount());
        values.put(DB_CART.table_cln_NAME_date, cart.getDate());
        values.put(DB_CART.table_cln_NAME_has_potato, cart.getHas_potato());
        values.put(DB_CART.table_cln_NAME_has_toy, cart.getHas_toy());
        values.put(DB_CART.table_cln_NAME_name_user, cart.getName_user());
        values.put(DB_CART.table_cln_NAME_price, cart.getPrice());


        long res = database.insert(DB_CART.table_NAME, null, values);
        return res != -1;
    }

    public ArrayList<cart> getAll_items_CART() {
        ArrayList<cart> carts = new ArrayList<>();
        cursor = database.rawQuery("select * from " + DB_CART.table_NAME, null);
        //كود التعامل مع الكيرير و تحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي غلى بيانات ام لا
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DB_CART.table_cln_NAME_id));
                String name = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(DB_CART.table_cln_NAME_price));
                String yesToy = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME_has_toy));
                String yespotatoes = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME_has_potato));
                String date = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME_date));
                int quantity = cursor.getInt(cursor.getColumnIndex(DB_CART.table_cln_NAME_count));

                cart c = new cart(id, name, price, quantity, yesToy, yespotatoes, date);
                carts.add(c);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return carts;
    }

    public ArrayList<cart> get_last_item() {
        ArrayList<cart> carts = new ArrayList<>();
        cursor = database.rawQuery("select * from " + DB_CART.table_NAME, null);
        int max = 0;
        int id = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(DB_CART.table_cln_NAME_id));
                if (max < id) {
                    max = id;
                }
            }
            while (cursor.moveToNext());
            Cursor cursor = database.rawQuery("select * from " + DB_CART.table_NAME
                    + " WHERE " + DB_CART.table_cln_NAME_id + "=?", new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {

                int id1 = cursor.getInt(cursor.getColumnIndex(MY_DB.table_cln_NAME_id));
                String name = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(DB_CART.table_cln_NAME_price));
                String yesToy = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME_has_toy));
                String yespotatoes = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME_has_potato));
                int quantity = cursor.getInt(cursor.getColumnIndex(DB_CART.table_cln_NAME_count));
                String date = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME_date));

                cart c = new cart(id, name, price, quantity, yesToy, yespotatoes, date);
                carts.add(c);
                cursor.close();
                carts.add(c);
            }
            cursor.close();
        }
        return carts;
    }

    public Double get_total_coast() {
        cursor = database.rawQuery("select * from " + DB_CART.table_NAME, null);
        Double max = 0.0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                double price = cursor.getDouble(cursor.getColumnIndex(DB_CART.table_cln_NAME_price));
                max = max + price;
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return max;
    }

    public String get_user_name() {
        cursor = database.rawQuery("select * from " + DB_CART.table_NAME, null);
        String name = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String user_name = cursor.getString(cursor.getColumnIndex(DB_CART.table_cln_NAME_name_user));
                name = user_name;
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return name;
    }

    public boolean deleteInfo(cart cart) {
        String args[] = {cart.getDate() + ""};
        int res = database.delete(DB_CART.table_NAME, "date=?", args);
        return res > 0;
    }

    public void delete() {
        database.execSQL("delete from "+ DB_CART.table_NAME);
    }

    public void user_name(String name_sh){
        String name_res;
        Cursor cursor = database.rawQuery("select * from " + DB_purchase.table_NAME
                + " WHERE " + DB_purchase.table_cln_NAME_name_user + "=?", new String[]{String.valueOf(name_sh)});

    }
}
