package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.Asset;
import com.example.airscan.Models.User;
import com.example.airscan.Models.ViewAsset;
import com.example.airscan.Models.WeatherAttributes;
import com.example.airscan.Models.WeatherData;
import com.example.airscan.Others.APIClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDashboardActivity extends AppCompatActivity {
    APIInterface apiInterface;
    TextView tvusername;
    ImageButton ibMap, ibSetting, ibReport, ibDevice, ibLight, ibGraph;
    TextView tvMap, tvSetting, tvReport, tvDevice, tvLight, tvGraph;
    TextView tvhumi, tvtemp, tvrainfall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindashboard);
        tvusername = findViewById(R.id.tvUser);
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

        ibGraph = findViewById(R.id.graphbtn);
        tvGraph = findViewById(R.id.graphtv);

        tvhumi = findViewById(R.id.humi);
        tvtemp = findViewById(R.id.temp);
        tvrainfall = findViewById(R.id.rainfall);


        CallAssetWeather("5zI6XqkQVSfdgOrZ1MyWEf");

        //GRAPH SQLITE
        tvGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDashboardActivity.this, GraphViewActivity.class);
                startActivity(intent);
            }
        });

        ibGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDashboardActivity.this, GraphViewActivity.class);
                startActivity(intent);
            }
        });


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

    void CallAssetWeather(String id){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Asset> call = apiInterface.getAsset(id);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset as = response.body();
                //SET NAME OF THE ASSET DEVICE
                TextView tv2 = findViewById(R.id.name);
                Gson gson = new Gson();

                //TURN INTO ASSET ATTRIBUTE
                String json = gson.toJson(as.attributes);
                WeatherAttributes attrObj = gson.fromJson(json, WeatherAttributes.class);

                //TEMPERATURE
                json = gson.toJson(attrObj.temperature);
                WeatherData tempurature = gson.fromJson(json, WeatherData.class);

                //HUMIDITY
                json = gson.toJson(attrObj.humidity);
                WeatherData humidity = gson.fromJson(json, WeatherData.class);

                //RAINFALL
                json = gson.toJson(attrObj.rainfall);
                WeatherData rainfall = gson.fromJson(json, WeatherData.class);

                tvhumi.setText(humidity.getValue() + "%");
                tvtemp.setText(tempurature.getValue() + "°C");
                tvrainfall.setText(rainfall.getValue() + "mm");
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });
    }
}