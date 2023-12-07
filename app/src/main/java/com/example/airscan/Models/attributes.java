package com.example.airscan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class attributes {

    @SerializedName("sunIrradiance")
    @Expose
    public WeatherData sunIrradiance;

    @SerializedName("rainfall")
    @Expose
    public WeatherData rainfall;

    @SerializedName("uVIndex")
    @Expose
    public WeatherData uVIndex;

    @SerializedName("sunAzimuth")
    @Expose
    public WeatherData sunAzimuth;

    @SerializedName("sunZenith")
    @Expose
    public WeatherData sunZenith;

    @SerializedName("manufacturer")
    @Expose
    public WeatherData manufacturer;

    @SerializedName("temperature")
    @Expose
    public WeatherData temperature;

    @SerializedName("humidity")
    @Expose
    public WeatherData humidity;

    @SerializedName("location")
    @Expose
    public Object location;

    @SerializedName("place")
    @Expose
    public WeatherData place;

    @SerializedName("windDirection")
    @Expose
    public WeatherData windDirection;

    @SerializedName("windSpeed")
    @Expose
    public WeatherData windSpeed;

    @SerializedName("sunAltitude")
    @Expose
    public WeatherData sunAltitude;

    //GETTER SETTER

}
