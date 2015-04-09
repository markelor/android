package com.markelmendizabal.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.markelmendizabal.earthquakes.fragments.EarthQuakeListFragment;
import com.markelmendizabal.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends FragmentActivity {
    private EarthQuake earthQuake;
    private TextView _id;
    private TextView lblPlace;
    private TextView lblDate;
    private TextView time;
    private TextView magnitude;
    private TextView url;
    private GoogleMap mMap;
    private EarthquakeMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //lblPlace = (TextView) findViewById(R.id.place);
        //lblDate= (TextView) findViewById(R.id.lblDate);
        mapFragment=(EarthquakeMapFragment)getFragmentManager().findFragmentById(R.id.map);

        Intent detailIntent = getIntent();
        earthQuake = detailIntent.getParcelableExtra(EarthQuakeListFragment.DETAIL_ITEM);
        populateView();
        showMapEarthquake(earthQuake);


    }

    private void showMapEarthquake(EarthQuake earthQuake) {
        List<EarthQuake> earthQeakes=new ArrayList<EarthQuake>();
        earthQeakes.add(earthQuake);
        mapFragment.setEarthQuakes(earthQeakes);

    }

    private void populateView() {
        lblPlace.setText(earthQuake.getPlace());
        //lblDate.setText(earthquake.getCreatedFormated());

    }


    @Override
    protected void onResume() {
        super.onResume();

    }





}

