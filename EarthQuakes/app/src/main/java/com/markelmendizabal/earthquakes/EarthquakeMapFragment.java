package com.markelmendizabal.earthquakes;

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
        mMap = getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        //bound

        for (EarthQuake earthQuake : earthQuakes) {
            double lat = earthQuake.getCoords().getLat();
            double lng = earthQuake.getCoords().getLng();

            mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat)).title(earthQuake.getPlace()).snippet(earthQuake.getUrl()));
            LatLng position = new LatLng(lng, lat);

            /*CameraPosition camPos = new CameraPosition.Builder().target(position)
                    .zoom(0)
                    .build();
            CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
            mMap.animateCamera(camUpd);*/
        }
        }
}
