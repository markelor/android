package com.markelmendizabal.earthquakes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.markelmendizabal.earthquakes.model.EarthQuake;
import com.markelmendizabal.earthquakes.tasks.DownloadEarthquakeTasks;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements DownloadEarthquakeTasks.AddEarthQuakeInterface  {
    private final int PREFS_ACTYVITY=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateEarthQuakes();

    }

    private void updateEarthQuakes() {

        DownloadEarthquakeTasks task = new DownloadEarthquakeTasks(this);
        //android crea un thread internamente y llama al doInBackround
        task.execute(getString(R.string.earthquakeurl));
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
            Intent prefsIntent=new Intent(this,SettingsActivity.class);
            startActivityForResult(prefsIntent,PREFS_ACTYVITY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


   /* public void addEarthQuake(EarthQuake earthquake) {
        double minMagnitude= Double.parseDouble(prefs.getString(getString(R.string.magnitude), "0"));

        if(earthquake.getMagnitude()>=minMagnitude){
            arr.add(0, earthquake);
            aa.notifyDataSetChanged();

        }



    }*/

    @Override
    public void notifyTotall(int total) {
        // Log.d("a", total);
        String msg = getString(R.string.num_earthquakes, total);
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();


    }
}
