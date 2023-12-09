package com.example.airscan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class location {
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @SerializedName("value")
    @Expose
    public Object value;
}
