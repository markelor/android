package com.markelmendizabal.earthquakes.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.markelmendizabal.earthquakes.providers.EarthQuakeDB;
import com.markelmendizabal.earthquakes.model.Coordinate;
import com.markelmendizabal.earthquakes.model.EarthQuake;

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

/**
 * Created by cursomovil on 25/03/15.
 */
public class DowloadEarthQuakesTask extends AsyncTask<String, EarthQuake, Integer> {
    private EarthQuakeDB eartQuakeDB;

    public interface AddEarthQuakeInterface {
        // public void addEarthQuake(EarthQuake earthquake);
        public void notifyTotall(int total);

    }

    private final String EARTHQUAKE = "EARTHQUAKE";
    private AddEarthQuakeInterface target;

    public DowloadEarthQuakesTask(Context context, AddEarthQuakeInterface target) {
        this.target = target;
        eartQuakeDB = new EarthQuakeDB(context);

    }


    //private ArrayList<EarthQuake> arr;
    public DowloadEarthQuakesTask(AddEarthQuakeInterface target) {
        this.target = target;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        Integer count = 0;

        if (urls.length > 0) {
            count = updateEarthQuake(urls[0]);


        }
        return count;
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);
        //target.addEarthQuake(earthQuakes[0]);

    }


    @Override
    protected void onPostExecute(Integer total) {
        super.onPostExecute(total);
        target.notifyTotall(total.intValue());

    }

    private Integer updateEarthQuake(String eartquakesFedd) {
        Integer count = 0;
        JSONObject json;
        //String eartquakesFedd=getString(R.string.earthquakeurl);
        try {
            URL url = new URL(eartquakesFedd);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");
                count = earthquakes.length();

                for (int i = earthquakes.length() - 1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            JSONArray jsoonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordinate coords = new Coordinate(jsoonCoords.getDouble(0), jsoonCoords.getDouble(1), jsoonCoords.getDouble(2));

            JSONObject properties = jsonObject.getJSONObject("properties");

            EarthQuake earthQuake = new EarthQuake();
            earthQuake.set_id(id);
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setTime(properties.getLong("time"));
            earthQuake.setUrl(properties.getString("url"));
            earthQuake.setCoords(coords);
            //  arr.add(0,earthQuake);
            // aa.notifyDataSetChanged();
            //para que pueda sincronizar con el thread principal, android lo hace internamente onProgresUpdate
            publishProgress(earthQuake);
            Log.d("EARTHQUAKE", earthQuake.toString());
            eartQuakeDB.insert(earthQuake);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
