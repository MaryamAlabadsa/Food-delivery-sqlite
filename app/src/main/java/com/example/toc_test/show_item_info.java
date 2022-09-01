package com.example.toc_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;

public class show_item_info extends AppCompatActivity {
    //view
    private TextInputEditText et_name, et_price, et_description;
    private ImageView iv;
    TextView quantityNum;
    RadioGroup group;
    Button btn_add_to_cart;
    private Toolbar toolbar;
    ImageButton btn_add, btn_remove;
    CheckBox add_kids_game, add_extra_potatoes;
    LinearLayout layout_quantity;
    //preferences
    private static final String checkBox_key = "check box";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String name_of_user, checkbox_sh_admin;
    SharedPreferences_return sh;
    //add||show info
    private int itemId = -1;
    private DatabaseAccess db;
    final int ACTIVITY_SELECT_IMAGE = 100;
    public static final int ADD_item_RESULT_CODE = 2;
    public static final int EDIT_item_RESULT_CODE = 3;
    public static final int delete_item_RESULT_CODE = 4;
    //
    Uri URI = Uri.EMPTY;
    int quantity;
    //time
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    //
    private DatabaseAccess_CART db_cart;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_info);

        //inflate
        toolbar = findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        iv = findViewById(R.id.view_img);
        et_name = findViewById(R.id.text_input_name);
        et_description = findViewById(R.id.text_input_description);
        et_price = findViewById(R.id.text_input_price);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        group = findViewById(R.id.group);
        btn_add = findViewById(R.id.btn_add);
        btn_remove = findViewById(R.id.btn_remove);
        quantityNum = findViewById(R.id.quantity_num);
        add_extra_potatoes = findViewById(R.id.add_extra_potatoes);
        add_kids_game = findViewById(R.id.add_kids_game);
        layout_quantity = findViewById(R.id.layout_quantity);

//preferences
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        sh = new SharedPreferences_return(sp);
        checkbox_sh_admin = sh.checkbox_admin();

        //add||show info
        Intent intent = getIntent();
        db = DatabaseAccess.getInstance(this);
        itemId = intent.getIntExtra(view_item_main.item_KEY, -1);
        if (itemId == -1) {
            //عملية اضافة
            enableFields();
        } else {
            //عملية عرض
            disableFields();
            db.open();
            item i = db.getItems(itemId);
            db.close();
            if (i != null) {
                fillitemFileds(i);
            }
        }

        //add_img
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(in, ACTIVITY_SELECT_IMAGE);

            }
        });

        //quantity
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //food price
                quantity++;
                DisPlayquantity();

                //extra price

                double extra = extra_price(add_extra_potatoes, add_kids_game);
                et_price.setText(String.valueOf(extra));


                db.close();
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity == 0) {
                    Toast.makeText(show_item_info.this, "can't decrease quantity<0", Toast.LENGTH_SHORT).show();

                } else {
                    quantity--;
                    DisPlayquantity();
                    //extra price
                    double extra = extra_price(add_extra_potatoes, add_kids_game);
                    et_price.setText(String.valueOf(extra));
                }
            }
        });
        //add to cart
        db_cart = DatabaseAccess_CART.getInstance(this);
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(show_item_info.this, cheakout.class);
                startActivity(n);
                name_of_user = sh.user_name();
                db_cart.open();
                cart cart1 = saveCart(name_of_user);
                db_cart.insert_info_cart(cart1);
                db_cart.close();
                Toast.makeText(show_item_info.this, date + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(show_item_info.this, name_of_user + "", Toast.LENGTH_SHORT).show();
            }
        });
        //time
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        date = dateFormat.format(calendar.getTime());
    }

    cart cart;

    private cart saveCart(String name_of_user) {
        String name = et_name.getText().toString();
        double price = Double.parseDouble(et_price.getText().toString());
        int quantity = Integer.parseInt(quantityNum.getText().toString());
        ContentValues values = new ContentValues();
        String has_toy;
        String has_potato;

        if (add_kids_game.isChecked()) {
            has_toy = "Has toy: yes";
        } else {
            has_toy = "Has toy: NO";
        }
        if (add_extra_potatoes.isChecked()) {
            has_potato = "Has extra potatoes: yes";
        } else {
            has_potato = "Has extra potatoes: NO";
        }
        cart = new cart(name, price, quantity, has_toy, has_potato, name_of_user, date);
        return cart;
    }

    private double extra_price(CheckBox add_extra_potatoes, CheckBox add_kids_game) {
        db.open();
        double price = db.getQuantityNum(itemId);
        db.close();
        if (add_extra_potatoes.isChecked()) {
            price = price + 3;
        }
        if (add_kids_game.isChecked()) {
            price = price + 2;
        }
        return price * quantity;
    }

    private void DisPlayquantity() {
        quantityNum.setText(quantity + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
            URI = data.getData();
            iv.setImageURI(URI);

        }
    }

    private void fillitemFileds(item i) {
        if (i.getImg() != null && !i.getImg().equals(""))
//            iv.setImageURI(Uri.parse(i.getImg()));
            et_name.setText(i.getName());
        et_price.setText(i.getPrice() + "");
        et_description.setText(i.getDesc());

    }

    private void disableFields() {
        iv.setEnabled(false);
        et_name.setEnabled(false);
        et_price.setEnabled(false);
        et_description.setEnabled(false);

    }

    private void disable() {
        iv.setEnabled(true);
        et_name.setEnabled(true);
        et_price.setEnabled(true);
        et_description.setEnabled(true);
    }

    private void enableFields() {
        iv.setImageURI(null);
        et_name.setText("");
        et_price.setText("");
        et_description.setText("");
        btn_add_to_cart.setVisibility(View.GONE);
        group.setVisibility(View.GONE);
        layout_quantity.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem save = menu.findItem(R.id.details_menu_save);
        MenuItem edit = menu.findItem(R.id.details_menu_edit);
        MenuItem delete = menu.findItem(R.id.details_menu_delete);
        if (checkbox_sh_admin.equals("false")) {
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        } else if (itemId == -1) {
            //عملية اضافة
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        } else {
            //عملية عرض
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        String name, desc, img = "";
        Double price;
        db.open();
        boolean res;
        item i;
        switch (item.getItemId()) {
            case R.id.details_menu_save:
                if (checkbox_sh_admin.equals("false")) {
                    Intent in = new Intent(getBaseContext(), view_item_main.class);
                    startActivityForResult(in, 3);
                } else {
                    name = et_name.getText().toString();
                    desc = et_description.getText().toString();
                    price = Double.parseDouble(et_price.getText().toString());
                    img = URI.toString();
                    i = new item(price, name, desc, img);
                    Toast.makeText(this, "ADD 1 SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                    if (itemId == -1) {
                        i = new item(price, name, desc, img);

                        Toast.makeText(this, db.insertinfo(i) + "", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "ADD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        setResult(ADD_item_RESULT_CODE, null);
                        finish();

                    } else {
                        res = db.updateInfo(i);
                        if (res) {
                            Toast.makeText(this, "MODIFIED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            setResult(EDIT_item_RESULT_CODE, null);
                            finish();
                        }
                    }
                }
                return true;
            case R.id.details_menu_edit:
                disable();
                MenuItem save = toolbar.getMenu().findItem(R.id.details_menu_save);
                MenuItem edit = toolbar.getMenu().findItem(R.id.details_menu_edit);
                MenuItem delete = toolbar.getMenu().findItem(R.id.details_menu_delete);
                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);
                return true;

            case R.id.details_menu_delete:
                i = new item(itemId, 0, null, null, null);
                res = db.deleteInfo(i);
                Toast.makeText(this, "deleted SUCCESSFULLY ", Toast.LENGTH_SHORT).show();
                setResult(delete_item_RESULT_CODE, null);

                return true;
        }
        db.close();
        return false;
    }

}