package com.markelmendizabal.earthquakes.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.markelmendizabal.earthquakes.R;
import com.markelmendizabal.earthquakes.adapter.EarthQuakeAdapter;
import com.markelmendizabal.earthquakes.model.Coordinate;
import com.markelmendizabal.earthquakes.model.EarthQuake;
import com.markelmendizabal.earthquakes.tasks.DownloadEarthquakeTasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * A fragment representing a list of EarthQuake.

 */
public class EarthQuakeFragment extends ListFragment implements  DownloadEarthquakeTasks.AddEarthQuakeInterface {
    private ArrayList<EarthQuake> arr;
    private ArrayAdapter<EarthQuake>aa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=super.onCreateView(inflater, container, savedInstanceState);
        aa=new EarthQuakeAdapter(getActivity(),R.layout.earthquake,arr);
        setListAdapter(aa);
        return  layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        arr=new ArrayList<EarthQuake>();
        DownloadEarthquakeTasks task=new DownloadEarthquakeTasks(this);
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
    public void addEarthQuake(EarthQuake earthquake) {
        arr.add(0,earthquake);
        aa.notifyDataSetChanged();

    }
}
