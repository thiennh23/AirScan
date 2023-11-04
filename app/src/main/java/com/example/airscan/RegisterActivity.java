package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button back;
    private Button signUp;
    private EditText username;
    private EditText email;
    private EditText pwd;
    private EditText rePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Back button
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //Input data
        username = findViewById(R.id.edt_username);
        email = findViewById(R.id.edt_email);
        pwd = findViewById(R.id.edt_password);
        rePwd = findViewById(R.id.edt_rePassword);
        signUp = findViewById(R.id.btn_register);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoadingScreen.class);
                intent.putExtra("username", username.getText().toString().trim());
                intent.putExtra("email", email.getText().toString().trim());
                intent.putExtra("password", pwd.getText().toString().trim());
                intent.putExtra("rePassword", rePwd.getText().toString().trim());

                startActivity(intent);
            }
        });
    }

}