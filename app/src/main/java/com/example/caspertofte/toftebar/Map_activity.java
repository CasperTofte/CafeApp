package com.example.caspertofte.toftebar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Map_activity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 100;
    GoogleApiClient mGoogleApiClient;
    TextView tv_location;
    TextView tv_address;

    double currentLat;
    double currentLong;
    String currentStreetAddress;
    String currentPostalCode;
    String currentCity;

    double toftebarLat;
    double toftebarLong;
    LocationRequest mLocationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        tv_location = (TextView) findViewById(R.id.txt_location);
        tv_address = (TextView) findViewById(R.id.txt_address);

        MapFragment mapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        // Initialize Maps API client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void onMapReady(GoogleMap googleMap) {
        // Retrieve bar address
        forwardGeocode();

        // Zoom in on the location
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(toftebarLat, toftebarLong), 15);
        googleMap.moveCamera(update);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker to TofteBar
        addMarker(toftebarLat, toftebarLong, "Toftebar");



        // googleMap.addMarker(new MarkerOptions()
        //         .position(new LatLng(toftebarLat, toftebarLong))
        //         .title("Toftebar"));



    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

        // Check if permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            Log.e("Location:", "Missing location permissions");
            return;
        }

        // Location listener - checks if location changes
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // update every second
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);

        // Get last known location
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        reverseGeocode(location);   // Get address from latitude + longitude
        addMarker(currentLat, currentLong, "Here I am");
        tv_address.setText("Your current position is: " + currentStreetAddress + ", " + currentPostalCode);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        tv_location.setText(location.toString());
        Log.d("Location", tv_location.getText().toString());

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void forwardGeocode() {
        Geocoder fwdGeocoder = new Geocoder(this, Locale.getDefault());
        //String streetAddress = "Ribevej 3, 9220 Aalborg Øst";
        String streetAddress = "Klostergårdsvej 10, 8881 Thorsø";
        List<Address> locations = null;
        try {
            locations = fwdGeocoder.getFromLocationName(streetAddress, 1); // present 1 result
            toftebarLong = locations.get(0).getLongitude();
            toftebarLat = locations.get(0).getLatitude();
            Log.d("Location:", locations.toString());   //log everyting
            Log.d("Location: Lat", String.valueOf(toftebarLat));
            Log.d("Location: Long", String.valueOf(toftebarLong));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void reverseGeocode(Location location) {
        currentLat = location.getLatitude();
        currentLong = location.getLongitude();
        Log.d("Location: lat+long", String.valueOf(currentLat)+String.valueOf(currentLong));
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(currentLat, currentLong, 1);  // present 1 result
            Log.d("Location", addresses.toString());
            currentStreetAddress = addresses.get(0).getAddressLine(0);
            currentPostalCode = addresses.get(0).getPostalCode();
            currentCity = addresses.get(0).getLocality();

            Log.d("Location:", addresses.toString());   // log everyting
            Log.d("Location: address", currentStreetAddress);
            Log.d("Location: Postal code", currentPostalCode);
            //Log.d("Location: address", currentCity);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void addMarker(double latitude, double lontitude, String title) {

        GoogleMap mMap;
        mMap = ((MapFragment)
                getFragmentManager().findFragmentById(R.id.fragment_map)).getMap();
        //TODO: getMap should be replaced by getMapAsync

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, lontitude))
                .title(title));
    }
}