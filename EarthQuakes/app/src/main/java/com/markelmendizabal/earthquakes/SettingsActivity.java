package com.markelmendizabal.earthquakes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.markelmendizabal.earthquakes.fragments.SettingsFragment;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //insertal el fragmento aqui por java
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();


    }

    private void setAlarm() {

        //	Get	a	reference	to	the	Alarm	Manager

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //	Set	the	alarm	to	wake	the	device	if	sleeping.

        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;

        //	Trigger	the	device	in	10	seconds.

        long timeOrLengthofWait = 10000;

        //	Create	a	Pending	Intent	that	will	broadcast	and	action

        String ALARM_ACTION = "ALARM_ACTION";
        Intent intentToFire = new Intent(ALARM_ACTION);

        PendingIntent alarmIntent = PendingIntent.getService(this, 0, intentToFire, 0);

        //	Set	the	alarm

        alarmManager.set(alarmType, timeOrLengthofWait, alarmIntent);
        alarmManager.cancel(alarmIntent);

    }


}