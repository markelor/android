package com.markelmendizabal.earthquakes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.markelmendizabal.earthquakes.fragments.EarthQuakeFragment;
import com.markelmendizabal.earthquakes.model.EarthQuake;


public class DetailsActivity extends ActionBarActivity {
    private EarthQuake earthQuake;
    private TextView lblPlace;
    private TextView lblDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        lblPlace= (TextView) findViewById(R.id.place);
       // lblDate= (TextView) findViewById(R.id.lblDate);

        Intent detailIntent = getIntent();
        earthQuake = detailIntent.getParcelableExtra(EarthQuakeFragment.DETAIL_ITEM);
        populateView();
    }
    private void populateView(){
        lblPlace.setText(earthQuake.getPlace());
        //lblDate.setText(earthquake.getCreatedFormated());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
