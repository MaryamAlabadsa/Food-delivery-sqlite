package com.example.toc_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class activity_favorites extends AppCompatActivity {
    private RecyclerView rv;
    private itemRVAdapter adapter;
    private Toolbar toolbar;
    private DatabaseAccess db;
    private DatabaseAccess_fav db_fav;
    ArrayList<item> items;
    private static final int EDIT_item_RES_CODE = 2;
    public static final String item_KEY = "item";
    private int itemId = -1;
    item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        //INFLAT
        rv = findViewById(R.id.main_rv);
        toolbar = findViewById(R.id.view_toolbar2);


        toolbar.setTitle(getString(R.string.txt_favorite));
        Toast.makeText(this, "Soon it will be available ☻!", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        itemId = intent.getIntExtra(view_item_main.item_KEY, -1);
        db = DatabaseAccess.getInstance(this);
        db.open();
        item = db.getItems(itemId);

        db_fav = DatabaseAccess_fav.getInstance(this);
        db_fav.open();
        items = db_fav.getAll_items_FAV();
        db_fav.close();
        //db.close();
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
                Intent i = new Intent(getBaseContext(), show_item_info.class);
                i.putExtra(item_KEY, carId);
                startActivityForResult(i, EDIT_item_RES_CODE);
            }
        }, new OnRecyclerViewButtonClick() {
            @Override
            public void onItemClick(int carId) {
                db.open();
                final item items = db.getItems(carId);
                Intent shareIntent = new Intent();
                // shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra("QuestionListExtra", (Parcelable) items);
                startActivity(Intent.createChooser(shareIntent, "send"));
                db.close();
            }
        }, this);

        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);

    }

    private void destroy() {

    }
}