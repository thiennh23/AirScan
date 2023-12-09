package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.Asset;
import com.example.airscan.Models.DatabaseHandler;
import com.example.airscan.Models.LightAttributes;
import com.example.airscan.Models.LightData;
import com.example.airscan.Models.ViewAsset;
import com.example.airscan.Models.WeatherAttributes;
import com.example.airscan.Models.WeatherData;
import com.example.airscan.Others.APIClient;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAssetActivity extends AppCompatActivity {
    private ArrayList<ViewAsset> mAssets ;
    private RecyclerView mRecyclerHero;
    private ViewAssetAdapter mAssetAdapter ;
    APIInterface apiInterface;
    DatabaseHandler database = new DatabaseHandler(this, "TESTDATA", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_asset);

        //FOR LIGHT ASSET
        database.QueryData("CREATE TABLE IF NOT EXISTS Test2 (\n" +
                "    Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    brightness INTEGER,\n" +
                "    colourTemperature INTEGER,\n" +
                "    onOff INTEGER,\n" +
                "    colourRGB TEXT\n" +
                ");");

        //FOR WEATHER ASSET
        database.QueryData("CREATE TABLE IF NOT EXISTS Test3(\n" +
                "    Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    Name TEXT,\n" +
                "    Humidity DOUBLE,\n" +
                "    Manufacturer TEXT,\n" +
                "    Place TEXT,\n" +
                "    Rainfall DOUBLE,\n" +
                "    altitude DOUBLE,\n" +
                "    azimuth DOUBLE,\n" +
                "    irradiance DOUBLE,\n" +
                "    zenith DOUBLE,\n" +
                "    Temperature DOUBLE,\n" +
                "    UVindex DOUBLE,\n" +
                "    WindDirection DOUBLE,\n" +
                "    WindSpeed DOUBLE\n" +
                ")");


        //Receive data (ASSET ID ON THE MARKER)
        Intent intent = getIntent();
        String assetId = intent.getStringExtra("ASSET_ID");
        TextView tv2 = findViewById(R.id.name);
        tv2.setText(assetId);

        //SETUP RECYCLERVIEW, ADAPTER, CLASS
        mRecyclerHero = findViewById(R.id.recyclerAssetView);
        mAssets = new ArrayList<>();
        if ("6iWtSbgqMQsVq8RPkJJ9vo".equals(assetId))
            CallAssetLight(assetId);
        else if ("5zI6XqkQVSfdgOrZ1MyWEf".equals(assetId))
             CallAssetWeather(assetId);
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
                tv2.setText(as.name);
                mAssets.add(new ViewAsset("ASSET ID", as.id,R.drawable.idicon));

                Gson gson = new Gson();

                //TURN INTO ASSET ATTRIBUTE
                String json = gson.toJson(as.attributes);
                WeatherAttributes attrObj = gson.fromJson(json, WeatherAttributes.class);


                //WIND SPEED
                json = gson.toJson(attrObj.windSpeed);
                WeatherData windSpeed = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("WIND SPEED (km/h)", CheckNull(windSpeed.getValue()),R.drawable.windspeed));

                //WIND DIRECTION
                json = gson.toJson(attrObj.windDirection);
                WeatherData windDirection= gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("WIND DIRECTION", CheckNull(windDirection.getValue()),R.drawable.winddirection));

                //TEMPERATURE
                json = gson.toJson(attrObj.temperature);
                WeatherData tempurature = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("TEMPERATURE (°C)", CheckNull(tempurature.getValue()),R.drawable.temperature));

                //HUMIDITY
                json = gson.toJson(attrObj.humidity);
                WeatherData humidity = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("HUMIDITY (%)", CheckNull(humidity.getValue()),R.drawable.humidity));


                //RAINFALL
                json = gson.toJson(attrObj.rainfall);
                WeatherData rainfall = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("RAIN FALL (mm)", CheckNull(rainfall.getValue()),R.drawable.humidity));
                //CHUA CO HINH

                //MANUFACTURER
                json = gson.toJson(attrObj.manufacturer);
                WeatherData manufacturer = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("MANUFACTURER", CheckNull(manufacturer.getValue()),R.drawable.humidity));
                //CHUA CO HINH

                //PLACE
                json = gson.toJson(attrObj.place);
                WeatherData place = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("PLACE", CheckNull(place.getValue()),R.drawable.humidity));
                //CHUA CO HINH

                //SUN AZIMUTH
                json = gson.toJson(attrObj.sunAzimuth);
                WeatherData sunAzimuth = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("SUN AZIMUTH", CheckNull(sunAzimuth.getValue()),R.drawable.humidity));

                //SUN ALTITUDE
                json = gson.toJson(attrObj.sunAltitude);
                WeatherData sunAltitude = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("SUN ALTITUDE", CheckNull(sunAltitude.getValue()),R.drawable.mintem));

                //SUN IRRADIANCE
                json = gson.toJson(attrObj.sunIrradiance);
                WeatherData sunIrradiance = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("SUN IRRADIANCE", CheckNull(sunIrradiance.getValue()),R.drawable.maxtem));

                //SUN ZENITH
                json = gson.toJson(attrObj.sunZenith);
                WeatherData sunZenith = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("SUN ZENITH", CheckNull(sunZenith.getValue()),R.drawable.soil));

                //UV INDEX
                json = gson.toJson(attrObj.uVIndex);
                WeatherData uVIndex = gson.fromJson(json, WeatherData.class);
                mAssets.add(new ViewAsset("UV INDEX", CheckNull(uVIndex.getValue()),R.drawable.sealevel));


                //LOAD TO RECYCLER VIEW
                RecyclerViewAsset();

                //LOAD INTO DATABASE
                double humidityValue = checkNullDouble(humidity.getValue());
                double rainfallValue = checkNullDouble(rainfall.getValue());
                double altitudeValue = checkNullDouble(sunAltitude.getValue());
                double azimuthValue = checkNullDouble(sunAzimuth.getValue());
                double irradianceValue = checkNullDouble(sunIrradiance.getValue());
                double zenithValue = checkNullDouble(sunZenith.getValue());
                double temperatureValue = checkNullDouble(tempurature.getValue());
                double uvIndexValue = checkNullDouble(uVIndex.getValue());
                double windDirectionValue = checkNullDouble(windDirection.getValue());
                double windSpeedValue = checkNullDouble(windSpeed.getValue());



                String insertSql = "INSERT INTO Test3 (Name, Humidity, Manufacturer, Place, Rainfall, altitude, azimuth, irradiance, zenith, Temperature, UVindex, WindDirection, WindSpeed) VALUES (" +
                        "'" + as.name + "', " +
                        humidityValue + ", " +
                        "'" + CheckNull(manufacturer.getValue()) + "', " +
                        "'" + CheckNull(place.getValue()) + "', " +
                        rainfallValue + ", " +
                        altitudeValue + ", " +
                        azimuthValue + ", " +
                        irradianceValue + ", " +
                        zenithValue + ", " +
                        temperatureValue + ", " +
                        uvIndexValue + ", " +
                        windDirectionValue + ", " +
                        windSpeedValue + ")";
                database.QueryData(insertSql);

                Cursor dataget = database.GetData("SELECT * FROM Test3");
                while (dataget.moveToNext()){
                    Log.d("THIEN", dataget.getString(0));
                    Log.d("THIEN", dataget.getString(1));
                    Log.d("THIEN", dataget.getString(2));
                    Log.d("THIEN", dataget.getString(3));
                    Log.d("THIEN", dataget.getString(4));
                    Log.d("THIEN", dataget.getString(5));
                    Log.d("THIEN", dataget.getString(6));
                    Log.d("THIEN", dataget.getString(7));
                    Log.d("THIEN", dataget.getString(8));
                    Log.d("THIEN", dataget.getString(9));
                    Log.d("THIEN", dataget.getString(10));
                    Log.d("THIEN", dataget.getString(11));
                    Log.d("THIEN", dataget.getString(12));
                    Log.d("THIEN", dataget.getString(13));
                }

            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });
    }
    void CallAssetLight(String id){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Asset> call = apiInterface.getAsset(id);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset as = response.body();
                //SET NAME OF THE ASSET DEVICE
                TextView tv2 = findViewById(R.id.name);
                tv2.setText(as.name);
                mAssets.add(new ViewAsset("ASSET ID", as.id,R.drawable.idicon));

                //TURN ASSET INTO ATTRIBUTES
                Gson gson = new Gson();
                String json = gson.toJson(as.attributes);
                LightAttributes lightObj = gson.fromJson(json, LightAttributes.class);


                //BRIGHTNESS (%)
                json = gson.toJson(lightObj.brightness);
                LightData brightness = gson.fromJson(json, LightData.class);
                mAssets.add(new ViewAsset("BRIGHTNESS (%)", CheckNull(brightness.getValue()),R.drawable.windspeed));

                //COLOUR TEMPERATURE (K)
                json = gson.toJson(lightObj.colourTemperature);
                LightData colourTemperature = gson.fromJson(json, LightData.class);
                mAssets.add(new ViewAsset("COLOUR TEMPERATURE (K)", CheckNull(colourTemperature.getValue()),R.drawable.winddirection));

                //ON OF
                json = gson.toJson(lightObj.onOff);
                LightData onOff = gson.fromJson(json, LightData.class);
                mAssets.add(new ViewAsset("DEVICE ON/OFF", CheckNull(onOff.getValue()),R.drawable.temperature));

                //COLOUR RGB
                json = gson.toJson(lightObj.colourRGB);
                LightData colourRGB = gson.fromJson(json, LightData.class);
                mAssets.add(new ViewAsset("COLOUR RGB", CheckNull(colourRGB.getValue()),R.drawable.humidity));

                //EMAIL
                json = gson.toJson(lightObj.email);
                LightData email = gson.fromJson(json, LightData.class);
                mAssets.add(new ViewAsset("EMAIL)", CheckNull(email.getValue()),R.drawable.humidity));
                //CHUA CO HINH

                //LOAD TO RECYCLER VIEW
                RecyclerViewAsset();


                //LOAD INTO DATABASE
                double bright = Double.parseDouble(CheckNull(brightness.getValue()));
                double clTemp = Double.parseDouble(CheckNull(colourTemperature.getValue()));
                int ONOFF = checkOnOff(CheckNull(onOff.getValue()));
                //Log.d("THIEN", String.format("%1$.2f", bright));

                // Your modified SQL query
                String insertQuery = "INSERT INTO Test2 (brightness, colourTemperature, onOff, colourRGB) VALUES (" +
                        bright + ", " +
                        clTemp + ", '" +
                        ONOFF + "', '" +
                        CheckNull(colourRGB.getValue()) + "')";

                database.QueryData(insertQuery);

                Cursor dataget = database.GetData("SELECT * FROM Test2");
                while (dataget.moveToNext()){
                    Log.d("THIEN", dataget.getString(0));
                    Log.d("THIEN", dataget.getString(1));
                    Log.d("THIEN", dataget.getString(2));
                    Log.d("THIEN", dataget.getString(3));
                    Log.d("THIEN", dataget.getString(4));
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });
    }

    void RecyclerViewAsset(){
        mAssetAdapter = new ViewAssetAdapter(ViewAssetActivity.this,mAssets);
        mRecyclerHero.setAdapter(mAssetAdapter);
        mRecyclerHero.setLayoutManager(new LinearLayoutManager(ViewAssetActivity.this));
    }

    String CheckNull(String string){
        if (string == null)
            return "NULL";
        else return string;
    }

    //Check on off of the light asset
    int checkOnOff(String onOff){
        if (onOff.equals("true"))
            return 1;
        else return 0;
    }

    double checkNullDouble(String str) {
        if (str != null) {
            // Check if the string is not empty after trimming
            String trimmedStr = str.trim();
            if (!trimmedStr.isEmpty()) {
                try {
                    return Double.parseDouble(trimmedStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
        }
        return 0; // Return a default value or handle the error case
    }



}