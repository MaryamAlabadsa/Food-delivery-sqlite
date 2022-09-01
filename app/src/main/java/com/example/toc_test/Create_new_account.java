package com.example.toc_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class Create_new_account extends AppCompatActivity {
    Uri URI;
    TextInputLayout ed_user_name, ed_full_name, ed_email, ed_Password, ed_Phone;
    TextInputEditText ed_date_of_birth;
    private static final String date_of_birth_key = "date_of_birth",
            user_name_key = "user_name", full_name_key = "full_name",
            email_key = "email", Password_key = "Password", Phone_key = "Phone",
            checkbox_admin_key = "checkbox_admin_key", URI_key = "URI", spinner_key = "spinner";
    int year, day, month;
    ImageView img_user;
    Button btn_finish;
    AppCompatSpinner spinner;
    final int ACTIVITY_SELECT_IMAGE = 100;
    final int ACTIVITY_finish = 100;
    CheckBox checkBox;
    public static final int Capture_Image_URI = 1001;
    public static final int PICK_FROM_CAMERA = 1001;
    Uri outputFileUri;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String spinner_name;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        //date picker
        ed_date_of_birth = findViewById(R.id.ed_datepicker);
        Calendar Calendar = java.util.Calendar.getInstance();
        year = Calendar.get(java.util.Calendar.YEAR);
        month = Calendar.get(java.util.Calendar.MONTH);
        day = Calendar.get(java.util.Calendar.DAY_OF_MONTH);

        ed_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDiallog();
            }
        });
//انفلات
        ed_email = findViewById(R.id.ed_EmailAddress);
        ed_Password = findViewById(R.id.ed_Password);
        ed_full_name = findViewById(R.id.ed_first_name);
        ed_user_name = findViewById(R.id.ed_user_name);
        ed_Phone = findViewById(R.id.ed_num_Phone);
        checkBox = findViewById(R.id.checkBox);
        spinner = findViewById(R.id.spinner);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edit = sp.edit();
        //add img
        img_user = findViewById(R.id.img_new_account);
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
            }
        });
//spinner
        Resources res = getResources();
        String[] city = res.getStringArray(R.array.DialingCountryCode);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, city); // Create Arrayadapter adapter
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_name = spinner.getItemAtPosition(i).toString();
                //  Toast.makeText(Create_new_account.this,spinner_name,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    edit.putString(checkbox_admin_key, "true");
                    Toast.makeText(Create_new_account.this, "You are now administrator", Toast.LENGTH_SHORT).show();
                } else if (!buttonView.isChecked()) {
                    edit.putString(checkbox_admin_key, "false");
                    Toast.makeText(Create_new_account.this, "You", Toast.LENGTH_SHORT).show();

                }
            }
        });
        //btn finish
        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String full_name = ed_full_name.getEditText().getText().toString();
                final String date_of_birth = ed_date_of_birth.getText().toString();
                final String email = ed_email.getEditText().getText().toString();
                final String Password = ed_Password.getEditText().getText().toString();
                final int Phone = Integer.parseInt(ed_Phone.getEditText().getText().toString());
                final String user_name = ed_user_name.getEditText().getText().toString();

                edit.putString(full_name_key, full_name);
                edit.putString(date_of_birth_key, date_of_birth);
                edit.putString(email_key, email);
                edit.putString(Password_key, Password);
                edit.putInt(Phone_key, Phone);
                edit.putString(user_name_key, user_name);
                edit.putString(spinner_key, spinner_name);
                edit.putString(URI_key, URI.toString());


                edit.commit();
                //Toast.makeText(Create_new_account.this, sp.getString(Password_key, "")+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(Create_new_account.this, "The process has saved successfully", Toast.LENGTH_SHORT).show();
            }

        });
        //confirmInput(btn_finish);
    }

    private void showDateDiallog() {
        DatePickerDialog.OnDateSetListener Listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ed_date_of_birth.setText(year + "_" + month + "_" + dayOfMonth);

            }


        };
        DatePickerDialog DatePickerDialog = new DatePickerDialog(this, Listener, year, month, day);
        DatePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
            URI = data.getData();
            img_user.setImageURI(URI);

        }
    }

    private boolean vaildDataEmail() {
        String x = ed_email.getEditText().getText().toString().trim();
        if (x.isEmpty()) {
            ed_email.setError("This can't be empty  ♣");
            return false;
        } else {
            ed_email.setError(null);
            ed_email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean vaildUserName() {
        String x = ed_user_name.getEditText().getText().toString().trim();
        if (x.isEmpty()) {
            ed_user_name.setError("This can't be empty  ♣");
            return false;
        }  else {
            ed_user_name.setError(null);
            ed_user_name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean vaildfullName() {
        String x = ed_full_name.getEditText().getText().toString().trim();
        if (x.isEmpty()) {
            ed_full_name.setError("This can't be empty  ♣");
            return false;
        } else {
            ed_full_name.setError(null);
            ed_full_name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean vaildPassword() {
        String x = ed_Password.getEditText().getText().toString().trim();
        if (x.isEmpty()) {
            ed_Password.setError("This can't be empty  ♣");
            return false;
        } else if (x.length() < 8) {
            ed_Password.setError("Password not save  ♣");
            return false;
        } else {
            ed_Password.setError(null);
            ed_Password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean vaildPhone() {
        String x = ed_Phone.getEditText().getText().toString().trim();
        if (x.isEmpty()) {
            ed_Phone.setError("This can't be empty  ♣");
            return false;
        } else if (x.length() < 10) {
            ed_Phone.setError("Phone number is wrong  ♣");
            return false;
        } else {
            ed_Phone.setError(null);
            ed_Phone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean vaildemail() {
        String x = ed_email.getEditText().getText().toString().trim();
        if (x.isEmpty()) {
            ed_email.setError("This can't be empty  ♣");
            return false;
        } else {
            ed_email.setError(null);
            ed_email.setErrorEnabled(false);
            return true;
        }
    }

    public void confirmInput(View V) {
        if (!vaildDataEmail() | !vaildemail() | !vaildfullName() | !vaildfullName() | !vaildPassword() | !vaildPhone() | !vaildUserName()) {
            return;
        }
    }
}
//!♣M♥♥l♥!MM§5§♥U▬☻☻▬▐╬«╬U☻▬▬