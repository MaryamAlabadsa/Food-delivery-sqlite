package com.example.toc_test;

import android.content.SharedPreferences;
import android.widget.Button;

public class SharedPreferences_return {
    private static final String date_of_birth_key = "date_of_birth",
            user_name_key = "user_name",
            full_name_key = "full_name",
            email_key = "email",
            Password_key = "Password",
            Phone_key = "Phone",
            checkBox_key_remember = "checkBox_key_remember",
            checkbox_admin_key = "checkbox_admin_key",
            URI_key = "URI",
            spinner_key = "spinner";
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    public SharedPreferences_return(SharedPreferences sp) {
        this.sp = sp;
    }

    public String user_name() {
        return sp.getString(user_name_key, null);
    }

    public String Password() {
        return sp.getString(Password_key, null);
    }
    public int Phone() {
        return sp.getInt(Phone_key, 0);
    }
    public String address() {
        return sp.getString(spinner_key, null);
    }

    public String checkbox_remember_me() {
        return sp.getString("checkBox_key_remember", "false");
    }

    public String checkbox_admin() {
        return sp.getString(checkbox_admin_key, "false");
    }

}
