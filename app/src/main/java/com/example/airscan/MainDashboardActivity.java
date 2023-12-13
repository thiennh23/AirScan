package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDashboardActivity extends AppCompatActivity {
    APIInterface apiInterface;
    TextView tvuser;
    ImageButton ibMap, ibSetting, ibReport, ibDevice, ibLight;
    TextView tvMap, tvSetting, tvReport, tvDevice, tvLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindashboard);
        tvuser = findViewById(R.id.tvUser);
        ibMap = findViewById(R.id.imageButton2);
        tvMap = findViewById(R.id.tvMap);

        ibSetting = findViewById(R.id.btnSetting);
        tvSetting = findViewById(R.id.tvSetting);

        ibReport = findViewById(R.id.btnReport);
        tvReport = findViewById(R.id.tvReport);

        ibDevice = findViewById(R.id.btnDevice);
        tvDevice = findViewById(R.id.tvDevice);

        ibLight = findViewById(R.id.lightassettv);
        tvLight = findViewById(R.id.lightassettv1);


        //Light Asset
        ibLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(MainDashboardActivity.this, ViewAssetActivity.class);
                intent.putExtra("ASSET_ID", "6iWtSbgqMQsVq8RPkJJ9vo");  // PUT THE ID ASSET
                startActivity(intent);
            }
        });

        tvLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(MainDashboardActivity.this, ViewAssetActivity.class);
                intent.putExtra("ASSET_ID", "6iWtSbgqMQsVq8RPkJJ9vo");  // PUT THE ID ASSET
                startActivity(intent);
            }
        });

        //DEVICE weather
        ibDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(MainDashboardActivity.this, ViewAssetActivity.class);
                intent.putExtra("ASSET_ID", "5zI6XqkQVSfdgOrZ1MyWEf");  // PUT THE ID ASSET
                startActivity(intent);
            }
        });

        tvDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(MainDashboardActivity.this, ViewAssetActivity.class);
                intent.putExtra("ASSET_ID", "5zI6XqkQVSfdgOrZ1MyWEf");  // PUT THE ID ASSET
                startActivity(intent);
            }
        });

        //CHART
        ibReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDashboardActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });

        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDashboardActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });

        //Setting
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDashboardActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDashboardActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });


        //MAP
        tvMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDashboardActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDashboardActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }
}