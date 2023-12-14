package com.example.airscan.Interfaces;

import com.example.airscan.Chart.AssetDataPoint;
import com.example.airscan.Chart.AssetDataPointBody;
import com.example.airscan.Models.Asset;
import com.example.airscan.Models.Map;
import com.example.airscan.Models.Token;
import com.example.airscan.Models.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("api/master/asset/{assetID}")
    Call<Asset> getAsset(@Path("assetID") String assetID);//, @Header("Authorization") String auth);

    @POST("auth/realms/master/protocol/openid-connect/token")
    @FormUrlEncoded
    Call<Token> login(
            @Field("client_id") String clientId,
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType
    );

    // Update user's password
    @Headers("Content-Type: application/json")
    @PUT("api/master/user/master/reset-password/{userId}")
    Call<String> updatePassword(@Path("userId") String userId, @Body JsonObject body);

    //GET MAP
    @GET("api/master/map")
    Call<Map> getMap();

    //USER IN4
    @GET("api/master/user/user")
    Call<User> getUser(); // Add the return type for getUserAsset

    //Asset Datapoint
    @POST("api/master/asset/datapoint/{assetID}/attribute/{assetAttribute}")
    Call<List<AssetDataPoint>> getListDataPoint(
            @Path("assetID") String assetID,
            @Path("assetAttribute") String assetAttribute,
            @Body AssetDataPointBody body);
}
