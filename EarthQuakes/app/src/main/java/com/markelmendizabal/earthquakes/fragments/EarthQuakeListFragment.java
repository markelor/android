package com.markelmendizabal.earthquakes.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.markelmendizabal.earthquakes.DetailsActivity;
import com.markelmendizabal.earthquakes.R;
import com.markelmendizabal.earthquakes.adapter.EarthQuakeAdapter;
import com.markelmendizabal.earthquakes.database.EarthQuakeDB;
import com.markelmendizabal.earthquakes.model.EarthQuake;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A fragment representing a list of EarthQuake.
 */
public class EarthQuakeListFragment extends ListFragment {
    private ArrayList<EarthQuake> arr;
    private ArrayAdapter<EarthQuake> aa;
    public static final String DETAIL_ITEM = "DETAIL_ITEM";
    private final String EARTHQUAKE = "earthquake";
    private SharedPreferences prefs = null;
    private JSONObject json;
    private EarthQuakeDB earthQuakeDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arr = new ArrayList<EarthQuake>();
        //cargar preferencias

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        earthQuakeDB = new EarthQuakeDB(getActivity());

        if (savedInstanceState != null) {
            ArrayList<EarthQuake> tmp = savedInstanceState.getParcelableArrayList(EARTHQUAKE);
            if (tmp != null) {
                arr.addAll(tmp);
            }

        }


        //para que la ejecucion del programa y my Jsoon vayan aparte lanzamos un thread
       /* Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                updateEarthQuake();
            }
        });
        t.start();
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake, arr);
        if (savedInstanceState != null) {

            ArrayList<EarthQuake> tmp = savedInstanceState.getParcelableArrayList(DETAIL_ITEM);
            if (tmp != null) {

                arr.addAll(tmp);
            }
        }

        setListAdapter(aa);
        return layout;
    }


    @Override
    public void onResume() {
        super.onResume();

        int minMag = Integer.parseInt(prefs.getString(getString(R.string.magnitude), "0"));
        arr.clear();
        arr.addAll(earthQuakeDB.getAllByMagnitude(minMag));
        aa.notifyDataSetChanged();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        EarthQuake earthQuake = arr.get(position);
        Intent detailIntent = new Intent(getActivity(), DetailsActivity.class);
        detailIntent.putExtra(DETAIL_ITEM, earthQuake);
        startActivity(detailIntent);

    }


}
