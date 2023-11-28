package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        //84f6fd5e-83b0-4689-951b-1d26b0c1950d
        //Click to textview LOGIN
        TextView LoginTV = findViewById(R.id.LoginTV);
        LoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

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

        //SHOW PASSWORD
        CheckBox showpass = findViewById(R.id.showpass);
        // Set up a listener for the CheckBox
        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPass(showpass, pwd);
                showPass(showpass, rePwd);
            }
        });
    }

    public void showPass(CheckBox showpass, EditText pass)
    {
        // Save cursor position
        int cursorPosition = pass.getSelectionStart();
        // Get the current left drawables
        Drawable leftDrawable = pass.getCompoundDrawables()[0];
        if (showpass.isChecked()) {
            // If the CheckBox is checked, show the password
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pass.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, getResources().getDrawable(R.drawable.visibility), null);
        } else {
            // If the CheckBox is unchecked, hide the password
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, getResources().getDrawable(R.drawable.visibility_off), null);
        }
        // Restore cursor position
        pass.setSelection(cursorPosition);
    }


}