package com.example.oabass.eventlocationdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_REQUEST_CODE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getlocationPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getlocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            }
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_REQUEST_CODE);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.test));
        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }
        mMap.setMyLocationEnabled(true);


        mMap.setIndoorEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("Hello world"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20, 40))
                .title("Hello world"));

        Address test=null;
        List<Address> addressList = new ArrayList<>();
        Geocoder geocoder = new Geocoder(this);
        List<Address> temp = null;
        try {

            temp= (List<Address>)geocoder.getFromLocationName("Coover ames iowa",1);
            addressList.add(0, temp.get(0));

            //addressList.add(temp.get(0));
            temp= (List<Address>) geocoder.getFromLocationName("Parks Library ames iowa",1);

            addressList.add(1, temp.get(0));

        } catch (IOException e) {
            e.printStackTrace();
        }
        test=addressList.get(0);
        Log.d(TAG, "onMapReady: test "+test.getLongitude());
        LatLng latLng = new LatLng(test.getLatitude(), test.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Coover"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        test=addressList.get(1);
        Log.d(TAG, "onMapReady: test2"+ test.getLongitude());
        latLng = new LatLng(test.getLatitude(), test.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Isu Library"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i : grantResults) {
                        if (i != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }


                }
            }
        }
    }

}
