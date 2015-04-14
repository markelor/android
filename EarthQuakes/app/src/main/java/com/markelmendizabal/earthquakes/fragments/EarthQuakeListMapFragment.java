package com.markelmendizabal.earthquakes.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.GoogleMap;
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
    private List<EarthQuake> earthQuakes;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    protected void getData() {
        int minMag = Integer.parseInt(prefs.getString(getString(R.string.magnitude), "0"));
        earthQuakeDB.getAllByMagnitude(minMag);

    }

    @Override
    protected void showMap() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (EarthQuake earthQuake : earthQuakes) {

            LatLng eartqueakeposition = new LatLng(earthQuake.getCoords().getLng(),
                    earthQuake.getCoords().getLat());

            String Place = earthQuake.getPlace();
            String Url = earthQuake.getUrl();
            double Magnitude = earthQuake.getMagnitude();
            getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            MarkerOptions marker = new MarkerOptions().position(eartqueakeposition).title(Place).snippet(String.valueOf(Magnitude));
            getMap().addMarker(marker);
            builder.include(marker.getPosition());

        }
        LatLngBounds bounds = builder.build();
    }
}
