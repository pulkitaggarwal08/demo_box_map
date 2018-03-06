package com.demo_mapbox.pulkit.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo_mapbox.pulkit.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class ShowMapCustomInfActivity extends AppCompatActivity {

    private MapView map_view = null;
    private MapboxMap mMap = null;

    private Marker marker;
    private Icon icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(),
                "pk.eyJ1IjoiYXBwc2RldiIsImEiOiJjamFucnEzZGs0N3BvMzNud3J1ZXFqbTBwIn0.95B_r_uGlx2G6d45Tw8N5Q");
        setContentView(R.layout.activity_show_custom_info);

        findIds();
        init(savedInstanceState);

    }

    private void findIds() {

        map_view = (MapView) findViewById(R.id.map_view);
    }

    private void init(Bundle savedInstanceState) {

        map_view.onCreate(savedInstanceState);

        map_view.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMap = mapboxMap;

                // The easiest way to add a marker view
                addMarker(mapboxMap);

                // Set map style
                setStyle(mapboxMap);

                //Set InfoWindowAdapter
                infoWindowAdapter(mapboxMap);

            }
        });
    }

    private void addMarker(MapboxMap mapboxMap) {

        icon = IconFactory.getInstance(ShowMapCustomInfActivity.this).fromResource(R.drawable.purple_marker);

        // restore map to previous location and zoom level, if any, or to default values
        float zoomLevel = 15;
        double lat = 30.713081;
        double lng = 76.688554;

        /*29.682707, 76.990146*/

        marker = mapboxMap.addMarker(new MarkerViewOptions()
                .position(new LatLng(lat, lng))
                .rotation(0)
                .icon(icon)
                .anchor(0.5f, 0.5f)
                .alpha(0.5f)
                .title(getString(R.string.current_location))
//                .snippet(getString(R.string.current_location))
                .infoWindowAnchor(0.5f, 0.5f)
                .flat(true));

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

        mapboxMap.setStyleUrl(Style.DARK);
    }

    private void infoWindowAdapter(MapboxMap mapboxMap) {

        mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {

                LinearLayout linearLayout = new LinearLayout(ShowMapCustomInfActivity.this);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                ImageView countryFlagImage = new ImageView(ShowMapCustomInfActivity.this);

                if (TextUtils.equals(marker.getTitle(), getString(R.string.custom_window_marker_title_spain))) {
                    countryFlagImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flag_of_spain));

                } else if (TextUtils.equals(marker.getTitle(), getString(R.string.custom_window_marker_title_egypt))) {
                    countryFlagImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flag_of_egypt));

                } else {
                    countryFlagImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flag_of_germany));
                }

                // Set the size of the image
                countryFlagImage.setLayoutParams(new ViewGroup.LayoutParams(150, 100));

                // add the image view to the parent layout
                linearLayout.addView(countryFlagImage);

                return linearLayout;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        map_view.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map_view.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        map_view.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map_view.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map_view.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map_view.onSaveInstanceState(outState);
    }


}
