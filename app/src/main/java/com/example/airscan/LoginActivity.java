package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                    Log.i("Login", response.body().getAccess_token());

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
}