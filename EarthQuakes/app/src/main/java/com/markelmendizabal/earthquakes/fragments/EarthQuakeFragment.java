package com.markelmendizabal.earthquakes.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.markelmendizabal.earthquakes.DetailsActivity;
import com.markelmendizabal.earthquakes.R;
import com.markelmendizabal.earthquakes.adapter.EarthQuakeAdapter;
import com.markelmendizabal.earthquakes.model.EarthQuake;
import com.markelmendizabal.earthquakes.tasks.DownloadEarthquakeTasks;

import java.util.ArrayList;


/**
 * A fragment representing a list of EarthQuake.
 */
public class EarthQuakeFragment extends ListFragment implements DownloadEarthquakeTasks.AddEarthQuakeInterface {
    private ArrayList<EarthQuake> arr;
    private ArrayAdapter<EarthQuake> aa;
    public static final String DETAIL_ITEM = "DETAIL_ITEM";
    private final String EARTHQUAKE = "earthquake";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake, arr);

        setListAdapter(aa);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            ArrayList<EarthQuake> tmp = savedInstanceState.getParcelableArrayList(EARTHQUAKE);
            if (tmp != null) {
                arr.addAll(tmp);
            }

        }

        arr = new ArrayList<EarthQuake>();
        DownloadEarthquakeTasks task = new DownloadEarthquakeTasks(this);
        //android crea un thread internamente y llama al doInBackround
        task.execute(getString(R.string.earthquakeurl));
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        EarthQuake earthQuake = arr.get(position);
        Intent detailIntent = new Intent(getActivity(), DetailsActivity.class);
        detailIntent.putExtra(DETAIL_ITEM, earthQuake);
        startActivity(detailIntent);

    }


    @Override
    public void addEarthQuake(EarthQuake earthquake) {
        arr.add(0, earthquake);
        aa.notifyDataSetChanged();

    }

    @Override
    public void notifyTotall(int total) {
        // Log.d("a", total);
        String msg = getString(R.string.num_earthquakes, total);
        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();


    }
}
