package com.example.airscan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherAttributes {

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

    public WeatherData getSunIrradiance() {
        return sunIrradiance;
    }

    public void setSunIrradiance(WeatherData sunIrradiance) {
        this.sunIrradiance = sunIrradiance;
    }

    public WeatherData getRainfall() {
        return rainfall;
    }

    public void setRainfall(WeatherData rainfall) {
        this.rainfall = rainfall;
    }

    public WeatherData getuVIndex() {
        return uVIndex;
    }

    public void setuVIndex(WeatherData uVIndex) {
        this.uVIndex = uVIndex;
    }

    public WeatherData getSunAzimuth() {
        return sunAzimuth;
    }

    public void setSunAzimuth(WeatherData sunAzimuth) {
        this.sunAzimuth = sunAzimuth;
    }

    public WeatherData getSunZenith() {
        return sunZenith;
    }

    public void setSunZenith(WeatherData sunZenith) {
        this.sunZenith = sunZenith;
    }

    public WeatherData getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(WeatherData manufacturer) {
        this.manufacturer = manufacturer;
    }

    public WeatherData getTemperature() {
        return temperature;
    }

    public void setTemperature(WeatherData temperature) {
        this.temperature = temperature;
    }

    public WeatherData getHumidity() {
        return humidity;
    }

    public void setHumidity(WeatherData humidity) {
        this.humidity = humidity;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public WeatherData getPlace() {
        return place;
    }

    public void setPlace(WeatherData place) {
        this.place = place;
    }

    public WeatherData getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(WeatherData windDirection) {
        this.windDirection = windDirection;
    }

    public WeatherData getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(WeatherData windSpeed) {
        this.windSpeed = windSpeed;
    }

    public WeatherData getSunAltitude() {
        return sunAltitude;
    }

    public void setSunAltitude(WeatherData sunAltitude) {
        this.sunAltitude = sunAltitude;
    }
}
