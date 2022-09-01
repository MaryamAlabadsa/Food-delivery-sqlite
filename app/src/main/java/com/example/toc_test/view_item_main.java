package com.example.toc_test;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class view_item_main extends AppCompatActivity {
    private RecyclerView rv;
    private FloatingActionButton fab;
    private itemRVAdapter adapter;
    private Toolbar toolbar;
    private DatabaseAccess db;
    private static final int ADD_ITEM_RES_CODE = 1;
    private static final int EDIT_item_RES_CODE = 2;
    private static final int RES_CODE = 3;
    public static final String item_KEY = "item";
    private static final int PERMISSION_REQ_CODE = 5;
    private static final String checkBox_key = "checkBox_key";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String checkbox_sh_remember, checkbox_sh_admin;
    BottomNavigationView bottom;
    ArrayList<item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_main);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        toolbar = findViewById(R.id.view_toolbar2);
        setSupportActionBar(toolbar);
        bottom = findViewById(R.id.bottom_na);
        rv = findViewById(R.id.main_rv);
        fab = findViewById(R.id.fab);
        bottom.setBackground(null);
        bottom.getMenu().getItem(2).setEnabled(false);


        sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences_return sh = new SharedPreferences_return(sp);

        checkbox_sh_remember = sh.checkbox_remember_me();
        checkbox_sh_admin = sh.checkbox_admin();

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_fav:
                        Intent i1 = new Intent(getBaseContext(), activity_favorites.class);
                        startActivityForResult(i1, RES_CODE);
                        return true;
                    case R.id.main_home:
                        Intent i2 = new Intent(getBaseContext(), view_item_main.class);
                        startActivityForResult(i2, RES_CODE);
                        return true;
                    case R.id.main_search:
                        Intent i3 = new Intent(getBaseContext(), search_layout.class);
                        startActivityForResult(i3, RES_CODE);
                        return true;

                    case R.id.main_cart:
                        Intent i4 = new Intent(getBaseContext(), cheakout.class);
                        startActivityForResult(i4, RES_CODE);

                        return true;
                }

                return false;
            }
        });

        // db.open();
        //  int num = (int) db.item_count();
        // bottom.getOrCreateBadge(R.id.main_cart).setNumber(num);
        //   db.close();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
        }

        Toast.makeText(this, checkbox_sh_admin+"", Toast.LENGTH_SHORT).show();
        String checkbox = sp.getString(checkBox_key, "");
        if (checkbox_sh_admin.equals("false")) {
            fab.hide();
        } else fab.show();
        Toast.makeText(this, checkbox_sh_admin + "", Toast.LENGTH_SHORT).show();


        db = DatabaseAccess.getInstance(this);
        db.open();
        items = db.getAllitems();
        db.close();
        adapter = new itemRVAdapter(items, new OnRecyclerViewClick() {
            @Override
            public void onItemClick(int carId) {
                Intent i = new Intent(getBaseContext(), show_item_info.class);
                i.putExtra(item_KEY, carId);
                startActivityForResult(i, EDIT_item_RES_CODE);
            }
        }, new OnRecyclerViewButtonClick() {
            @Override
            public void onItemClick(int carId) {
                Toast.makeText(view_item_main.this, carId + "\uD83D\uDE32\uD83D\uDD11", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), show_item_info.class);
                i.putExtra(item_KEY, carId);
                startActivityForResult(i, EDIT_item_RES_CODE);
            }
        },new OnRecyclerViewButtonClick() {
            @Override
            public void onItemClick(int carId) {
              /* item i = db.getItems(carId);
                DatabaseAccess_fav db_fav = DatabaseAccess_fav.getInstance(view_item_main.this);
                db_fav.open();
                db_fav.insert_info_FAV(i, 1);
                db_fav.close();*/

            }
        }, this);

        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), show_item_info.class);
                startActivityForResult(intent, ADD_ITEM_RES_CODE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_out:
                Intent n = new Intent(getBaseContext(), MainActivity.class);
                startActivityForResult(n, RES_CODE);

                edit = sp.edit();
                edit.putString("checkBox_key_remember", "false");
                edit.apply();
                return true;
            case R.id.main_menu_change_password:
                Intent n1 = new Intent(getBaseContext(), change_password.class);
                startActivityForResult(n1, RES_CODE);
                return true;
                case R.id.main_menu_purchase:
                Intent n2 = new Intent(getBaseContext(), purchase.class);
                startActivity(n2);
                return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //تم الحصول على الصلاحية
                } else {
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        db.open();
        ArrayList<item> items = db.getAllitems();
        db.close();

        adapter.setItems(items);
        adapter.notifyDataSetChanged();

    }
}