package com.example.airscan.Chart;

import com.google.gson.annotations.SerializedName;

public class AssetDataPointBody {
    @SerializedName("fromTimestamp")
    private long fromTimestamp;
    @SerializedName("toTimestamp")
    private long toTimestamp;
    @SerializedName("type")
    private String type;
    @SerializedName("amountOfPoints")
    private int amountOfPoints;

    public AssetDataPointBody(
            long fromTimestamp,
            long toTimestamp,
            String type, int amountOfPoints
    ) {
        this.fromTimestamp = fromTimestamp;
        this.toTimestamp = toTimestamp;
        this.type = type;
        this.amountOfPoints = amountOfPoints;
    }
}