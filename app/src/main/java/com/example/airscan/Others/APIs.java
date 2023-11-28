package com.example.airscan.Others;

import com.example.airscan.Interfaces.APIInterface;
import com.google.gson.JsonObject;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class APIs {

    private static final APIClient apiClient = new APIClient();
    private static final APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);;

    public static void updatePassword(String id, JsonObject query) {
        Call<String> call = apiInterface.updatePassword(id, query);

        int returnCode = -1;
        try {
            Response<String> response = call.execute();
            returnCode = response.code();
        } catch (IOException e) { e.printStackTrace(); }

    }
}
