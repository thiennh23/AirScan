package com.example.airsense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    Handler UIHandler = new Handler();
    EditText edt_username;
    EditText edt_password;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create popup noti if username or password is empty
                if (edt_username.getText().toString().isEmpty() || edt_password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không được để trống",Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread login_request_Thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL login_url;
                            HttpURLConnection con;
                            Map<String, String> parameters = new HashMap<>();
                            DataOutputStream out;

                            try {
                                login_url = new URL("https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token");
                                parameters.put("client_id","openremote");
                                parameters.put("username",edt_username.getText().toString());
                                parameters.put("password",edt_password.getText().toString());
                                parameters.put("grant_type","password");
                                con = (HttpURLConnection) login_url.openConnection();
                                con.setRequestMethod("POST");
                                con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                                Log.d("Thiendz","1");
                                con.setDoOutput(true);
                                StringBuilder result = new StringBuilder();
                                out = new DataOutputStream(con.getOutputStream());

                                for(Map.Entry<String,String> entry: parameters.entrySet()) {
                                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                                    result.append("=");
                                    result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
                                    result.append("&");
                                }
                                Log.d("Thiendz","2");
                                String resultString = result.toString();
                                resultString = resultString.substring(0,resultString.length()-1);
                                out.writeBytes(resultString);
                                Log.d("Thiendz",resultString);
                                out.flush();
                                out.close();
                                int status = con.getResponseCode();
                                Log.d("Thiendz","3");
                                UIHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("Thiendz","status code: "+ String.valueOf(status));
                                        Log.d("Thiendz","4");
                                    }
                                });
                            if (status == 200){
                                Intent ok = new Intent(getApplicationContext(),MainActivity2.class);
                                startActivity(ok);
                            }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    login_request_Thread.start();

                }
            }
        });



    }
}