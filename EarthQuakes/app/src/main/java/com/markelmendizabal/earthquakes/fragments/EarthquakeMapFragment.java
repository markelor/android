package com.markelmendizabal.earthquakes.fragments;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.markelmendizabal.earthquakes.R;
import com.markelmendizabal.earthquakes.fragments.EarthQuakeListFragment;
import com.markelmendizabal.earthquakes.model.EarthQuake;

import java.util.List;

public class EarthquakeMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback  {

    private List<EarthQuake> earthQuakes;
    private EarthQuake earthQuake;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=super.onCreateView(inflater, container, savedInstanceState);
        mMap=getMap();
        mMap.setOnMapLoadedCallback(this);
        return layout;
    }

    public void setEarthQuakes(List<EarthQuake> earthQuakes) {
        this.earthQuakes = earthQuakes;
    }


    @Override
    public void onMapLoaded() {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLngBounds.Builder builder= new LatLngBounds.Builder();

        for (EarthQuake earthQuake : earthQuakes) {
            double lat = earthQuake.getCoords().getLat();
            double lng = earthQuake.getCoords().getLng();


            LatLng position = new LatLng(lng, lat);
            LatLng eartqueakeposition = new LatLng(earthQuake.getCoords().getLng(),
                    earthQuake.getCoords().getLat());

            String Place = earthQuake.getPlace();
            String Url = earthQuake.getUrl();
            double Magnitude = earthQuake.getMagnitude();

            MarkerOptions marker = new MarkerOptions().position(eartqueakeposition).title(Place).snippet(String.valueOf(Magnitude));
            mMap.addMarker(marker);
            builder.include(marker.getPosition());
            LatLngBounds bounds = builder.build();

            CameraPosition camPos = new CameraPosition.Builder().target(position)
                    .zoom(3)
                    .build();
            CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
            mMap.animateCamera(camUpd);
        }
        }
}
