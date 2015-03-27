package com.markelmendizabal.earthquakes.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markelmendizabal.earthquakes.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //	Register this OnSharedPreferenceChangeListener
        SharedPreferences prefs	=PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        //Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.userpreferences);

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String PREF_AUTO_UPDATE=getString(R.string.pref_auto_update);
        String PREF_AUTO_INTERVAL=getString(R.string.pref_auto_interval);
        String MAGNITUDE =getString(R.string.magnitude);
        if(key.equals(PREF_AUTO_UPDATE)){
            //start/Stop auto refresh
        }else if(key.equals(PREF_AUTO_INTERVAL)){

        }else if (key.equals(MAGNITUDE)){
            double minMag=Double.parseDouble(sharedPreferences.getString(key,"0"));

        }

    }
}
