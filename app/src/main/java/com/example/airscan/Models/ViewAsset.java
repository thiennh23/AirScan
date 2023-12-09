package com.example.airscan.Models;

public class ViewAsset {
    private String mName;
    private String mvalue;
    private int mImage;

    public ViewAsset(String mName, String mvalue, int mImage) {
        this.mName = mName;
        this.mvalue = mvalue;
        this.mImage = mImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getMvalue() {
        return mvalue;
    }

    public void setMvalue(String mvalue) {
        this.mvalue = mvalue;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }
}
