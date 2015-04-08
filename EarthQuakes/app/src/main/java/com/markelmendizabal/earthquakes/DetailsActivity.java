package com.markelmendizabal.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.markelmendizabal.earthquakes.fragments.EarthQuakeListFragment;
import com.markelmendizabal.earthquakes.model.EarthQuake;


public class DetailsActivity extends FragmentActivity {
    private EarthQuake earthQuake;
    private TextView _id;
    private TextView lblPlace;
    private TextView lblDate;
    private TextView time;
    private TextView magnitude;
    private TextView url;
    private GoogleMap mMap;
    public static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //lblPlace = (TextView) findViewById(R.id.place);
        // lblDate= (TextView) findViewById(R.id.lblDate);

        Intent detailIntent = getIntent();
        earthQuake = detailIntent.getParcelableExtra(EarthQuakeListFragment.DETAIL_ITEM);
       // populateView();
        setUpMapIfNeeded();


    }

    private void populateView() {
        //lblPlace.setText(earthQuake.getPlace());
        //lblDate.setText(earthquake.getCreatedFormated());

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
       double lat=earthQuake.getCoords().getLat();
       double lng=earthQuake.getCoords().getLng();
        Log.d("aaa", earthQuake.getPlace());
        Log.d("aaa", String.valueOf(lat));
        Log.d("aaa", String.valueOf(lng));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Marker"));
    }
}

