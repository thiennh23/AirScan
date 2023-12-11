package com.example.airscan;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airscan.Models.DatabaseHandler;
import com.example.airscan.Models.ViewAsset;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import java.util.List;

public class GraphViewActivity extends AppCompatActivity {

    private ArrayList<ViewAsset> mAssets;
    private RecyclerView mRecyclerHero;
    private ViewAssetAdapter mAssetAdapter;
    private DatabaseHandler database = new DatabaseHandler(this, "TESTDATA", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        // Get the GraphView instance from the layout
        GraphView graphView = findViewById(R.id.graph);

        // Fetch humidity data from the WeatherAsset table
        List<DataPoint> humidityData = fetchHumidityData();

        // Convert DataPoint list to array
        DataPoint[] dataPoints = humidityData.toArray(new DataPoint[0]);

        // Create a LineGraphSeries from the data points
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

        // Add the series to the graph
        graphView.addSeries(series);
    }

    private List<DataPoint> fetchHumidityData() {
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
}



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
