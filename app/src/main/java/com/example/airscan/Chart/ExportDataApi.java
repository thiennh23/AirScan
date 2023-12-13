package com.example.airscan.Chart;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class ExportDataApi {
    URL url;
    HttpURLConnection con;

    public static String full_url(String url, Map<String, String> query) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append("?");
        for (Map.Entry<String, String> entry : query.entrySet()) {
            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            builder.append("&");
        }
        String result = builder.toString();
        result = result.substring(0, result.length() - 1);

        return result;
    }

    public ExportDataApi(String url,
                         String method,
                         Map<String, String> query, String token) throws IOException {


        this.url = new URL(full_url(url, query));
        this.con = (HttpURLConnection) this.url.openConnection();
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Authorization", "Bearer ".concat(token));
        con.setRequestMethod(method);

    }

    public Map<Date, Float> GetData() throws IOException {
        InputStream in;
        int status = con.getResponseCode();
        if (status == 200)
            in = con.getInputStream();
        else
            in = con.getErrorStream();


        int length = con.getContentLength();
        byte[] raw_data = new byte[length];
        int byte_read = 0;
        while (byte_read < length) {
            byte_read += in.read(raw_data, byte_read, length-byte_read);
        }
        in.close();
        InputStream response_stream = new ByteArrayInputStream(raw_data);

        ZipInputStream zip = new ZipInputStream(response_stream);
        zip.getNextEntry();

        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        while (zip.available()==1) {
            writer.write(zip.read());
        }
        byte[] extract_raw_data = writer.toByteArray();

        InputStream extract_data_stream = new ByteArrayInputStream(extract_raw_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(extract_data_stream));

        StringBuilder builder = new StringBuilder();
        String line;
        Map<Date, Float> list = new HashMap<>();
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
            String[] elements = line.split(",");
            Calendar temp = Calendar.getInstance();
            try {
                temp.setTimeInMillis(Timestamp.valueOf(elements[0]).getTime() + (long) (3600 * 6 * 1000));
                list.put(temp.getTime(), Float.valueOf(elements[3]));
            }catch (IllegalArgumentException e) {
                Log.d("custom_error",e.toString());
                Log.d("custom_error",line);
                continue;
            }
        }
        return list;

    }
}
