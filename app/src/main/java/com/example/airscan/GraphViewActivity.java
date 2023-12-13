package com.example.airscan;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.airscan.Models.DatabaseHandler;
import com.example.airscan.Models.ViewAsset;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphViewActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM ");

    private ArrayList<ViewAsset> mAssets;
    private RecyclerView mRecyclerHero;
    private ViewAssetAdapter mAssetAdapter;
    private DatabaseHandler database = new DatabaseHandler(this, "TESTDATA", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        drawLineChart();
        drawPieChart();
    }

    //THIS IS THE PIE CHART AND LIGHT TURN ON/OFF FROM THE DATABASE
    private void drawPieChart() {
        PieChart pieChart = findViewById(R.id.piechart);

        // Fetching data from the database
        Cursor cursor = database.GetData("SELECT COUNT(*) AS total, SUM(onOff) AS turnedOn FROM Test2");

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int totalDevices = cursor.getInt(cursor.getColumnIndex("total"));
            @SuppressLint("Range") int turnedOnDevices = cursor.getInt(cursor.getColumnIndex("turnedOn"));
            int turnedOffDevices = totalDevices - turnedOnDevices;

            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();

            // Calculate percentages
            float percentageTurnedOn = (float) turnedOnDevices * 100 / totalDevices;
            float percentageTurnedOff = (float) turnedOffDevices * 100 / totalDevices;

            // Add entries to the pie chart
            pieEntries.add(new PieEntry(percentageTurnedOn, "Turned On"));
            pieEntries.add(new PieEntry(percentageTurnedOff, "Turned Off"));

            // Define colors
            int colorTurnedOn = Color.rgb(42, 157, 143);
            int colorTurnedOff = Color.rgb(231, 111, 81); // Red

            colors.add(colorTurnedOn);
            colors.add(colorTurnedOff);

            // Create pie data set
            PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
            pieDataSet.setColors(colors);
            pieDataSet.setValueTextSize(18f); // Set text size for percentages
            pieDataSet.setValueTextColor(Color.BLACK); // Set text color for percentages

            // Create pie data
            PieData pieData = new PieData(pieDataSet);
            pieData.setValueFormatter(new PercentFormatter());
            pieData.setDataSet(pieDataSet);

            // Configure pie chart
            pieChart.getDescription().setEnabled(false);
            pieChart.setData(pieData);
            pieChart.invalidate();
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }
    }

    private List<Entry> getDataSet(String query, int columnIndex) {
        List<Entry> lineEntries = new ArrayList<>();

        // Fetching data from the database
        Cursor cursor = database.GetData(query);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Assuming the column index 0 is for Id (you may want to use this for x-coordinate)
                long id = cursor.getLong(0);

                // Use the specified column index for humidity value
                double humidityValue = cursor.getDouble(columnIndex);

                lineEntries.add(new Entry(id, (float) humidityValue));
            }
            cursor.close();
        }
        return lineEntries;
    }
    private void drawLineChart() {
        LineChart lineChart = findViewById(R.id.linechart);

        // Create multiple data sets
        List<ILineDataSet> dataSets = new ArrayList<>();

        // Add the first data set
        List<Entry> lineEntries1 = getDataSet("SELECT * FROM Test3", 2);
        LineDataSet lineDataSet1 = createLineDataSet(lineEntries1, "Humidity", Color.RED);
        dataSets.add(lineDataSet1);

        // Add the second data set
        List<Entry> lineEntries2 = getDataSet("SELECT * FROM Test3", 10);
        LineDataSet lineDataSet2 = createLineDataSet(lineEntries2, "Temperature", Color.BLUE);
        dataSets.add(lineDataSet2);

        // Add the 3rd data set
        List<Entry> lineEntries3 = getDataSet("SELECT * FROM Test3", 5);
        LineDataSet lineDataSet3 = createLineDataSet(lineEntries3, "RainFall", Color.YELLOW);
        dataSets.add(lineDataSet3);

        // Add the 4 data set
        List<Entry> lineEntries4 = getDataSet("SELECT * FROM Test3", 13);
        LineDataSet lineDataSet4 = createLineDataSet(lineEntries4, "WindSpeed", Color.BLACK);
        dataSets.add(lineDataSet4);

        // Create the LineData object
        LineData lineData = new LineData(dataSets);

        // Configure the line chart
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(1.0f);
        lineChart.getXAxis().setLabelCount(lineEntries1.size()); // Assuming all data sets have the same size
        lineChart.setData(lineData);
    }
    private LineDataSet createLineDataSet(List<Entry> entries, String label, int color) {
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(color);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.DKGRAY);

        return lineDataSet;
    }




    //THIS IS THE LINE CHART AND HUMIDITY FROM DATABASE
    /*private void drawLineChart() {
        LineChart lineChart = findViewById(R.id.linechart);
        List<Entry> lineEntries = getDataSet();
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Humidity");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.YELLOW);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(lineDataSet);
        //lineChart.getDescription().setText("Price in last 12 days");
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(1.0f);
        lineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
        lineChart.setData(lineData);
    }
    private List<Entry> getDataSet() {
        List<Entry> lineEntries = new ArrayList<>();

        // Fetching humidity data from the database
        Cursor cursor = database.GetData("SELECT * FROM Test3");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Assuming the column index 2 is for Humidity (adjust based on your schema)
                double humidityValue = cursor.getDouble(2);

                // Assuming the column index 0 is for Id (you may want to use this for x-coordinate)
                long id = cursor.getLong(0);

                lineEntries.add(new Entry(id, (float) humidityValue));
            }
            cursor.close();
        }
        return lineEntries;
    }*/

}




    /*private List<DataPoint> fetchHumidityData() {
        List<DataPoint> humidityData = new ArrayList<>();

        // Fetching humidity data from the database
        Cursor cursor = database.GetData("SELECT * FROM Test3");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Assuming the column index 2 is for Humidity (adjust based on your schema)
                double humidity = cursor.getDouble(2);

                // Assuming the column index 0 is for Id (you may want to use this for x-coordinate)
                long id = cursor.getLong(0);

                humidityData.add(new DataPoint(id, humidity));
            }
            cursor.close();
        }

        return humidityData;
    }

    private List<DataPoint> fetchTemperatureData() {
        List<DataPoint> temperatureData = new ArrayList<>();

        // Fetching humidity data from the database
        Cursor cursor = database.GetData("SELECT * FROM Test3");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Assuming the column index 2 is for Humidity (adjust based on your schema)
                double temperature = cursor.getDouble(10);

                // Assuming the column index 0 is for Id (you may want to use this for x-coordinate)
                long id = cursor.getLong(0);

                temperatureData.add(new DataPoint(id, temperature));
            }
            cursor.close();
        }
        return temperatureData;
    }*/




/*
public class GraphViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        GraphView graph = findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        graph.addSeries(series);

    }
}*/
