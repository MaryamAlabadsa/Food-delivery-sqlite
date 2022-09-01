package com.example.toc_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess Instance;
    Cursor cursor;

    private DatabaseAccess(Context context) {
        this.openHelper = new MY_DB(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (Instance == null) {
            Instance = new DatabaseAccess(context);
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

    public boolean insertinfo(item item) {

        ContentValues values = new ContentValues();
       //values.put(MY_DB.table_cln_NAME_id, item.getId());
        values.put(MY_DB.table_cln_NAME_img, item.getImg());
        values.put(MY_DB.table_cln_NAME_description, item.getDesc());
        values.put(MY_DB.table_cln_NAME, item.getName());
        values.put(MY_DB.table_cln_NAME_price, item.getPrice());

        long res = database.insert(MY_DB.table_NAME, null, values);
        return res != -1;
    }

    public boolean updateInfo(item item) {

        ContentValues values = new ContentValues();
        values.put(MY_DB.table_cln_NAME_id, item.getId());
        values.put(MY_DB.table_cln_NAME_img, item.getImg());
        values.put(MY_DB.table_cln_NAME_description, item.getDesc());
        values.put(MY_DB.table_cln_NAME, item.getName());
        values.put(MY_DB.table_cln_NAME_price, item.getPrice());

        String args[] = {item.getId() + ""};
        int res = database.update(MY_DB.table_NAME, values, "id=?", args);
        return res > 0;
    }

    //ارجاع عدد الاغمدة بالجدول
    public long item_count() {

        return DatabaseUtils.queryNumEntries(database, MY_DB.table_NAME);
    }

    //حذف البيانات من الجدول
    public boolean deleteInfo(item item) {
        String args[] = {item.getId() + ""};
        int res = database.delete(MY_DB.table_NAME, "id=?", args);
        return res > 0;
    }

    //دالة استرجاع البيانات جميعا
    public ArrayList<item> getAllitems() {
        ArrayList<item> items = new ArrayList<>();
        cursor = database.rawQuery("select * from " + MY_DB.table_NAME, null);
        //كود التعامل مع الكيرير و تحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي غلى بيانات ام لا
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MY_DB.table_cln_NAME_id));
                String name = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME));
                String description = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME_description));
                String img = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME_img));
                double price = cursor.getDouble(cursor.getColumnIndex(MY_DB.table_cln_NAME_price));
                item i = new item(id, price, name, description, img);
                items.add(i);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }

    public item getItems(int itemId) {
        Cursor cursor = database.rawQuery("select * from " + MY_DB.table_NAME
                + " WHERE " + MY_DB.table_cln_NAME_id + "=?", new String[]{String.valueOf(itemId)});
        //كود التعامل مع الكيرير و تحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي غلى بيانات ام لا
        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(MY_DB.table_cln_NAME_id));
            String name = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME));
            String description = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME_description));
            String img = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME_img));
            double price = cursor.getDouble(cursor.getColumnIndex(MY_DB.table_cln_NAME_price));
            item i = new item(id, price, name, description, img);
            cursor.close();
            return i;
        }
        return null;
    }

    public Double getQuantityNum(int itemId) {
        Cursor cursor = database.rawQuery("select * from " + MY_DB.table_NAME
                + " WHERE " + MY_DB.table_cln_NAME_id + "=?", new String[]{String.valueOf(itemId)});
        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(MY_DB.table_cln_NAME_id));
            double price = cursor.getDouble(cursor.getColumnIndex(MY_DB.table_cln_NAME_price));

            cursor.close();
            return price;
        }
        return null;
    }

    public ArrayList<item> getItems(String nameSearch) {
        ArrayList<item> items = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from " + MY_DB.table_NAME + " where " + MY_DB.table_cln_NAME + " like?", new
                String[]{"%" + nameSearch + "%"});
        //كود التعامل مع الكيرير و تحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي غلى بيانات ام لا
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MY_DB.table_cln_NAME_id));
                String name = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME));
                String description = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME_description));
                String img = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME_img));
                double price = cursor.getDouble(cursor.getColumnIndex(MY_DB.table_cln_NAME_price));
                item i = new item(id, price, name, description, img);
                items.add(i);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }

    class x implements OnRecyclerViewClick {

        @Override
        public void onItemClick(int carId) {

        }
    }


    //
    public item InfoToExtra(int itemId) {
        Cursor cursor = database.rawQuery("select * from " + MY_DB.table_NAME
                + " WHERE " + MY_DB.table_cln_NAME_id + "=?", new String[]{String.valueOf(itemId)});
        //كود التعامل مع الكيرير و تحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي غلى بيانات ام لا
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MY_DB.table_cln_NAME_id));
            String name = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME));
            String description = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME_description));
            String img = cursor.getString(cursor.getColumnIndex(MY_DB.table_cln_NAME_img));
            double price = cursor.getDouble(cursor.getColumnIndex(MY_DB.table_cln_NAME_price));
            item i = new item(price, name, img);
            cursor.close();
            return i;
        }
        return null;
    }

}
