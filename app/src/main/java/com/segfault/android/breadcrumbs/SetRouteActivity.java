package com.segfault.android.breadcrumbs;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SetRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Location mLastLocation;
    LocationRequest mLocationRequest;

    private FusedLocationProviderClient mFusedLocationClient;
    private Button mStartPointButton, mMiddlePointButton, mEndPointButton;
    private Button mSaveRouteButton;
    Boolean startPoint = true;
    Boolean middlePoint = false;
    Boolean endPoint = false;
    int trailPointCount = 1;
    List<Marker> mapMarkers = new ArrayList<Marker>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mStartPointButton = (Button) findViewById(R.id.set_start_point);
        mMiddlePointButton = (Button) findViewById(R.id.set_middle_point);
        mEndPointButton = (Button) findViewById(R.id.set_end_point);
        mStartPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPoint = true;
                middlePoint = false;
                endPoint = false;
            }
        });
        mMiddlePointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPoint = false;
                middlePoint = true;
                endPoint = false;
            }
        });
        mEndPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPoint = false;
                middlePoint = false;
                endPoint = true;
            }
        });

        mSaveRouteButton = (Button) findViewById(R.id.save_route);
        mSaveRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("travelerRoutes");
                GeoFire geoFire = new GeoFire(ref);
                System.out.println("######: " + mapMarkers.size());
                for(int i=0; i<mapMarkers.size(); i++) {
                    geoFire.setLocation("user1",
                        new GeoLocation(mapMarkers.get(i).getPosition().latitude,
                        mapMarkers.get(i).getPosition().longitude));
                }
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                checkLocationPermission();
            }
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String markerTitle = "";
                float hueColor = 0;
                if(startPoint == true) {
                    startPoint = false;
                    markerTitle = "Start Point";
                    hueColor = BitmapDescriptorFactory.HUE_GREEN;
                    middlePoint = true;
                } else if(middlePoint == true) {
                    markerTitle = "Middle Point(" + trailPointCount + ")";
                    hueColor = BitmapDescriptorFactory.HUE_AZURE;
                    trailPointCount++;
                } else {
                    markerTitle = "End Point";
                    hueColor = BitmapDescriptorFactory.HUE_RED;
                }
                Marker m = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(markerTitle)
                        .icon(BitmapDescriptorFactory.defaultMarker(hueColor)));
                mapMarkers.add(m);
                if(mapMarkers.size() > 1) {
                    Polyline pl = mMap.addPolyline(new PolylineOptions()
                            .add(mapMarkers.get(mapMarkers.size()-2).getPosition(), latLng)
                            .width(3)
                            .color(Color.RED));
                }
            }
        });
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for(Location location : locationResult.getLocations()) {
                mLastLocation = location;

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            }
        }
    };

    private void checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(SetRouteActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(SetRouteActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    //@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case 1: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please provide the permissions", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
