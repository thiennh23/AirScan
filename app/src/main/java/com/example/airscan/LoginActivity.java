package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.Token;
import com.example.airscan.Others.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    //Declare here, so that the function "getToken" can access the value of it
    APIInterface apiInterface;
    String grant_type = "password";
    String client_id = "openremote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FindViewByID
        apiInterface = APIClient.getClient().create(APIInterface.class);
        EditText edt_username = findViewById(R.id.edt_username);
        EditText edt_password = findViewById(R.id.edt_password);
        Button btn_login = findViewById(R.id.btn_login);
        TextView registerTV = findViewById(R.id.registerTV);


        //SHOW PASSWORD
        CheckBox showpass = findViewById(R.id.showpass);
        // Set up a listener for the CheckBox
        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPass(showpass, edt_password);
            }
        });



        //Back button
        Button back = findViewById(R.id.btnBack);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //Click on register text
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Convert username, password into string and clear EditText
                //edt_username.clearFocus();
               // edt_password.clearFocus();

                String usr = String.valueOf(edt_username.getText());
                String pwd = String.valueOf(edt_password.getText());
                getToken(usr, pwd);
            }
        });
    }

    public void getToken(String usr, String pwd)
    {
        Call<Token> call = apiInterface.login(client_id, usr, pwd, grant_type);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    assert response.body() != null;
                    Log.i("Login123", response.body().getAccess_token());
                    Token Token = response.body();
                    Log.i("Login123", response.toString());
                    APIClient.Usertoken = Token.access_token;
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Can not login!", Toast.LENGTH_SHORT).show();
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