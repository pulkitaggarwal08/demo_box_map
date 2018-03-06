package com.demo_mapbox.pulkit.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.demo_mapbox.pulkit.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;

import java.util.List;

public class TrafficActivity extends AppCompatActivity {

    private MapView trafficMapView;
    private FloatingActionButton trafficToggleFab;

    private MapboxMap map;
    private TrafficPlugin trafficPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext()
                , "pk.eyJ1IjoiYXBwc2RldiIsImEiOiJjamFucnEzZGs0N3BvMzNud3J1ZXFqbTBwIn0.95B_r_uGlx2G6d45Tw8N5Q");
        setContentView(R.layout.activity_traffic);

        findIds();
        init();

    }

    private void findIds() {

        trafficMapView = findViewById(R.id.mapView);
        trafficToggleFab = findViewById(R.id.building_toggle_fab);
    }

    private void init() {

        trafficMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                map = mapboxMap;

                // The easiest way to add a marker view
//                addMarker(map);

                // Set map style
//                setStyle(map);

                trafficPlugin = new TrafficPlugin(trafficMapView, map);

                // Enable the traffic view by default
                trafficPlugin.setVisibility(true);
            }
        });

        trafficToggleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(map != null) {
                    trafficPlugin.setVisibility(!trafficPlugin.isVisible());
                }
            }
        });
    }

    private void addMarker(MapboxMap mapboxMap) {

        Icon icon = IconFactory.getInstance(TrafficActivity.this).fromResource(R.drawable.red_marker);

        // restore map to previous location and zoom level, if any, or to default values
        float zoomLevel = 15;
        double lat = 30.713081;
        double lng = 76.688554;

        Marker marker = mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .icon(icon)
                .title(getString(R.string.current_location)));
//                .snippet(getString(R.string.current_location))

        // Set the camera's starting position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng)) // set the camera's center position
                .zoom(zoomLevel)  // set the camera's zoom level
                .tilt(0)  // set the camera's tilt
                .build();

        // Move the camera to that position
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setStyle(MapboxMap mapboxMap) {

        mapboxMap.setStyleUrl(Style.LIGHT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        trafficMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        trafficMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        trafficMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        trafficMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        trafficMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trafficMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        trafficMapView.onSaveInstanceState(outState);
    }

}
