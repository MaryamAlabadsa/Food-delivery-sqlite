package com.example.toc_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    final static public int Register_name = 100;
    final static public int login_name = 200;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    Button btn_login, btnRegister;
    CheckBox checkbox_remember_me;
    private static final int RES_CODE = 3;
    private TextInputEditText edUserName, edPassword;
    String name_sh, Password_sh, checkbox_sh_remember;
    String name, Password, checkbox;
    SharedPreferences_return sh;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        edUserName = findViewById(R.id.ed_user_name);
        edPassword = findViewById(R.id.ed_Password);
        checkbox_remember_me = findViewById(R.id.checkbox_remember_me);


        sp = PreferenceManager.getDefaultSharedPreferences(this);
         sh = new SharedPreferences_return(sp);

        Password_sh = sh.Password();
        checkbox_sh_remember = sh.checkbox_remember_me();


        //صوت الزر
        final MediaPlayer MediaPlayer= android.media.MediaPlayer.create(this,R.raw.sound);
        //زر تسجيل مستخدم جديد
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getBaseContext(), Create_new_account.class);
                startActivityForResult(Intent, Register_name);
                edit = sp.edit();
                edit.putString("checkBox_key_remember", "false");
                edit.apply();
                 MediaPlayer.start();
            }
        });
        
//-------------------------------------------btn_login---------------------------------------------------
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // MediaPlayer.start();
                login();
            }
        });

        checkbox_remember_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    edit = sp.edit();
                    edit.putString("checkBox_key_remember", "true");
                    edit.commit();
                    Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                } else if (!buttonView.isChecked()) {
                    edit = sp.edit();
                    edit.putString("checkBox_key_remember", "false");
                    edit.commit();
                    Toast.makeText(MainActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();

                }
            }
        });
        Toast.makeText(MainActivity.this, checkbox_sh_remember+"checkbox_sh_remember", Toast.LENGTH_SHORT).show();

        if (checkbox_sh_remember.equals("true")) {
            name_sh = sh.user_name();
            Password_sh = sh.Password();
              edPassword.setText(Password_sh);
              edUserName.setText(name_sh);
            checkbox_remember_me.setChecked(true);

        } else if (checkbox_sh_remember.equals("false")) {
            Toast.makeText(this, checkbox_sh_remember+"", Toast.LENGTH_SHORT).show();
        }

    }
    private void login(){
        Password = edPassword.getText().toString();
        name = edUserName.getText().toString();
        name_sh = sh.user_name();
        Password_sh = sh.Password();
        Toast.makeText(this, name_sh+"name_sh", Toast.LENGTH_SHORT).show();
        if(!name.isEmpty()||name.equals(null)) {
            if(!Password.isEmpty()||Password.equals(null)) {
                if(!name_sh.equals(null)||! (name_sh ==null)) {
                    if(!Password_sh.equals(null)|| !(Password_sh ==null)){
                        if (Password_sh.equals(Password)) {
                            if (name_sh.equals(name)) {
                                // Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                                Intent enter = new Intent(getApplicationContext(), view_item_main.class);
                                startActivityForResult(enter, login_name);
                            } else
                                Toast.makeText(MainActivity.this, "Please check your username and password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Please check your password", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "You do not have an account with this name1", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "You do not have an account with this name2", Toast.LENGTH_SHORT).show();
                }
            }else { edPassword.setError("THIS CAN'T BE EMBTY");}
        }else {
            edUserName.setError("THIS CAN'T BE EMBTY");
        }
        }



}
