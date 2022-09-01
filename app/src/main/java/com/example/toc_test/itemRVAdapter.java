package com.example.toc_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class itemRVAdapter extends RecyclerView.Adapter<itemRVAdapter.itemViewholder> {
    private ArrayList<item> items;
    private OnRecyclerViewClick listener;
    private OnRecyclerViewButtonClick listener_btn;
    private OnRecyclerViewButtonClick listener_btn_share;
    private OnRecyclerViewButtonClick listener_btn_fav;
    Context context;

    public ArrayList<item> getItems() {
        return items;
    }

    public void setItems(ArrayList<item> items) {
        this.items = items;
    }

    public itemRVAdapter(ArrayList<item> items,
                         OnRecyclerViewClick listener,
                         OnRecyclerViewButtonClick listener_btn,
                         OnRecyclerViewButtonClick listener_btn_fav,

                         Context context) {
        this.items = items;
        this.listener = listener;
        this.listener_btn = listener_btn;
        this.listener_btn_fav = listener_btn_fav;
        this.context = context;
    }



    public itemRVAdapter(ArrayList<item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public itemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_layout, null, false);
        itemViewholder viewHolder = new itemViewholder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull itemViewholder holder, int position) {
        item c = items.get(position);
        if (c.getImg() != null && !c.getImg().isEmpty()) {
            //   holder.img.setImageURI(Uri.parse(c.getImg()));
        } else {
            holder.img.setImageResource(R.drawable.boomer_stacker);

        }
        holder.tv_name.setText(c.getName());
        holder.tv_price.setText(String.valueOf(c.getPrice()));
        holder.tv_name.setTag(c.getId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class itemViewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_name, tv_price;


        public itemViewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_custom_food);
            tv_name = itemView.findViewById(R.id.tv_custom_item_name);
            tv_price = itemView.findViewById(R.id.tv_custom_item_price);
            Button btn_add_to_cart = itemView.findViewById(R.id.btn_add_to_cart);
            Button btn_share = itemView.findViewById(R.id.btn_share_custom);
            Button btn_fav = itemView.findViewById(R.id.btn_fav);
            btn_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) tv_name.getTag();
                    listener_btn_fav.onItemClick(id);
//                    DatabaseAccess_fav db_fav = DatabaseAccess_fav.getInstance(context);
//                    db_fav.open();
//                    int fav = db_fav.get_items_FAV_(id);
//                    db_fav.close();
//                    if (fav == 1) {
//                        btn_fav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
//                    }
                    Toast.makeText(context, "Soon it will be available ☻!", Toast.LENGTH_SHORT).show();

                }
            });



            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) tv_name.getTag();
                    listener_btn_share.onItemClick(id);
                    DatabaseAccess db = DatabaseAccess.getInstance(context);
                    db.open();
                    item p = db.getItems(id);
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, p.getName());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, p.getPrice());
                    if (p.getImg() == null) {
                        context.startActivity(Intent.createChooser(shareIntent, "send"));
                    } else {
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(p.getImg()));
                        shareIntent.setType("image/jpeg");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(Intent.createChooser(shareIntent, "send"));
                    }

                    db.close();
                }
            });
            btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) tv_name.getTag();
                    listener_btn.onItemClick(id);
                    // Toast.makeText(context, id + 1 + "", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) tv_name.getTag();
                    listener.onItemClick(id);
                    Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
                }
            });

           /**
            }**/
        }
    }

}

