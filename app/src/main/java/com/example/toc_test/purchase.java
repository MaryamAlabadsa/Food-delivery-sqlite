package com.example.toc_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class purchase extends AppCompatActivity {
    private ListView lv_purchase;
    private Toolbar toolbar;
    checkOut_purchase_adapter adapter;
    TextView txt1, txt2, txt3;
    LinearLayout layout;
    ArrayList<cart> carts;

    private DatabaseAccess_purchase db_purchase;
    //preferences
    private static final String checkBox_key = "check box";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String name_of_user, name_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
//INFLAT
        toolbar = findViewById(R.id.view_toolbar2);
        setSupportActionBar(toolbar);
        lv_purchase = findViewById(R.id.lv_purchase);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        layout = findViewById(R.id.layout);

        ActionBar AB = getSupportActionBar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        db_purchase = DatabaseAccess_purchase.getInstance(this);
        db_purchase.open();
        switch (item.getItemId()) {
            case R.id.cart_clear_purchase:
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                db_purchase.delete_purchase();
                finish();
                gone();
                return true;


            case R.id.cart_shaw_all_purchase:
                gone();
                lv_purchase.setVisibility(View.VISIBLE);
                carts = db_purchase.getAll_items_purchase();
                adapter = new checkOut_purchase_adapter(this, R.layout.cart_item, carts);
                lv_purchase.setAdapter(adapter);

                return true;


            case R.id.cart_shaw_last_purchase:
                gone();
                lv_purchase.setVisibility(View.VISIBLE);
                carts = db_purchase.get_last_item_id();
                adapter = new checkOut_purchase_adapter(this, R.layout.cart_item, carts);
                lv_purchase.setAdapter(adapter);
                Toast.makeText(this, db_purchase.get_last_item_name() + "", Toast.LENGTH_SHORT).show();
                return true;
        }
        db_purchase.close();

        return false;
    }

    void gone() {
        layout.setVisibility(View.GONE);

    }
}