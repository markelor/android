package com.markelmendizabal.earthquakes.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
import com.markelmendizabal.earthquakes.services.DownloadEarthQuakesService;

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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_refresh,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_referesh) {
            Intent download = new Intent(getActivity(), DownloadEarthQuakesService.class);
            getActivity().startService(download);

        }
        return super.onOptionsItemSelected(item);
    }
}
