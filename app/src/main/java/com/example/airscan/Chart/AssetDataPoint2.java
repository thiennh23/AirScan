package com.example.airscan.Chart;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Others.APIClient;

public class AssetDataPoint2 {
    private APIInterface apiInterface;

    public AssetDataPoint2(Context context) {
        this.apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public LiveData<List<AssetDataPoint>> getListDataPoint(
            String assetID,
            String assetAttribute,
            AssetDataPointBody body
    ) {
        MutableLiveData<List<AssetDataPoint>> data = new MutableLiveData<>();
        Call<List<AssetDataPoint>> call = apiInterface.getListDataPoint(assetID, assetAttribute, body);
        call.enqueue(new Callback<List<AssetDataPoint>>() {
            @Override
            public void onResponse(Call<List<AssetDataPoint>> call, Response<List<AssetDataPoint>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else  {
                    Log.e("API CALL", "Request failed with code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<AssetDataPoint>> call, Throwable t) {
                // Handle network error
                Log.e("API CALL", "Request failed with code: " +  t.getMessage());
            }
        });
        return data;
    }
}
