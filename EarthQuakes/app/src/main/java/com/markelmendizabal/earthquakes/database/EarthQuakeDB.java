package com.markelmendizabal.earthquakes.database;

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

    private final SQLiteDatabase db;
    private EarthQuakeOpenHelper helper;
    //http://www.vogella.com/tutorials/AndroidSQLite/article.html

    //colum names
    public static final String KEY_ID="_id";
    public static final String KEY_MAGNITUDE="magnitude";
    public static final String KEY_PLACE="place";
    public static final String KEY_URL="url";
    public static final String KEY_LAT="lat";
    public static final String KEY_LNG="lng";
    public static final String KEY_DEPTH="depth";
    public static final String KEY_TIME="time";
    public static  final String[] allColumns = { KEY_ID,KEY_MAGNITUDE,KEY_PLACE,KEY_URL,KEY_LAT,KEY_LNG,KEY_DEPTH,KEY_TIME};




    public EarthQuakeDB(Context context) {

        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    public List<EarthQuake> getAll() {
        return query(null,null);
    }

    public List<EarthQuake> getAllByMagnitude(int magnitude) {
        String where=KEY_MAGNITUDE + ">=?";
        String [] whereArgs={
                String.valueOf(magnitude)
        };

        return query(where,whereArgs);
    }

    private List<EarthQuake> query(String where,String[] whereArgs) {

        List<EarthQuake> earthQuakes = new ArrayList<EarthQuake>();


        Cursor cursor = db.query(
                EarthQuakeOpenHelper.DATABASE_TABLE,
                allColumns,
                where,
                whereArgs,
                null,
                null,
                KEY_TIME + " DESC"
        );

        HashMap<String ,Integer> indexes=new HashMap<>();
        for (int i=0;i<allColumns.length;i++){
            indexes.put(allColumns[i],cursor.getColumnIndex(allColumns[i]));

        }

        while (cursor.moveToNext()) {
            EarthQuake earthQuake=new EarthQuake();
            earthQuake.set_id(cursor.getString(indexes.get(KEY_ID)));
            earthQuake.setMagnitude(cursor.getDouble(indexes.get(KEY_MAGNITUDE)));
            earthQuake.setPlace(cursor.getString(indexes.get(KEY_PLACE)));
            earthQuake.setUrl(cursor.getString(indexes.get(KEY_URL)));
            earthQuake.getCoords().setLat(cursor.getDouble(indexes.get(KEY_LAT)));
            earthQuake.getCoords().setLng(cursor.getDouble(indexes.get(KEY_LNG)));
            earthQuake.getCoords().setDepth(cursor.getDouble(indexes.get(KEY_DEPTH)));
            earthQuake.setTime(cursor.getLong(indexes.get(KEY_TIME)));
            earthQuakes.add(earthQuake);
        }
        // make sure to close the cursor
        cursor.close();
        return earthQuakes;
    }





    public void insert(EarthQuake earthQuake) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, earthQuake.get_id());
        values.put(KEY_MAGNITUDE, earthQuake.getMagnitude());
        values.put(KEY_PLACE, earthQuake.getPlace());
        values.put(KEY_URL, earthQuake.getUrl());
        values.put(KEY_LAT, earthQuake.getCoords().getLat());
        values.put(KEY_LNG, earthQuake.getCoords().getLng());
        values.put(KEY_DEPTH, earthQuake.getCoords().getDepth());
        values.put(KEY_TIME, earthQuake.getTime().getTime());
        //	Insert	the	row	into	your	tabled
        try{
            db.insertOrThrow(EarthQuakeOpenHelper.DATABASE_TABLE, null, values);

        }catch (SQLiteException ex){

        }

    }


    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthQuakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;



        private static final String DATABASE_CREATE = "CREATE Table " + DATABASE_TABLE +
                " (" +
                KEY_ID + " TEXT PRIMARY KEY," +
                KEY_PLACE + " TEXT," +
                KEY_MAGNITUDE + " REAL," +
                KEY_LAT + " REAL," +
                KEY_LNG + " REAL," +
                KEY_DEPTH + " REAL," +
                KEY_URL + " TEXT," +
                KEY_TIME + " INTEGER)";


        public EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

