package com.markelmendizabal.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.app.ActionBar;

import com.markelmendizabal.earthquakes.fragments.EarthQuakeListFragment;
import com.markelmendizabal.earthquakes.fragments.EarthQuakeListMapFragment;
import com.markelmendizabal.earthquakes.listeners.TabListener;
import com.markelmendizabal.earthquakes.managers.EarthQuakeAlarmManager;
import com.markelmendizabal.earthquakes.tasks.DowloadEarthQuakesTask;


public class MainActivity extends Activity implements DowloadEarthQuakesTask.AddEarthQuakeInterface {

    private static final int PREFS_ACTIVITY = 1;
    private final String EARTHQUAKE_PREFS="EARTHQUAKE_PREFS";private ActionBar actionBar;
    private	static final String	SELECTED_TAB_KEY = "SELECTED_TAB_KEY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DowloadEarthQuakesTask task = new DowloadEarthQuakesTask(this, this);
        task.execute(getString(R.string.earthquakeurl));
        checkToSetAlarm();
        actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tabList = actionBar.newTab();

        tabList.setText(getString(R.string.tab_list_title))
                .setTabListener(
                        new TabListener<EarthQuakeListFragment>
                                (this, R.id.fragmentContainer, EarthQuakeListFragment.class));

        actionBar.addTab(tabList);

        //tab all earthquakes
        ActionBar.Tab tabAllEarthquakes = actionBar.newTab();
        tabAllEarthquakes.setText(getString(R.string.tab_map_title))
                .setTabListener(
                        new TabListener<EarthQuakeListMapFragment>
                                (this, R.id.fragmentContainer, EarthQuakeListMapFragment.class));
        actionBar.addTab(tabAllEarthquakes);
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

    public void checkToSetAlarm() {
        //Intent downdload = new Intent(this, DownloadEarthQuakesService.class);
        //startService(downdload);
        SharedPreferences prefs=getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);
        //cuando se refresca el servicio salta la alarma
        //si es la primera vez que lanzamos la aplicacion se activa, si no no
        if(!prefs.getBoolean("LAUNCHED_BEFORE",false)){
            long interval=getResources().getInteger(R.integer.default_integer)*60*1000;
            EarthQuakeAlarmManager.setAlarm(this,interval);
            prefs.edit().putBoolean("LAUNCHED_BEFORE",true).apply();
        }

    }

    @Override
    public void notifyTotall(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(SELECTED_TAB_KEY, actionBar.getSelectedNavigationIndex());
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if	(savedInstanceState	!=	null && savedInstanceState.containsKey(SELECTED_TAB_KEY))
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB_KEY));

        super.onRestoreInstanceState(savedInstanceState);
    }
}