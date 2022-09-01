package com.example.toc_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Place_Order extends AppCompatActivity {
    EditText edit_date;
    TextView txt_total_cash, txt_user_phone, txt_user_address, edt_date;
    EditText txt_new_address;
    Button btn_proceed, btn_add_new_address;
    CheckBox ckd_default_address;

    //preferences
    private static final String checkBox_key = "check box";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    Toolbar toolbar;
    int phone;
    String address, newAddress;
    String spinner_key = "spinner_key";
    //time
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    //Database
    ArrayList<cart> carts;
    private DatabaseAccess_CART db_cart;
    private DatabaseAccess_purchase db_purchase;
    SQLiteDatabase database;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__order);
        //inflat
        toolbar = findViewById(R.id.view_toolbar2);
        edit_date = findViewById(R.id.edt_date);
        txt_total_cash = findViewById(R.id.txt_total_cash);
        txt_user_phone = findViewById(R.id.txt_user_phone);
        txt_user_address = findViewById(R.id.txt_user_address);
        txt_new_address = findViewById(R.id.txt_new_address);
        edt_date = findViewById(R.id.edt_date);
        btn_proceed = findViewById(R.id.btn_proceed);
        btn_add_new_address = findViewById(R.id.btn_add_new_address);
        ckd_default_address = findViewById(R.id.ckd_default_address);
        //preferences
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences_return sh = new SharedPreferences_return(sp);
        phone = sh.Phone();
        address = sh.address();
        //time
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");
        date = dateFormat.format(calendar.getTime());
        //
        toolbar.setTitle(getString(R.string.place_order));
        edt_date.setText(date + "");
        txt_user_phone.setText(phone + "");
        txt_user_address.setText(address + "");

        db_cart = DatabaseAccess_CART.getInstance(this);
        db_cart.open();
        txt_total_cash.setText(db_cart.get_total_coast() + "$");
        carts = db_cart.getAll_items_CART();

        db_purchase = DatabaseAccess_purchase.getInstance(Place_Order.this);


        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAddress = txt_new_address.getText().toString();
                edit = sp.edit();
                edit.putString(spinner_key, newAddress);
                edit.commit();
                address = sh.address();

                db_purchase.open();
                db_purchase.insert_info_purchase(carts);
                db_cart.open();
                db_cart.delete();



                Intent i = new Intent(Place_Order.this, final_screen.class);
                startActivity(i);

            }
        });
        db_purchase.close();
        db_cart.close();
        Toast.makeText(this, address + "address", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, newAddress + "newAddressL", Toast.LENGTH_SHORT).show();
        btn_add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ckd_default_address.setChecked(false);
                txt_new_address.setVisibility(View.VISIBLE);
            }
        });
        ActionBar AB=getSupportActionBar();

    }

}