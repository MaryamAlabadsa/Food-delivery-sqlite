package com.example.toc_test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class change_password extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    private TextInputLayout ed_current_password, ed_new_password, ed_Confirm_password;
    TextView cancel;
    Button btn_save;

    String password_current, new_password, Confirm_password;

    private static final String Password_key = "Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActionBar AB=getSupportActionBar();

        ed_Confirm_password = findViewById(R.id.ed_Confirm_password);
        ed_current_password = findViewById(R.id.ed_current_password);
        ed_new_password = findViewById(R.id.ed_new_password);

        btn_save = findViewById(R.id.btn_save);
        cancel = findViewById(R.id.cancel_button);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences_return sh = new SharedPreferences_return(sp);

        String password_sh = sh.Password();

        Toast.makeText(change_password.this, password_sh+"", Toast.LENGTH_SHORT).show();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password_current = ed_current_password.getEditText().getText().toString();
                new_password = ed_new_password.getEditText().getText().toString();
                Confirm_password = ed_Confirm_password.getEditText().getText().toString();
                if (!password_current.isEmpty()) {
                    if (password_sh.equals(password_current)) {
                        if(!new_password.isEmpty()){
                            if(!new_password.isEmpty()){
                        if (Confirm_password.equals(new_password)) {
                            edit = sp.edit();
                            edit.putString(Password_key, new_password);

                            Intent intent = new Intent(getBaseContext(), MainActivity.class);

                            edit.putString("checkBox_key_remember", "false");
                            edit.apply();
                            startActivity(intent);
                        }else {
                            Toast.makeText(change_password.this, "please check your password", Toast.LENGTH_SHORT).show();
                        }

                        }else { ed_new_password.setError("This can't be empty!!!");
                                Toast.makeText(change_password.this, "This can't be empty!!!", Toast.LENGTH_SHORT).show();}
                    }else { ed_new_password.setError("This can't be empty!!!");
                            Toast.makeText(change_password.this, "This can't be empty!!!", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else {
                        ed_current_password.setError("please check your password");
                        Toast.makeText(change_password.this, "please check your password", Toast.LENGTH_SHORT).show();
                    }

                }
                else{ ed_current_password.setError("This can't be empty!!!");}

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), view_item_main.class);
                startActivity(i);
            }
        });
    }
}