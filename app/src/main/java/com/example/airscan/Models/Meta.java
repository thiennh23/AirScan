package com.example.airscan.Models;

import com.google.gson.annotations.SerializedName;

public class Meta {
    @SerializedName("readOnly")
    private boolean readOnly;
    @SerializedName("label")
    private String label;

    @SerializedName("storeDataPoints")
    private boolean storeDataPoints;
    @SerializedName("ruleState")
    private boolean ruleState;


    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isStoreDataPoints() {
        return storeDataPoints;
    }

    public void setStoreDataPoints(boolean storeDataPoints) {
        this.storeDataPoints = storeDataPoints;
    }

    public boolean isRuleState() {
        return ruleState;
    }

    public void setRuleState(boolean ruleState) {
        this.ruleState = ruleState;
    }

}
