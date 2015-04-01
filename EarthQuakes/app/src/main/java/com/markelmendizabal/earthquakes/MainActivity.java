package com.markelmendizabal.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.markelmendizabal.earthquakes.database.EarthQuakeDB;
import com.markelmendizabal.earthquakes.managers.EarthQuakeAlarmManager;
import com.markelmendizabal.earthquakes.services.DownloadEarthQuakesService;
import com.markelmendizabal.earthquakes.tasks.DowloadEarthQuakesTask;


public class MainActivity extends ActionBarActivity implements DowloadEarthQuakesTask.AddEarthQuakeInterface {

    private static final int PREFS_ACTIVITY = 1;
    private EarthQuakeDB eartQuakeDB;
    private final String EARTHQUAKE_PREFS="EARTHQUAKE_PREFS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DowloadEarthQuakesTask task = new DowloadEarthQuakesTask(this, this);

        task.execute(getString(R.string.earthquakeurl));
        checkToSetAlarm();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefIntent;
            prefIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(prefIntent, PREFS_ACTIVITY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        @Override
        public void addEarthQuake(EarthQuake earthquake) {
            ContentValues newValues	=	new	ContentValues();
            if(eartQuakeDB!=null){
                ContentValues values = new ContentValues();
                values.put("id", earthQuake.get_id());
                values.put("magnitude", earthQuake.getMagnitude());
                values.put("place", earthQuake.getPlace());
                values.put("url", earthQuake.getUrl());
                values.put("coords", earthQuake.getCoords().toString());
                values.put("time", earthQuake.getTime().toString());
                //	Insert	the	row	into	your	table
                eartQuakeDB.insert()
                eartQuakeDB.insert(HoardDBOpenHelper.DATABASE_TABLE,	null,	newValues);
            }
            db.close();



        }
    */
    public void checkToSetAlarm() {
        //Intent downdload = new Intent(this, DownloadEarthQuakesService.class);
        //startService(downdload);
        SharedPreferences prefs=getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);
        if(!prefs.getBoolean("LAUNCHED_BEFORE",false)){
            long interval=getResources().getInteger(R.integer.default_integer)*60*1000;
            EarthQuakeAlarmManager.setAlarm(this,interval);
            prefs.edit().putBoolean("LAUNCHED_BEFORE",true).apply();
        }

    }

    @Override
    public void notifyTotall(int total) {
        String msg = getString(R.string.magnitude, total);
        Toast toast = Toast.makeText(this, msg + total, Toast.LENGTH_LONG);
        toast.show();

    }
}