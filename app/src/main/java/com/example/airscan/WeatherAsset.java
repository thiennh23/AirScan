package com.example.airscan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.Asset;
import com.example.airscan.Models.WeatherData;
import com.example.airscan.Models.attributes;
import com.example.airscan.Others.APIClient;
import com.google.gson.Gson;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherAsset extends AppCompatActivity {
    APIInterface apiInterface;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM ");
  /*  LineGraphSeries<DataPoint> series = new LineGraphSeries<>( new DataPoint[0]);
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[0]);
    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[0]);

    GraphDatabaseHelper graphDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    DataGraph dataGraph;
    DataGraphAdapter dataGraphAdapter;
    GraphActivity graphActivity;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_asset);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Asset> call = apiInterface.getAsset("5zI6XqkQVSfdgOrZ1MyWEf");
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset as = response.body();
                Gson gson = new Gson();
                String json = gson.toJson(as.attributes);
                attributes attrObj = gson.fromJson(json, attributes.class);

                json = gson.toJson(attrObj.windSpeed);
                WeatherData windsobj = gson.fromJson(json, WeatherData.class);

                json = gson.toJson(attrObj.temperature);
                WeatherData temobj = gson.fromJson(json, WeatherData.class);

                json = gson.toJson(attrObj.humidity);
                WeatherData humidobj = gson.fromJson(json, WeatherData.class);

                json = gson.toJson(attrObj.windDirection);
                WeatherData winddiobj = gson.fromJson(json, WeatherData.class);

                TextView tv = findViewById(R.id.tvweaid1);
                tv.setText(as.id.toString());

                TextView tv2 = findViewById(R.id.tvweatemper1);
                tv2.setText(temobj.getValue().toString());

                TextView tv3 = findViewById(R.id.tvweahumi1);
                tv3.setText(humidobj.getValue().toString());
                TextView tv4= findViewById(R.id.tvweawinddi1);
                tv4.setText(winddiobj.getValue().toString());
                TextView tv5 = findViewById(R.id.tvweawindspeed1);
                tv5.setText(windsobj.getValue().toString());

                TextView tv6 = findViewById(R.id.tvweapressnext1);
                json = gson.toJson(attrObj.place);
                WeatherData Place = gson.fromJson(json, WeatherData.class);
                tv6.setText(Place.getValue().toString());


                TextView tv7 = findViewById(R.id.tvweamaxtempnext1);
                json = gson.toJson(attrObj.rainfall);
                WeatherData rainFall = gson.fromJson(json, WeatherData.class);
                tv7.setText(rainFall.getValue().toString());

                TextView tv8 = findViewById(R.id.tvweamintempnext1);
                tv8.setText("NULL");

                TextView tv9 = findViewById(R.id.tvweaseanext1);
                tv9.setText("NULL");

                TextView tv10 = findViewById(R.id.tvweagroundnext1);
                tv10.setText("NULL");
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });
        /*RelativeLayout rlt1 = findViewById(R.id.rtltemper1);
        rlt1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv1 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv1.setText("Weather Asset 1 Temperature");
                //graph = (GraphView) findViewById(R.id.graphdialog);
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series.resetData(getDataPoint());
                grphview.addSeries(series);
                series.setTitle("Temperature");
                series.setThickness(8);
                grphview.getLegendRenderer().setVisible(true);
                grphview.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview.getViewport().setXAxisBoundsManual(true);
                grphview.getViewport().setYAxisBoundsManual(true);
                grphview.getViewport().scrollToEnd();
                grphview.getViewport().setScalable(true);
                grphview.getViewport().setScalableY(true);
                grphview.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long)value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Temperature (Celsius)");
                gridLabel.setVerticalAxisTitleTextSize(30);
                return true;
            }
        });
        RelativeLayout rlt2 = findViewById(R.id.rtlhumidity1);
        rlt2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview2 =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv2 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv2.setText("Weather Asset 1 Humidity");
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series2.resetData(getDataPoint2());
                grphview2.addSeries(series2);
                series2.setTitle("Humidity");
                series2.setThickness(8);
                grphview2.getViewport().setXAxisBoundsManual(true);
                grphview2.getViewport().setYAxisBoundsManual(true);
                grphview2.getViewport().scrollToEnd();
                grphview2.getViewport().setScalable(true);
                grphview2.getViewport().setScalableY(true);
                grphview2.getLegendRenderer().setVisible(true);
                grphview2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview2.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long) value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview2.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Humidity (%)");
                gridLabel.setVerticalAxisTitleTextSize(30);
                return true;
            }
        });
        ImageView img= findViewById(R.id.backpic2);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ActivityWeatherData.super.onBackPressed();
            }
        });
        RelativeLayout rlt3 = findViewById(R.id.rtlwindspeed1);
        rlt3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview3 =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv3 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv3.setText("Weather Asset 1 Wind speed");
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series3.resetData(getDataPoint3());
                grphview3.addSeries(series3);
                series3.setTitle("Windspeed");
                series3.setThickness(8);
                grphview3.getLegendRenderer().setVisible(true);
                grphview3.getViewport().setXAxisBoundsManual(true);
                grphview3.getViewport().setYAxisBoundsManual(true);
                grphview3.getViewport().scrollToEnd();
                grphview3.getViewport().setScalable(true);
                grphview3.getViewport().setScalableY(true);
                grphview3.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview3.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long)value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview3.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Wind speed (km/h)");
                gridLabel.setVerticalAxisTitleTextSize(30);
                return true;
            }
        });
        RelativeLayout rltdirec = findViewById(R.id.rtlwinddirec1);
        rltdirec.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview4 =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv3 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv3.setText("Weather Asset 1 Wind direction");
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series4.resetData(getDataPoint4());
                grphview4.addSeries(series4);
                series4.setTitle("Wind direction");
                series4.setThickness(8);
                grphview4.getLegendRenderer().setVisible(true);
                grphview4.getViewport().setXAxisBoundsManual(true);
                grphview4.getViewport().setYAxisBoundsManual(true);
                grphview4.getViewport().scrollToEnd();
                grphview4.getViewport().setScalable(true);
                grphview4.getViewport().setScalableY(true);
                grphview4.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview4.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long)value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview4.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Wind direction");
                gridLabel.setVerticalAxisTitleTextSize(30);
                return true;
            }

        });
        RelativeLayout rltpress = findViewById(R.id.rtltemper7);
        rltpress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview5 =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv3 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv3.setText("Weather Asset 1 Pressure");
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series5.resetData(getDataPoint5());
                grphview5.addSeries(series5);
                series5.setTitle("Pressure");
                series5.setThickness(8);
                grphview5.getLegendRenderer().setVisible(true);
                grphview5.getViewport().setXAxisBoundsManual(true);
                grphview5.getViewport().setYAxisBoundsManual(true);
                grphview5.getViewport().scrollToEnd();
                grphview5.getViewport().setScalable(true);
                grphview5.getViewport().setScalableY(true);
                grphview5.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview5.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long)value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview5.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Pressure");
                gridLabel.setVerticalAxisTitleTextSize(30);
                return true;
            }
        });
        RelativeLayout rltmaxtemp = findViewById(R.id.rtlhumidity7);
        rltmaxtemp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview6 =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv3 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv3.setText("Weather Asset 1 Max temperature");
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series6.resetData(getDataPoint6());
                grphview6.addSeries(series6);
                series6.setTitle("Max temperature");
                series6.setThickness(8);
                grphview6.getViewport().setXAxisBoundsManual(true);
                grphview6.getViewport().setYAxisBoundsManual(true);
                grphview6.getViewport().scrollToEnd();
                grphview6.getViewport().setScalable(true);
                grphview6.getViewport().setScalableY(true);
                grphview6.getLegendRenderer().setVisible(true);
                grphview6.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview6.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long)value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview6.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Max temperature (Celsius)");
                return true;
            }
        });
        RelativeLayout rltmintemp = findViewById(R.id.rtlwinddirec4);
        rltmintemp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview7 =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv3 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv3.setText("Weather Asset 1 Min temperature");
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series7.resetData(getDataPoint7());
                grphview7.addSeries(series7);
                series7.setTitle("Min temperature");
                series7.setThickness(8);
                grphview7.getViewport().setXAxisBoundsManual(true);
                grphview7.getViewport().setYAxisBoundsManual(true);
                grphview7.getViewport().scrollToEnd();
                grphview7.getViewport().setScalable(true);
                grphview7.getViewport().setScalableY(true);
                grphview7.getLegendRenderer().setVisible(true);
                grphview7.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview7.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long)value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview7.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Min temperature (Celsius)");
                return true;
            }
        });
        RelativeLayout rltsea = findViewById(R.id.rtlwindspeed7);
        rltsea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview8 =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv3 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv3.setText("Weather Asset 1 Sea level");
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series8.resetData(getDataPoint8());
                grphview8.addSeries(series8);
                series8.setTitle("Sea level");
                series8.setThickness(8);
                grphview8.getViewport().setXAxisBoundsManual(true);
                grphview8.getViewport().setYAxisBoundsManual(true);
                grphview8.getViewport().scrollToEnd();
                grphview8.getViewport().setScalable(true);
                grphview8.getViewport().setScalableY(true);
                grphview8.getLegendRenderer().setVisible(true);
                grphview8.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview8.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long)value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview8.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Sea level");
                return true;
            }
        });
        RelativeLayout rltgrnd = findViewById(R.id.rtlid);
        rltgrnd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater li = LayoutInflater.from(WeatherAsset.this);
                View customDialogView = li.inflate(R.layout.custom_dialog, null);
                final AlertDialog alertDialogBuilder  = new AlertDialog.Builder(WeatherAsset.this).create();
                final GraphView grphview9 =  (GraphView) customDialogView.findViewById(R.id.graphdialog);
                alertDialogBuilder.setView(customDialogView);
                alertDialogBuilder.show();
                final TextView tv3 = (TextView) customDialogView.findViewById(R.id.titledialog);
                tv3.setText("Weather Asset 1 Ground level");
                graphDatabaseHelper = new GraphDatabaseHelper(WeatherAsset.this);
                sqLiteDatabase = graphDatabaseHelper.getWritableDatabase();
                series9.resetData(getDataPoint9());
                grphview9.addSeries(series9);
                series9.setTitle("Ground level");
                series9.setThickness(8);
                grphview9.getViewport().setXAxisBoundsManual(true);
                grphview9.getViewport().setYAxisBoundsManual(true);
                grphview9.getViewport().scrollToEnd();
                grphview9.getViewport().setScalable(true);
                grphview9.getViewport().setScalableY(true);
                grphview9.getLegendRenderer().setVisible(true);
                grphview9.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                grphview9.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    public String formatLabel(double value, boolean x){
                        if (x)
                        {
                            return sdf.format(new Date((long)value));
                        }else{
                            return super.formatLabel(value, x);
                        }
                    }
                });
                GridLabelRenderer gridLabel = grphview9.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Time");
                gridLabel.setHorizontalAxisTitleTextSize(30);
                gridLabel.setVerticalAxisTitle("Ground level");
                return true;
            }
        });
    }
*/}

   /* private DataPoint[] getDataPoint2(){
        String[] columns = {"KEY_HUMITIME","KEY_HUMIDITY"};
        Cursor cursor = graphDatabaseHelper.GetData("SELECT * FROM contacts WHERE name = 'Weather Asset'");
        DataPoint[] dp = new DataPoint[cursor.getCount()];
        //  if(cursor.getString(1)== "6H4PeKLRMea1L0WsRXXWp9") {
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getLong(6), cursor.getInt(5));
        }
        //}
        return dp;
    }
    private DataPoint[] getDataPoint(){
        String[] columns = {"KEY_TEMPTIMESTAMP","KEY_TEMPERATURE"};
        Cursor cursor = graphDatabaseHelper.GetData("SELECT * FROM contacts WHERE name = 'Weather Asset'");
        DataPoint[] dp = new DataPoint[cursor.getCount()];
        //  if(cursor.getString(1)== "6H4PeKLRMea1L0WsRXXWp9") {
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getLong(4), cursor.getDouble(3));
        }
        //  }
        return dp;
    }
    private DataPoint[] getDataPoint3(){
        String[] columns = {"KEY_WINDSTIME","KEY_WINDSPEED"};
        Cursor cursor = graphDatabaseHelper.GetData("SELECT * FROM contacts WHERE name = 'Weather Asset'");
        DataPoint[] dp = new DataPoint[cursor.getCount()];
        //  if(cursor.getString(1)== "6H4PeKLRMea1L0WsRXXWp9") {
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getLong(8), cursor.getInt(7));
        }
        //}
        return dp;
    }*/
}
