package com.markelmendizabal.earthquakes.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.markelmendizabal.earthquakes.services.DownloadEarthQuakesService;

/**
 * Created by cursomovil on 1/04/15.
 */
public class EarthQuakeAlarmManager {
    //static: para acceder a el sin usar new
    public static void setAlarm(Context context, long interval){

        //	Get	a	reference	to	the	Alarm	Manager
       AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //	Set	the	alarm	to	wake	the	device	if	sleeping.
        int alarmType = AlarmManager.RTC;
        //	Create	a	Pending	Intent	that	will	broadcast	and	action
        Intent intentToFire	=	new	Intent(context,DownloadEarthQuakesService.class);
        PendingIntent alarmIntent  = PendingIntent.getService(context, 0, intentToFire, 0);
        //	Set	the	alarm

        alarmManager.setInexactRepeating(alarmType,
                interval,
                interval,
                alarmIntent);
        //Log.d("aa", "a");

    }
    public static void cancel(Context context){
        AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //lanzar intent para que android sepa cual cancelar
        Intent intentToFire	=	new	Intent(context,DownloadEarthQuakesService.class);
        PendingIntent alarmIntent  = PendingIntent.getService(context, 0, intentToFire, 0);
        alarmManager.cancel(alarmIntent);
        //Log.d("aa", "b");

    }
    public static void updateAlarm(Context context,long interval){
       setAlarm(context, interval);
       // Log.d("aa", "c");



    }
}
