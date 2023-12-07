package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.Asset;
import com.example.airscan.Models.WeatherData;
import com.example.airscan.Models.attributes;
import com.example.airscan.Others.APIClient;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Asset> call = apiInterface.getAsset("5zI6XqkQVSfdgOrZ1MyWEf");
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset as = response.body();
                Gson gson = new Gson();
                String json = gson.toJson(as.attributes);
                attributes attrObj = gson.fromJson(json, attributes.class);

                json = gson.toJson(attrObj.humidity);//LINE NULL
                WeatherData humidobj = gson.fromJson(json, WeatherData.class);

                json = gson.toJson(attrObj.sunIrradiance);//LINE NULL
                WeatherData sunIrra = gson.fromJson(json, WeatherData.class);

                json = gson.toJson(attrObj.manufacturer);//LINE NULL
                WeatherData temp = gson.fromJson(json, WeatherData.class);




                TextView tv = findViewById(R.id.tv1);
                tv.setText(as.id.toString());
                TextView tv2 = findViewById(R.id.tv2);
                tv2.setText(humidobj.getValue().toString());
                TextView tv3 = findViewById(R.id.tv3);
                TextView tv4= findViewById(R.id.tv4);

                // Check if sunIrra.value is null before accessing it
                if (sunIrra.getValue() != null) {
                    tv3.setText(String.valueOf(sunIrra.getValue()));
                    Log.d("THIEN", "Value is NOT NULL: " + sunIrra.getType());
                } else {
                    Log.d("THIEN", "Value is NULL");
                    tv3.setText("No data");
                }

                tv4.setText(temp.getValue().toString());
                TextView textView5 = findViewById(R.id.tv5);
                json = gson.toJson(attrObj.rainfall);
                WeatherData rainFall = gson.fromJson(json, WeatherData.class);
                textView5.setText(rainFall.getValue());
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });


    }
}