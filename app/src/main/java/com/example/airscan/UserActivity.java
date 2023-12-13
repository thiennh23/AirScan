package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.User;
import com.example.airscan.Others.APIClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    APIInterface apiInterface;
    TextView tvname, email, firstname, lastname, userID, service, createDay;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        tvname = findViewById(R.id.tv1);
        email = findViewById(R.id.tv2);
        firstname = findViewById(R.id.tv3);
        lastname = findViewById(R.id.tv4);
        userID = findViewById(R.id.tv5);
        createDay = findViewById(R.id.tv6);
        service = findViewById(R.id.tv7);
        logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                // If you want to clear the back stack, add the following flags:
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                APIClient.Usertoken = "";
                startActivity(intent);
                finish(); // Finish the current activity to prevent the user from going back
            }
        });

        Call<User> call1 = apiInterface.getUser();
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call1, Response<User> response1) {

                //Log.d ("API CALL", response.toString());
                User user = response1.body();
                tvname.setText(user.username); //Du lieu tra ve
                email.setText(user.email); //Du lieu tra ve
                firstname.setText(user.firstName); //Du lieu tra ve
                lastname.setText(user.lastName); //Du lieu tra ve
                userID.setText(user.id); //Du lieu tra ve
                service.setText(ServiceAcc(user.serviceAccount)); //Du lieu tra ve
                createDay.setText(convertTimestampToString(user.createdOn)); //Du lieu tra ve
            }

            @Override
            public void onFailure(Call<User> call1, Throwable t) {
                //t.printStackTrace();
            }
        });
    }

    public String ServiceAcc(boolean a)
    {
        if (a == true)
            return "YES";
        else return "NO";
    }

    public static String convertTimestampToString(String timestamp) {
        try {
            // Convert the string timestamp to a long value
            long timestampLong = Long.parseLong(timestamp);

            // Create a Date object using the timestamp
            Date date = new Date(timestampLong);

            // Define the desired date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            // Format the date and return the result
            return dateFormat.format(date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Invalid Timestamp";
        }
    }

}