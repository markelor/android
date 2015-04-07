package com.markelmendizabal.geolocation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.markelmendizabal.geolocation.listeners.LocationListener;


public class MainActivity extends ActionBarActivity implements LocationListener.AddLocationInterface {
    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblAltitude;
    private TextView lblSpeed;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String	serviceString=	Context.LOCATION_SERVICE;
        locationManager	=	(LocationManager)getSystemService(serviceString);
        lblLatitude = (TextView) findViewById(R.id.lblLatidude);
        lblLongitude = (TextView) findViewById(R.id.lblLongitude);
        lblAltitude = (TextView) findViewById(R.id.lblAltitude);
        lblSpeed = (TextView) findViewById(R.id.lblSpeed);
        getLocationProvider();
        listenLocationChanges();
    }


    private void getLocationProvider() {


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);
        criteria.setSpeedRequired(true);
        provider = locationManager.getBestProvider(criteria, true);
        //LocationProvider provider = locationManager.getProvider(bestProvider);
        Log.d("a", provider);
    }
    private void listenLocationChanges() {
        int t=5000;
        int distance=5;
        LocationListener listener=new LocationListener(this);
        locationManager.requestLocationUpdates(provider,t,distance,listener);
    }


    @Override
    public void addLocation(Location location) {
        lblLatitude.setText(String.valueOf(location.getLatitude()));
        lblLongitude.setText(String.valueOf(location.getLongitude()));
        lblAltitude.setText(String.valueOf(location.getAltitude()));
        lblSpeed.setText(String.valueOf(location.getSpeed()));
    }
}
