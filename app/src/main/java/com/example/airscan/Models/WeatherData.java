package com.example.airscan.Models;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private String value;
    @SerializedName("name")
    private String name;
    @SerializedName("meta")
    private Meta meta;
    @SerializedName("timestamp")
    private long timestamp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
