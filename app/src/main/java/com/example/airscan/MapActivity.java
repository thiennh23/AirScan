package com.example.airscan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.Asset;
import com.example.airscan.Models.Default;
import com.example.airscan.Models.Map;
import com.example.airscan.Models.Options;
import com.example.airscan.Models.MapAttribute;
import com.example.airscan.Models.location;
import com.example.airscan.Models.value;
import com.example.airscan.Others.APIClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapActivity extends AppCompatActivity {

    APIInterface apiInterface;
    private MapView mapView;
    private IMapController mapController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the user agent
        Configuration.getInstance().setUserAgentValue("com.example.airscan");

        // Set up the map view
        MapView map = new MapView(this);
        map.setTileSource(TileSourceFactory.MAPNIK);

        setContentView(R.layout.activity_map);

        Button btntest = findViewById(R.id.BtnTest);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });


        SearchView searchView = findViewById(R.id.location);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            List<Address> addressList = null;
            @Override
            public boolean onQueryTextSubmit(String query) {

                try {
                    String location = searchView.getQuery().toString();
                    Marker marker2 = new Marker(mapView);
                    if(location.equals("Weather Asset") || location.equals("Weather Asset 2") || location.contains("Weather Asset 1")|| location.contains("Weather Asset 2"))
                    {
                        if(location.contains("1"))
                        {
                            Call<Asset> call = apiInterface.getAsset("5zI6XqkQVSfdgOrZ1MyWEf");
                            call.enqueue(new Callback<Asset>() {
                                @Override
                                public void onResponse(Call<Asset> call, Response<Asset> response) {
                                    Asset asset= response.body();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(asset.attributes);
                                    MapAttribute attrObj = gson.fromJson(json, MapAttribute.class);
                                    json = gson.toJson(attrObj.location);
                                    location locobj = gson.fromJson(json, com.example.airscan.Models.location.class);
                                    json = gson.toJson(locobj.value);
                                    value vlueobj = gson.fromJson(json, value.class);
                                    json = gson.toJson(vlueobj.coordinates);
                                    Float longitude4 = vlueobj.coordinates[0];
                                    Float latitude4 = vlueobj.coordinates[1];
                                    Geocoder geocoder = new Geocoder(MapActivity.this);
                                    try {
                                        addressList = geocoder.getFromLocationName(location , 1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    //Address address = addressList.get(0);
                                    GeoPoint geoPoint = new GeoPoint(latitude4,longitude4);
                                    marker2.setPosition(geoPoint);
                                    mapView.getController().animateTo(geoPoint);
                                    searchView.getQueryHint();
                                    searchView.clearFocus();
                                    searchView.onActionViewExpanded();


                                }

                                @Override
                                public void onFailure(Call<Asset> call, Throwable t) {

                                }
                            });
                        }
                        else if (location.contains("2"))
                        {
                            Call<Asset> call2 = apiInterface.getAsset("6iWtSbgqMQsVq8RPkJJ9vo");
                            call2.enqueue(new Callback<Asset>() {
                                @Override
                                public void onResponse(Call<Asset> call, Response<Asset> response) {
                                    Asset asset= response.body();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(asset.attributes);
                                    MapAttribute attrObj = gson.fromJson(json, MapAttribute.class);
                                    json = gson.toJson(attrObj.location);
                                    location locobj = gson.fromJson(json, location.class);
                                    json = gson.toJson(locobj.value);
                                    value vlueobj = gson.fromJson(json, value.class);
                                    json = gson.toJson(vlueobj.coordinates);
                                    Float longitude4 = vlueobj.coordinates[0];
                                    Float latitude4 = vlueobj.coordinates[1];
                                    Geocoder geocoder = new Geocoder(MapActivity.this);
                                    try {
                                        addressList = geocoder.getFromLocationName(location , 1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    //Address address = addressList.get(0);
                                    GeoPoint geoPoint = new GeoPoint(latitude4,longitude4);
                                    marker2.setPosition(geoPoint);
                                    mapView.getController().animateTo(geoPoint);
                                    searchView.getQueryHint();
                                    searchView.clearFocus();
                                    searchView.onActionViewExpanded();
                                }

                                @Override
                                public void onFailure(Call<Asset> call, Throwable t) {
                                }
                            });
                        }
                    }
                    else if (location != null && !location.equals("")) {
                        Geocoder geocoder = new Geocoder(MapActivity.this);
                        try {
                            // Attempt to get address from the provided location
                            addressList = geocoder.getFromLocationName(location, 1);

                            // Check if addressList is not empty
                            if (!addressList.isEmpty()) {
                                Address address = addressList.get(0);
                                GeoPoint gpoint = new GeoPoint(address.getLatitude(), address.getLongitude());

                                // Set up marker icon
                                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.markersearching, null);
                                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                                Drawable dr = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, (int) (18.0f * getResources().getDisplayMetrics().density), (int) (18.0f * getResources().getDisplayMetrics().density), true));

                                // Configure and add the marker to the map
                                marker2.setIcon(dr);
                                mapView.getOverlays().add(marker2);
                                marker2.setPosition(gpoint);
                                marker2.setTitle(location);
                                mapView.getController().animateTo(gpoint);

                                // Additional actions with searchView if needed
                                searchView.getQueryHint();
                                searchView.clearFocus();
                                searchView.onActionViewExpanded();

                                // Set marker click listener
                                marker2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                                        // Create a BottomSheetDialog
                                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapActivity.this, R.style.BottomSheetDialogTheme);

                                        // Inflate the bottom sheet layout
                                        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet3, (LinearLayout) findViewById(R.id.bottomSheetContainer));

                                        // Set TextViews with marker details
                                        TextView tv = (TextView) bottomSheetView.findViewById(R.id.detailname1);
                                        tv.setText("Name: " + location.toString());

                                        TextView tv2 = (TextView) bottomSheetView.findViewById(R.id.detailtype1);
                                        tv2.setText("Type: Searching");

                                        TextView tv3 = (TextView) bottomSheetView.findViewById(R.id.detaillocation1);
                                        tv3.setText("Longitude: " + address.getLongitude() + "\nLatitude: " + address.getLatitude());

                                        // Set the content view for the BottomSheetDialog
                                        bottomSheetDialog.setContentView(bottomSheetView);
                                        bottomSheetDialog.show();
                                        // Set click listener for the delete button in the bottom sheet
                                        bottomSheetView.findViewById(R.id.buttondelete).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                // Remove the marker from the map and dismiss the bottom sheet
                                                marker2.remove(mapView);
                                                bottomSheetDialog.cancel();
                                                searchView.clearFocus();
                                            }
                                        });

                                        return true;
                                    }
                                });
                            } else {
                                // Handle the case when addressList is empty
                                // For example, show a message to the user or handle it according to your app's logic
                                Toast.makeText(MapActivity.this, "No address found for the given location", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Handle the case when location is null or empty
                        // For example, show a message to the user or handle it according to your app's logic
                        Toast.makeText(MapActivity.this, "Invalid location", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                } catch (Exception ex) {
                    throw ex;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    return false;
                } catch (Exception ex) {
                    throw ex;
                }
            }
        });
        apiInterface = APIClient.getClient().create(APIInterface.class);
//        NavigationView nv = findViewById(R.id.navi);
//        nv.setNavigationItemSelectedListener(this);
        Call<Map> mapCall = apiInterface.getMap();
        mapCall.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                Map map = response.body();
                Gson gson = new Gson();
                String json = gson.toJson(map.options);
                Options optionsObj = gson.fromJson(json, Options.class);
                json = gson.toJson(optionsObj._default);
                Default defaultObj = gson.fromJson(json, Default.class);
                Double zoom = defaultObj.zoom;
                Float longitude = defaultObj.center[0];
                Float latitude = defaultObj.center[1];
                GeoPoint startPoint = new GeoPoint(latitude, longitude);
                mapView = findViewById(R.id.Map);
                mapController = mapView.getController();
                mapController.setCenter(startPoint);
                mapView.setTileSource(TileSourceFactory.MAPNIK);
                mapView.setMultiTouchControls(true);
                mapController.setZoom(18);

                Marker marker = new Marker(mapView);
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.markerapp, null);
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                Drawable dr = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, (int) (19.0f * getResources().getDisplayMetrics().density), (int) (19.0f * getResources().getDisplayMetrics().density), true));
                marker.setIcon(dr);
                marker.setPosition(startPoint);
                mapView.getOverlays().add(marker);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapActivity.this, R.style.BottomSheetDialogTheme);
                        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet2, (LinearLayout) findViewById(R.id.bottomSheetContainer));
                        TextView tv = (TextView) bottomSheetView.findViewById(R.id.detailname1);
                        tv.setText("Name: UIT ");
                        TextView tv2 = (TextView) bottomSheetView.findViewById(R.id.detailtype1);
                        tv2.setText("Type: Center Location");
                        TextView tv3 = (TextView) bottomSheetView.findViewById(R.id.detaillocation1);
                        tv3.setText("Longitude: " + defaultObj.center[0]+ "\nLatitude: " +defaultObj.center[1]);
                        TextView tv4 = (TextView) bottomSheetView.findViewById(R.id.detailversion1);
                        tv4.setText("Version: "+ map.version.toString());
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.show();
                        return true;
                    }
                });
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                t.printStackTrace();
            }
        });
        ImageView img = (ImageView) findViewById(R.id.btn_zoom_in);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.canZoomIn();
                mapController.zoomIn();
            }
        });
        ImageView img3 = (ImageView) findViewById(R.id.btn_zoom_out);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.canZoomOut();
                mapController.zoomOut();
            }
        });


        //ZOOM TAN NOI LUON
        ImageView img4 = (ImageView) findViewById(R.id.btn_my_location);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = APIClient.getClient().create(APIInterface.class);
//        NavigationView nv = findViewById(R.id.navi);
//        nv.setNavigationItemSelectedListener(this);
                Call<Map> mapCall = apiInterface.getMap();
                mapCall.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        Map map = response.body();
                        Gson gson = new Gson();
                        String json = gson.toJson(map.options);
                        Options optionsObj = gson.fromJson(json, Options.class);
                        json = gson.toJson(optionsObj._default);
                        Default defaultObj = gson.fromJson(json, Default.class);
                        Double zoom = defaultObj.zoom;
                        Float longitude = defaultObj.center[0];
                        Float latitude = defaultObj.center[1];
                        GeoPoint startPoint = new GeoPoint(latitude, longitude);
                        mapView = findViewById(R.id.Map);
                        mapController = mapView.getController();
                        mapController.setCenter(startPoint);
                        mapView.setTileSource(TileSourceFactory.MAPNIK);
                        mapView.setMultiTouchControls(true);
                        mapController.setZoom(18);
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        Call<Asset> call2 = apiInterface.getAsset("5zI6XqkQVSfdgOrZ1MyWEf");
        call2.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                try {
                    Asset asset = response.body();
                    Gson gson = new Gson();
                    String json = gson.toJson(asset.attributes);
                    MapAttribute attrObj = gson.fromJson(json, MapAttribute.class);
                    json = gson.toJson(attrObj.location);
                    location locobj = gson.fromJson(json, location.class);
                    json = gson.toJson(locobj.value);
                    value vlueobj = gson.fromJson(json, value.class);
                    json = gson.toJson(vlueobj.coordinates);
                    Float longitude4 = vlueobj.coordinates[0];
                    Float latitude4 = vlueobj.coordinates[1];
                    mapView = findViewById(R.id.Map);
                    mapView.setTileSource(TileSourceFactory.MAPNIK);
                    mapView.setMultiTouchControls(true);
                    mapController = mapView.getController();
                    mapController.setZoom(20);
                    GeoPoint startPoint4 = new GeoPoint(latitude4, longitude4);
                    Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.weathermarker, null);
                    Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                    Drawable dr = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, (int) (19.0f * getResources().getDisplayMetrics().density), (int) (19.0f * getResources().getDisplayMetrics().density), true));
                    Marker marker3 = new Marker(mapView);
                    marker3.setPosition(startPoint4);
                    mapView.getOverlays().add(marker3);
                    marker3.setIcon(dr);
                    marker3.getTextLabelBackgroundColor();
                    marker3.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker, MapView mapView) {

                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapActivity.this, R.style.BottomSheetDialogTheme);
                            //BottomSheetDialog bottomSheetDialog = findViewById(R.id.bottomSheetContainer);
                            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContainer));
                            TextView tv = (TextView) bottomSheetView.findViewById(R.id.detailname1);
                            tv.setText("Name: "+ asset.getName().toString());
                            TextView tv2 = (TextView) bottomSheetView.findViewById(R.id.detailtype1);
                            tv2.setText("Type: "+ asset.getType().toString());
                            TextView tv3 = (TextView) bottomSheetView.findViewById(R.id.detaillocation1);
                            tv3.setText("Longitude: " + vlueobj.coordinates[0]+ "\nLatitude: " + vlueobj.coordinates[1]);
                            TextView tv4 = (TextView) bottomSheetView.findViewById(R.id.detailversion1);
                            tv4.setText("Version: "+ asset.version.toString());
                            bottomSheetView.findViewById(R.id.buttondetail).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("THIEN", "DONT MAP");
                                    startActivity(new Intent(MapActivity.this, WeatherAssetActivity.class));
                                }
                            });
                            bottomSheetDialog.setContentView(bottomSheetView);
                            bottomSheetDialog.show();
                            return true;
                        }
                    });
                    marker3.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                } catch (Exception ex) {
                    throw ex;
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
            }
        });
        Call<Asset> call4 = apiInterface.getAsset("6iWtSbgqMQsVq8RPkJJ9vo");
        call4.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                try {
                    Asset asset = response.body();
                    Gson gson = new Gson();
                    String json = gson.toJson(asset.attributes);
                    MapAttribute attrObj = gson.fromJson(json, MapAttribute.class);
                    json = gson.toJson(attrObj.location);
                    location locobj = gson.fromJson(json, location.class);
                    json = gson.toJson(locobj.value);
                    value vlueobj = gson.fromJson(json, value.class);
                    json = gson.toJson(vlueobj.coordinates);
                    Float longitude4 = vlueobj.coordinates[0];
                    Float latitude4 = vlueobj.coordinates[1];
                    mapView = findViewById(R.id.Map);
                    mapView.setTileSource(TileSourceFactory.MAPNIK);
                    mapView.setMultiTouchControls(true);
                    mapController = mapView.getController();
                    mapController.setZoom(20);
                    GeoPoint startPoint4 = new GeoPoint(latitude4, longitude4);
                    Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.weathermarker, null);
                    Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                    Drawable dr = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, (int) (19.0f * getResources().getDisplayMetrics().density), (int) (19.0f * getResources().getDisplayMetrics().density), true));
                    Marker marker2 = new Marker(mapView);
                    marker2.setPosition(startPoint4);
                    mapView.getOverlays().add(marker2);
                    marker2.setIcon(dr);
                    marker2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker, MapView mapView) {
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapActivity.this, R.style.BottomSheetDialogTheme);
                            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContainer));
                            TextView tv = (TextView) bottomSheetView.findViewById(R.id.detailname1);
                            tv.setText("Name: "+ asset.getName().toString());
                            TextView tv2 = (TextView) bottomSheetView.findViewById(R.id.detailtype1);
                            tv2.setText("Type: "+ asset.getType().toString());
                            TextView tv3 = (TextView) bottomSheetView.findViewById(R.id.detaillocation1);
                            tv3.setText("Longitude: " + vlueobj.coordinates[0]+ "\nLatitude: " + vlueobj.coordinates[1]);
                            TextView tv4 = (TextView) bottomSheetView.findViewById(R.id.detailversion1);
                            tv4.setText("Version: "+ asset.version.toString());
                            bottomSheetView.findViewById(R.id.buttondetail).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(MapActivity.this, LightAssetActivity.class));
                                }
                            });
                            bottomSheetDialog.setContentView(bottomSheetView);
                            bottomSheetDialog.show();
                            return true;
                        }
                    });
                    marker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                } catch (Exception ex) {
                    throw ex;
                }
            }
            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
            }
        });

    }
}
