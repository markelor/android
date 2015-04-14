package com.markelmendizabal.earthquakes.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import com.markelmendizabal.earthquakes.MainActivity;
import com.markelmendizabal.earthquakes.R;
import com.markelmendizabal.earthquakes.database.EarthQuakeDB;
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

public class DownloadEarthQuakesService extends Service {
    private EarthQuakeDB earthQuakeDB;

    @Override
    public void onCreate() {
        super.onCreate();
        earthQuakeDB = new EarthQuakeDB(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                updateEarthQuake(getString(R.string.earthquakeurl));
            }
        });
        t.start();

        return Service.START_STICKY;
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
            sendNotification(count);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void sendNotification(Integer count) {
        Intent intentToFire= new Intent(this, MainActivity.class);
        PendingIntent activytyIntent=PendingIntent.getActivity(this,0,intentToFire,0);
        Notification.Builder builder	=
                new	Notification.Builder(DownloadEarthQuakesService.this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.num_earthquakes,count))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSound(
                        RingtoneManager.getDefaultUri(
                                RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(activytyIntent)
                .setAutoCancel(true);

        Notification	notification	=	builder.build();
        NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        int	NOTIFICATION_REF	=	1;
        notificationManager.notify(NOTIFICATION_REF,notification);
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
            Log.d("EARTHQUAKE", earthQuake.toString());
            this.earthQuakeDB.insert(earthQuake);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
