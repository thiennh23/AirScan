package com.example.airscan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LightAttributes {
   /* @SerializedName("notes")
    @Expose
    public LightData notes;*/

    @SerializedName("brightness")
    @Expose
    public LightData brightness;

    @SerializedName("colourTemperature")
    @Expose
    public LightData colourTemperature;

    @SerializedName("location")
    @Expose
    public Object location;

    @SerializedName("colourRGB")
    @Expose
    public LightData colourRGB;

    @SerializedName("email")
    @Expose
    public LightData email;

    /*@SerializedName("tags")
    @Expose
    public LightData tags;*/

    @SerializedName("onOff")
    @Expose
    public LightData onOff;

    //GETTER SETTER

    public LightData getBrightness() {
        return brightness;
    }

    public void setBrightness(LightData brightness) {
        this.brightness = brightness;
    }

    public LightData getColourTemperature() {
        return colourTemperature;
    }

    public void setColourTemperature(LightData colourTemperature) {
        this.colourTemperature = colourTemperature;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public LightData getColourRGB() {
        return colourRGB;
    }

    public void setColourRGB(LightData colourRGB) {
        this.colourRGB = colourRGB;
    }

    public LightData getEmail() {
        return email;
    }

    public void setEmail(LightData email) {
        this.email = email;
    }

    public LightData getOnOff() {
        return onOff;
    }

    public void setOnOff(LightData onOff) {
        this.onOff = onOff;
    }
}
