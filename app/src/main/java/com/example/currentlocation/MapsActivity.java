package com.example.currentlocation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.currentlocation.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
     TextView textView;
     LocationManager locationManager;
     Button cl;
    double  longitude,  latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cl = findViewById(R.id.cl);
        textView = findViewById(R.id.textView);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onLocationChanged(@NonNull Location location) {
         longitude=location.getLongitude();
         latitude = location.getLatitude();
         textView.setText("longitude:- "+longitude+ "\n" + "latitude:- "+latitude );

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mMap = googleMap;
                    LatLng latLng = new LatLng(latitude, longitude);
                    Toast.makeText(MapsActivity.this,latitude +" "+longitude,Toast.LENGTH_SHORT).show();
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 11);
                    googleMap.animateCamera(cameraUpdate);
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}