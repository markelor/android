package com.markelmendizabal.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.markelmendizabal.earthquakes.model.EarthQuake;

import org.w3c.dom.Comment;

import java.sql.SQLException;
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
    public static final String KEY_DEPHT="depth";
    public static final String KEY_TIME="time";
    public static  final String[] allColumns = { KEY_ID,KEY_MAGNITUDE,KEY_PLACE,KEY_URL,KEY_LAT,KEY_LNG,KEY_DEPHT,KEY_TIME};




    public EarthQuakeDB(Context context) {

        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
        //getCursor();
    }




       private List<EarthQuake> query(String where,String[] whereArgs) {

            List<EarthQuake> earthQuakes = new ArrayList<EarthQuake>();

            Cursor cursor = db.query(EarthQuakeOpenHelper.DATABASE_TABLE,
                    allColumns, where, whereArgs, null, null, KEY_TIME+"DESC");

            cursor.moveToFirst();
            HashMap<String ,Integer> indexes=new HashMap<>();
            for (int i=0;i<allColumns.length;i++){
                indexes.put(allColumns[i],cursor.getColumnIndex(allColumns[i]));

            }
            while (cursor.moveToNext()) {
               EarthQuake earthQuake=new EarthQuake();
                earthQuake.set_id(cursor.getString(indexes.get(KEY_ID)));
                earthQuake.setPlace(cursor.getString(indexes.get(KEY_PLACE)));
                earthQuake.setMagnitude(cursor.getString(indexes.get(KEY_MAGNITUDE)));
                earthQuake.setCoords(cursor.getString(indexes.get(KEY_ID)));

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
        values.put(KEY_DEPHT, earthQuake.getCoords().getDepth());
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


        //private  static  final String DATABASE_CREATE= "CREATE TABLE" + DATABASE_TABLE + "_id PRIMARY KEY, place TEXT, magnitude REAL, lat REAL,long REAL, depth REAL, url TEXT, time INTEGER";
        private static final String DATABASE_CREATE = "CREATE Table " + DATABASE_TABLE +
                "(_id  TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL , lng REAL, url TEXT,depth REAL, time INTEGER)";


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

