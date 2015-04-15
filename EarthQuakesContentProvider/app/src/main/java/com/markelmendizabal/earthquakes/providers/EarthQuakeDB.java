package com.markelmendizabal.earthquakes.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.markelmendizabal.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {
    private Context context;

    public EarthQuakeDB(Context context) {
        this.context = context;
    }

    public static final String[] allColumns = {EarthQuakesProvider.Columns.KEY_ID, EarthQuakesProvider.Columns.KEY_MAGNITUDE, EarthQuakesProvider.Columns.KEY_PLACE, EarthQuakesProvider.Columns.KEY_URL, EarthQuakesProvider.Columns.KEY_LAT, EarthQuakesProvider.Columns.KEY_LNG, EarthQuakesProvider.Columns.KEY_DEPTH, EarthQuakesProvider.Columns.KEY_TIME};

    public void insert(EarthQuake earthQuake) {
        ContentValues values = new ContentValues();
        values.put(EarthQuakesProvider.Columns.KEY_ID, earthQuake.get_id());
        values.put(EarthQuakesProvider.Columns.KEY_MAGNITUDE, earthQuake.getMagnitude());
        values.put(EarthQuakesProvider.Columns.KEY_PLACE, earthQuake.getPlace());
        values.put(EarthQuakesProvider.Columns.KEY_URL, earthQuake.getUrl());
        values.put(EarthQuakesProvider.Columns.KEY_LAT, earthQuake.getCoords().getLat());
        values.put(EarthQuakesProvider.Columns.KEY_LNG, earthQuake.getCoords().getLng());
        values.put(EarthQuakesProvider.Columns.KEY_DEPTH, earthQuake.getCoords().getDepth());
        values.put(EarthQuakesProvider.Columns.KEY_TIME, earthQuake.getTime().getTime());
        ContentResolver cr= context.getContentResolver();
        cr.insert(EarthQuakesProvider.CONTENT_URI,values);


    }
    /*public List<EarthQuake> getAll() {
        return query(null, null);
    }
    */

  public List<EarthQuake> getAllByMagnitude(int magnitude) {

      String where = EarthQuakesProvider.Columns.KEY_MAGNITUDE + ">=?";
      String[] whereArgs = {
              String.valueOf(magnitude)
      };

        return query(where, whereArgs);
    }


    private List<EarthQuake> query(String where, String[] whereArgs) {

        List<EarthQuake> earthQuakes = new ArrayList<EarthQuake>();
         ContentResolver cr= context.getContentResolver();
        String orderBy=EarthQuakesProvider.Columns.KEY_TIME+ " DESC";

        Cursor cursor = cr.query(
                EarthQuakesProvider.CONTENT_URI,
                allColumns,
                where,
                whereArgs,
               orderBy
        );

        HashMap<String, Integer> indexes = new HashMap<String, Integer>();
        for (int i = 0; i < allColumns.length; i++) {
            indexes.put(allColumns[i], cursor.getColumnIndex(allColumns[i]));

        }

        while (cursor.moveToNext()) {
            EarthQuake earthQuake = new EarthQuake();
            earthQuake.set_id(cursor.getString(indexes.get(EarthQuakesProvider.Columns.KEY_ID)));
            earthQuake.setMagnitude(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.KEY_MAGNITUDE)));
            earthQuake.setPlace(cursor.getString(indexes.get(EarthQuakesProvider.Columns.KEY_PLACE)));
            earthQuake.setUrl(cursor.getString(indexes.get(EarthQuakesProvider.Columns.KEY_URL)));
            earthQuake.getCoords().setLat(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.KEY_LAT)));
            earthQuake.getCoords().setLng(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.KEY_LNG)));
            earthQuake.getCoords().setDepth(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.KEY_DEPTH)));
            earthQuake.setTime(cursor.getLong(indexes.get(EarthQuakesProvider.Columns.KEY_TIME)));
            earthQuakes.add(earthQuake);
        }
        // make sure to close the cursor
        cursor.close();
        return earthQuakes;
    }





}

