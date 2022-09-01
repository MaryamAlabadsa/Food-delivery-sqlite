package com.example.toc_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class cheakout extends AppCompatActivity {
    private ListView cart_list;
    private Button btn_order;
    private EditText tv_coast;
    private Toolbar toolbar;
    checkOut_cart_adapter adapter;

    ArrayList<cart> carts;
    private DatabaseAccess_CART db_cart;
    private DatabaseAccess_purchase db_purchase;
    //preferences
    private static final String checkBox_key = "check box";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String name_of_user, name_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheakout);

        ActionBar AB=getSupportActionBar();
        toolbar = findViewById(R.id.view_toolbar2);
        setSupportActionBar(toolbar);
        btn_order = findViewById(R.id.btn_order);
        cart_list = findViewById(R.id.cart_list);
        //preferences
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences_return sh = new SharedPreferences_return(sp);
        name_of_user = sh.user_name();


        db_cart = DatabaseAccess_CART.getInstance(this);
        db_cart.open();
        name_user = db_cart.get_user_name();

        carts = db_cart.getAll_items_CART();

        adapter = new checkOut_cart_adapter(this, R.layout.cart_item, carts);
        cart_list.setAdapter(adapter);
        tv_coast = findViewById(R.id.tv_CheckOut_subtotal);
        tv_coast.setText(db_cart.get_total_coast() + "");

        db_cart.close();
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent n = new Intent(cheakout.this, Place_Order.class);
                startActivity(n);

            }
        });
    }


}