package com.example.toc_test;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class checkOut_purchase_adapter extends BaseAdapter {
    private Context context;
    private int recource;
    private ArrayList<cart> carts;
    private OnRecyclerViewClick listener;


    public checkOut_purchase_adapter(Context context, int recource, ArrayList<cart> carts) {
        this.context = context;
        this.recource = recource;
        this.carts = carts;
    }

    public void addItem(cart cart) {
        this.carts.add(cart);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public cart getItem(int position) {
        return carts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(recource, null, false);
        }

        TextView mealName, yesToy, yespotatoes, price, quantity, date;

        mealName = view.findViewById(R.id.mealNameinOrderSummary);
        price = view.findViewById(R.id.priceinOrderSummary);
        yesToy = view.findViewById(R.id.hasToy);
        yespotatoes = view.findViewById(R.id.haspotatoes);
        quantity = view.findViewById(R.id.quantityinOrderSummary);
        date = view.findViewById(R.id.et_date);
        final cart c = getItem(position);
        mealName.setText(c.getName());
        price.setText(c.getPrice() + "");
        yesToy.setText(c.getHas_toy());
        yespotatoes.setText(c.getHas_potato());
        quantity.setText(c.getCount() + "");

        date.setText(c.getDate());

        mealName.setTag(c.getDate());

        Button btn_remove_item = view.findViewById(R.id.btn_remove);

        btn_remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = (String) mealName.getTag();
                DatabaseAccess_CART db = DatabaseAccess_CART.getInstance(context);
                db.open();
                cart c = new cart(0, null, 0, 0, null, null, date);
                db.deleteInfo(c);
                db.close();
                carts.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }


}

