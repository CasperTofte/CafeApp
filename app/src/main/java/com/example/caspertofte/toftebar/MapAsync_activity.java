package com.example.caspertofte.toftebar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapAsync_activity extends AppCompatActivity {

    // Async
    MyAsyncTask myAsyncTask;

    double lastLat;
    double lastLong;
    String lastStreetAddress;
    String lastPostalCode;
    String lastCity;
    String locationAddress;

    TextView tv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_async_activity);

        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, String> {

        Location lastLocation = null;

        @Override
        protected String doInBackground(Void... params) {
            Location location = getLastLocation();
            reverseGeocode(location);
            return locationAddress;
        }
        @Override
        protected void onPreExecute() {
            tv_address = (TextView)findViewById(R.id.txt_asyncAddress);
        }
        @Override
        protected void onPostExecute(String result) {
            tv_address.setText("Your last position was " + result);
        }


        protected Location getLastLocation() {
                // Check if permissions are granted
                if (ActivityCompat.checkSelfPermission(MapAsync_activity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MapAsync_activity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    Log.e("Location:", "Missing location permissions");
                }
                else {
                    Log.d("Location", "Permissions granted");
                LocationManager locationManager = (LocationManager)
                        MapAsync_activity.this.getSystemService(MapAsync_activity.this.LOCATION_SERVICE);

                lastLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            }
            return lastLocation;
        }

        private void reverseGeocode(Location location) {
            lastLat = location.getLatitude();
            lastLong = location.getLongitude();
            Log.d("Location: lat+long", String.valueOf(lastLat) + String.valueOf(lastLong));
            List<Address> addresses = null;
            Geocoder geocoder = new Geocoder(MapAsync_activity.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(lastLat, lastLong, 1);  // present 1 result
                Log.d("Location", addresses.toString());
                lastStreetAddress = addresses.get(0).getAddressLine(0);
                lastPostalCode = addresses.get(0).getPostalCode();
                lastCity = addresses.get(0).getLocality();

                locationAddress = lastStreetAddress + ", " + lastPostalCode;

                Log.d("Location:", addresses.toString());   // log everyting
                Log.d("Location: address", lastStreetAddress);
                Log.d("Location: Postal code", lastPostalCode);
                //Log.d("Location: address", lastCity);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

}




