package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.Asset;
import com.example.airscan.Models.LightAttributes;
import com.example.airscan.Models.LightData;
import com.example.airscan.Models.WeatherAttributes;
import com.example.airscan.Models.WeatherData;
import com.example.airscan.Others.APIClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LightAssetActivity extends AppCompatActivity {
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_asset);apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Asset> call = apiInterface.getAsset("6iWtSbgqMQsVq8RPkJJ9vo");
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset as = response.body();
                Gson gson = new Gson();
                String json = gson.toJson(as.attributes);
                LightAttributes lightObj = gson.fromJson(json, LightAttributes.class);

                json = gson.toJson(lightObj.brightness);
                LightData windsobj = gson.fromJson(json, LightData.class);

                json = gson.toJson(lightObj.colourTemperature);
                LightData temobj = gson.fromJson(json, LightData.class);

                json = gson.toJson(lightObj.email);
                LightData humidobj = gson.fromJson(json, LightData.class);

                json = gson.toJson(lightObj.onOff);
                LightData abc = gson.fromJson(json, LightData.class);

                TextView tv = findViewById(R.id.tvweaid1);
                tv.setText(as.id.toString());

                TextView tv2 = findViewById(R.id.tvweatemper1);
                tv2.setText(windsobj.getValue());
                TextView tv3 = findViewById(R.id.tvweahumi1);
                tv3.setText(temobj.getValue());
                TextView tv4= findViewById(R.id.tvweawinddi1);
                tv4.setText(humidobj.getValue());
                TextView tv5 = findViewById(R.id.tvweawindspeed1);
                tv5.setText(abc.getValue());
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });
    }
}