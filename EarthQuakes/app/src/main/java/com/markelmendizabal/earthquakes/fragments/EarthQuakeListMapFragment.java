package com.markelmendizabal.earthquakes.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.markelmendizabal.earthquakes.R;
import com.markelmendizabal.earthquakes.abstracts.AbstractMapFragment;
import com.markelmendizabal.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * Created by cursomovil on 13/04/15.
 */
public class EarthQuakeListMapFragment extends AbstractMapFragment {
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    protected void getData() {
        int minMag = Integer.parseInt(prefs.getString(getString(R.string.magnitude), "0"));
       earthQuakes= earthQuakeDB.getAllByMagnitude(minMag);

    }

    @Override
    protected void showMap() {

        getMap().clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (EarthQuake earthQuake: earthQuakes) {
            MarkerOptions marker = crearMarker(earthQuake);
            getMap().addMarker(marker);
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);

        getMap().animateCamera(cu);
    }
}
