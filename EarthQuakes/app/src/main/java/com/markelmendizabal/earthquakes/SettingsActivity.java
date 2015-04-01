package com.markelmendizabal.earthquakes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.markelmendizabal.earthquakes.fragments.SettingsFragment;
import com.markelmendizabal.earthquakes.managers.EarthQuakeAlarmManager;
import com.markelmendizabal.earthquakes.services.DownloadEarthQuakesService;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //insertal el fragmento aqui por java
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);


    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String PREF_AUTO_UPDATE = getString(R.string.pref_auto_update);
        String AUTO_UPTDATE_INTERVAL = getString(R.string.auto_update_interval);
        String MAGNITUDE = getString(R.string.magnitude);
        if (key.equals(PREF_AUTO_UPDATE)) {
            //start/Stop auto refresh
            boolean update = sharedPreferences.getBoolean(key,false);
            if(update){
                long interval=Long.parseLong(sharedPreferences.getString(AUTO_UPTDATE_INTERVAL,"60"));
                EarthQuakeAlarmManager.setAlarm(this,interval*60*1000);

            }else{
                EarthQuakeAlarmManager.cancel(this);
            }
            // Log.d("a", minMag);



        } else if (key.equals(AUTO_UPTDATE_INTERVAL)) {
            long interval=Long.parseLong(sharedPreferences.getString(AUTO_UPTDATE_INTERVAL,"60"));
            EarthQuakeAlarmManager.updateAlarm(this,interval*60*1000);
           // Log.d("a", key);

        } else if (key.equals(MAGNITUDE)) {
            // double minMag = Double.parseDouble(sharedPreferences.getString(key, "0"));


        }

    }


}