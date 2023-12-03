package com.example.airscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewByID
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);
        Button btn_resetPwd = findViewById(R.id.btn_resetPwd);

        //Language button
        ImageView language = findViewById(R.id.language);
        String currentLang = getResources().getConfiguration().locale.getLanguage();
        if (currentLang.equals("en")) {
            language.setImageResource(R.drawable.uk_flag);
        } else {
            language.setImageResource(R.drawable.vn_flag);
        }

        //When click language image view
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentLang = getResources().getConfiguration().locale.getLanguage();
                if (currentLang.equals("en")) {
                    setLocale("vi");
                } else {
                    setLocale("en");
                }
            }
        });


        //Switch to Login Activity
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });

        //Switch to Register Activity
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });

        //Change Password
        btn_resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetPassword = new Intent(getApplicationContext(), EnterUsrIDActivity.class);
                startActivity(resetPassword);
            }
        });
    }

    //SET LANGUAGE
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}

