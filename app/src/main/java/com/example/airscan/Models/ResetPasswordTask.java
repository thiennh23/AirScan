package com.example.airscan.Models;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.airscan.Changepassok;
import com.example.airscan.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResetPasswordTask extends AsyncTask<String, Void, Integer> {

    private static final String TAG = "ResetPasswordTask";
    private static final String BASE_URL = "https://uiot.ixxc.dev/api/master/user/master/reset-password/";

    private String userId;
    private String dfaultPassword;
    private String token;
    private Context context;

    public ResetPasswordTask(String userId, String dfaultPassword, String token, Context context) {
        this.userId = userId;
        this.dfaultPassword = dfaultPassword;
        this.token = token;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String urlString = BASE_URL + userId;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);

            // Set request headers
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            // Create JSON body
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("type", "string");
            jsonBody.put("value", dfaultPassword);
            jsonBody.put("temporary", true);

            // Write the JSON body to the request
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(jsonBody.toString());
            writer.flush();
            writer.close();
            outputStream.close();
            // Get the response
            int responseCode = connection.getResponseCode();
            return responseCode;
        } catch (IOException | JSONException e) {
            Log.e("THIENTEST", "Error: " + e.getMessage());
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer responseCode) {
        Log.d("THIENTEST", "Response Code: " + responseCode);

        if (responseCode >= 200 && responseCode < 300) {
            // Response code is in the 2xx range, indicating success
            // Change to MainActivity2
            Log.e(TAG, "Success with HTTP status code: " + responseCode);
            Intent intent = new Intent(context, Changepassok.class);
            intent.putExtra("userId", userId);
            context.startActivity(intent);
        } else {
            // Show an error message or handle the failure case
            Toast.makeText(context, "Can not login! Check your USERID again.", Toast.LENGTH_SHORT).show();
            Log.e("THIENTEST", "Request failed with HTTP error status: " + responseCode);
        }
    }
}