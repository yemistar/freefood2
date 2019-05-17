package com.example.location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.EventObj.Event2;
import com.example.Utils.Parse;
import com.example.oabass.freefood.MainActivity;
import com.example.oabass.freefood.R;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{
    private static final String TAG = "MapActivity";
    private GoogleMap mMap;
    private RequestQueue mRequestQueue;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_REQUEST_CODE = 1234;
    List<Address> addressList = new ArrayList<>();
    Geocoder geocoder;
    Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        getlocationPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        mRequestQueue= Volley.newRequestQueue(getApplicationContext());
        geocoder = new Geocoder(MapActivity.this);

        BottomNavigationView bottomNavigationView= (BottomNavigationView) findViewById(R.id.mapNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Subs:


                        break;

                    case R.id.map:

                        onRestart();
                        break;


                    case R.id.help :

                       // onBackPressed();
//                        Intent intent=new Intent(MapActivity.this, MainActivity.class);
//                        startActivity(intent);
                        startActivity(new Intent(MapActivity.this, MainActivity.class));
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;


        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.test));
        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMinZoomPreference(14.0f);
        mMap.setMaxZoomPreference(14.0f);
//        MapRunableBackground mapRunableBackground=new MapRunableBackground();
//        new Thread(mapRunableBackground).start();

        List<Address> temp = null;

        try {

            Log.d(TAG, "onMapReady: size :"+MainActivity.event2ArrayList.size());
            for(int i=0; i< MainActivity.event2ArrayList.size(); i++){
                String name=""+MainActivity.event2ArrayList.get(i).getLocation();

                    temp= (List<Address>)geocoder.getFromLocationName(name+" ames iowa",1);
                    addressList.add(i, temp.get(0));

                    Address test=addressList.get(i);
                    Address test2=temp.get(0);
                    LatLng latLng = new LatLng(test2.getLatitude(), test2.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(name));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


            }




        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    /**
     *  Send the http reuest to the backend
     */
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

    class MapRunableBackground implements Runnable{



        MapRunableBackground(){

        }

        @Override
        public void run() {


            List<Address> temp = null;

            try {

                for(int i=0; i< MainActivity.event2ArrayList.size(); i++){
                    final String name=""+MainActivity.event2ArrayList.get(i).getLocation();
                    if(name.equals("null")){
                        temp= (List<Address>)geocoder.getFromLocationName("Coover ames iowa",1);
                        addressList.add(i, temp.get(0));

                        final int var=i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Address test=addressList.get(var);
                                LatLng latLng = new LatLng(test.getLatitude(), test.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(latLng).title(name));
                                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                            }
                        });
                    }else{
                        temp= (List<Address>)geocoder.getFromLocationName(name+" ames iowa",1);
                        addressList.add(i, temp.get(0));

                        final int var=i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Address test=addressList.get(var);
                                LatLng latLng = new LatLng(test.getLatitude(), test.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(latLng).title(name));
                                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                            }
                        });
                    }

                }

//                temp= (List<Address>)geocoder.getFromLocationName("Coover ames iowa",1);
//                addressList.add(0, temp.get(0));
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Address test=addressList.get(0);
//                        LatLng latLng = new LatLng(test.getLatitude(), test.getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("Coover"));
//                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                    }
//                });
//
//
//                temp= (List<Address>) geocoder.getFromLocationName("Parks Library ames iowa",1);
//
//                addressList.add(1, temp.get(0));
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Address test=addressList.get(1);
//                        LatLng latLng = new LatLng(test.getLatitude(), test.getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("ISU Library"));
//                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                    }
//                });

                temp= (List<Address>)geocoder.getFromLocationName("Pearson Hall ames iowa",1);
                addressList.add(2, temp.get(0));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Address test=addressList.get(2);
                        LatLng latLng = new LatLng(test.getLatitude(), test.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Pearson"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
