package com.example.airscan.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AssetDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE1_NAME = "WeatherAsset";
    private static final String KEY1_ID = "Id";
    private static final String KEY1_NAME = "Name";
    private static final String KEY1_HUMIDITY = "Humidity";
    private static final String KEY1_MANUFACTURER = "Manufacturer";
    private static final String KEY1_PLACE = "Place";
    private static final String KEY1_RAINFALL = "Rainfall ";
    private static final String KEY1_SUNALTITUDE = "SunAltitude";
    private static final String KEY1_SUNAZIMUTH = "SunAzimuth";
    private static final String KEY1_SUNIRRADIANCE = "SunIrradiance";
    private static final String KEY1_SUNZENITH = "SunZenith";
    private static final String KEY1_TEMERATURE = "Temperature";
    private static final String KEY1_UVINDEX = "UVIndex ";
    private static final String KEY1_WINNDDIRECTION = "WindDirection";
    private static final String KEY1_WINDSPEED = "Wind Speed ";


    //Query but not return anything: CREATE, DROP, INSERT, ...
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //Query return a result: SELECT
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
