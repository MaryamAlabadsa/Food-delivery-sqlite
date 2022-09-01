package com.example.toc_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class search_layout extends AppCompatActivity {
    private RecyclerView rv;
    private itemRVAdapter adapter;
    private Toolbar toolbar;
    private DatabaseAccess db;
    ArrayList<item> items;

    public static final String item_KEY = "item";
    private static final int ADD_ITEM_RES_CODE = 1;
    private static final int EDIT_item_RES_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        toolbar = findViewById(R.id.view_toolbar2);
        setSupportActionBar(toolbar);
        rv = findViewById(R.id.Rv);
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
                //  Toast.makeText(s.this, carId + "\uD83D\uDE32\uD83D\uDD11", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), cheakout.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu); SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                db.open();
                ArrayList<item> items = db.getItems(query);
                db.close();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                db.open();
                ArrayList<item> items = db.getItems(newText);
                db.close();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
                return false;
            }

        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                db.open();
                ArrayList<item> items = db.getAllitems();
                db.close();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }
}

