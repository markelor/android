package com.markelmendizabal.earthquakes.abstracts;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.markelmendizabal.earthquakes.R;
import com.markelmendizabal.earthquakes.adapter.EarthQuakeAdapter;
import com.markelmendizabal.earthquakes.database.EarthQuakeDB;
import com.markelmendizabal.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cursomovil on 13/04/15.
 */
public abstract class AbstractMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback  {
    //protected no es visible hacia fuera pero si para sus hijos
    protected EarthQuakeDB earthQuakeDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        earthQuakeDB=new EarthQuakeDB(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout= super.onCreateView(inflater, container, savedInstanceState);
        getMap().setOnMapLoadedCallback(this);
        return  layout;
    }

    abstract protected void getData();

    abstract protected void showMap();

    public void onMapLoaded() {
        this.getData();
        this.showMap();
    }
    }


