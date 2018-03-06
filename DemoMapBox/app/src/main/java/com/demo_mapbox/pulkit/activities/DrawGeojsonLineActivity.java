package com.demo_mapbox.pulkit.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo_mapbox.pulkit.navigation.NavigationLauncher;
import com.demo_mapbox.pulkit.R;
import com.demo_mapbox.pulkit.styles.StyleCycle;
import com.mapbox.directions.v5.DirectionsCriteria;
import com.mapbox.directions.v5.MapboxDirections;
import com.mapbox.directions.v5.models.DirectionsResponse;
import com.mapbox.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;
import com.mapbox.services.android.telemetry.location.LostLocationEngine;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;
import com.mapbox.services.commons.models.Position;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DrawGeojsonLineActivity extends AppCompatActivity implements
        LocationEngineListener
        , PermissionsListener
        , Callback<DirectionsResponse> {

    private MapView navigate_map_view = null;
    private MapboxMap mMap = null;

    private MapboxNavigation mapboxNavigation;
    private final String DEFAULT_MAPBOX_ACCESS_TOKEN =
            "pk.eyJ1IjoiYXBwc2RldiIsImEiOiJjamFucnEzZGs0N3BvMzNud3J1ZXFqbTBwIn0.95B_r_uGlx2G6d45Tw8N5Q";

    private StyleCycle styleCycle = new StyleCycle();

    private PermissionsManager permissionsManager;
    private LocationLayerPlugin locationPlugin;
    private LocationEngine locationEngine;

    private Marker originMarker, destinationMarker;
    private LatLng originlatLng;
    private LatLng destinationlatLng;
    private Location originLocation;
    private Icon icon;

    private List<DirectionsRoute> routes = new ArrayList<>();
    private NavigationMapRoute navigationMapRoute;
    private DirectionsRoute currentRoute;

    private Point originPosition;
    private Point destinationPosition;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapboxNavigation = new MapboxNavigation(this, DEFAULT_MAPBOX_ACCESS_TOKEN);
        setContentView(R.layout.activity_draw_geojson_line);

        findIds();
        init(savedInstanceState);

    }

    private void findIds() {

        navigate_map_view = (MapView) findViewById(R.id.navigate_map_view);
        button = findViewById(R.id.startButton);
    }

    private void init(Bundle savedInstanceState) {

        navigate_map_view.onCreate(savedInstanceState);

        navigate_map_view.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                mMap = mapboxMap;

                // Set map style
//                setStyle(mapboxMap);

//                /*start Navigation*/
//                startNavigation();

                /*Enable Location Permission*/
                enableLocationPlugin();

                /*when a user clicks on the map and then add a marker to the map.*/
                clickOnMapToAddMarker(mapboxMap);


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Point origin = originPosition;
                Point destination = destinationPosition;

            }
        });
    }

    private void clickOnMapToAddMarker(final MapboxMap mapboxMap) {

        originlatLng = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());

        mapboxMap.setOnMapClickListener(new MapboxMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng point) {

                if (destinationMarker != null) {
                    mapboxMap.removeMarker(destinationMarker);
                }

                destinationlatLng = point;

                destinationMarker = mapboxMap.addMarker(new MarkerOptions()
                        .position(destinationlatLng));

                destinationPosition = Point.fromLngLat(
                        destinationlatLng.getLongitude(), destinationlatLng.getLatitude());
                originPosition = Point.fromLngLat(
                        originlatLng.getLongitude(), originlatLng.getLatitude());
                getRoute(originPosition, destinationPosition);

            }
        });


        mapboxMap.setOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng point) {

//                if (destinationMarker != null) {
//                    mapboxMap.removeMarker(destinationMarker);
//                }
//
//                destinationMarker = mapboxMap.addMarker(new MarkerOptions()
//                        .position(point));
//
//                Point originPoint = Point.fromLngLat(
//                        originMarker.getPosition().getLongitude(),
//                        originMarker.getPosition().getLatitude());
//                Point destinationPoint = Point.fromLngLat(
//                        destinationMarker.getPosition().getLongitude(),
//                        destinationMarker.getPosition().getLatitude());
//
//                getRoute(originPoint, destinationPoint);

//                mapboxMap.removeMarker(originMarker);
//                mapboxMap.removeMarker(destinationMarker);
            }
        });

    }

    private void getRoute(Point originPoint, Point destinationPoint) {

//                MapboxDirections directions = MapboxDirections.builder()
//                .origin(originPoint)
//                .destination(destinationPoint)
//                .accessToken(Mapbox.getAccessToken())
//                .profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC)
//                .build();
//
//        directions.enqueueCall(this);

        NavigationRoute.builder()
                .origin(originPoint)
                .destination(destinationPoint)
                .accessToken(Mapbox.getAccessToken())
                .profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC)
                .annotations(DirectionsCriteria.ANNOTATION_CONGESTION)
                .alternatives(true)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                        if (response.body() != null && !response.body().routes().isEmpty()) {

                            currentRoute = response.body().routes().get(0);

                            if (navigationMapRoute != null) {
                                navigationMapRoute.removeRoute();
                            } else {
                                navigationMapRoute = new NavigationMapRoute(
                                        null, navigate_map_view, mMap, R.style.NavigationMapRoute);
                            }

                            navigationMapRoute.addRoute(currentRoute);

                        } else if (response.body().routes().size() < 1) {
                            Log.e("TAG", "No routes found");
                            return;
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Timber.e(throwable);
                    }
                });
    }

    @Override
    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
        if (response.body() != null && !response.body().routes().isEmpty()) {
            List<DirectionsRoute> routeList = response.body().routes();
            routes = routeList;
        }
    }

    @Override
    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
        Timber.e(throwable);
    }

    private void startNavigation() {

        Point origin = Point.fromLngLat(-77.03613, 38.90992);
        Point destination = Point.fromLngLat(-77.07458, 38.90894);

        String awsPoolId = null;
        boolean simulateRoute = true;

        NavigationLauncher.startNavigation(DrawGeojsonLineActivity.this,
                origin, destination, awsPoolId, simulateRoute);
    }

    /*For Location Permission*/
    @SuppressWarnings({"MissingPermission"})
    private void enableLocationPlugin() {

        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Create an instance of LOST location engine
            initializeLocationEngine();

            locationPlugin = new LocationLayerPlugin(navigate_map_view, mMap, locationEngine);
            locationPlugin.setLocationLayerEnabled(LocationLayerMode.TRACKING);

        } else {

            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);

        }
    }

    /*For Location Permission*/
    @SuppressWarnings({"MissingPermission"})
    private void initializeLocationEngine() {

        locationEngine = new LostLocationEngine(this);
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
            setCameraPosition(lastLocation);

        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    private void setCameraPosition(Location location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 13));
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            /*For Location Permission*/
            enableLocationPlugin();

        } else {
            finish();
        }
    }

    /*For Remove updates of Location */
    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onConnected() {
        locationEngine.removeLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            originLocation = location;
            setCameraPosition(location);

            locationEngine.removeLocationEngineListener(this);
        }
    }

    private void setStyle(MapboxMap mapboxMap) {

        mapboxMap.setStyleUrl(Style.LIGHT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /*For Request Location Updates*/
    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();

        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStart();
        }
        navigate_map_view.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigate_map_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigate_map_view.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStop();
        }
        navigate_map_view.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        navigate_map_view.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigate_map_view.onDestroy();

        mapboxNavigation.endNavigation();
        mapboxNavigation.onDestroy();

        if (locationEngine != null) {
            locationEngine.deactivate();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        navigate_map_view.onSaveInstanceState(outState);
    }

}
