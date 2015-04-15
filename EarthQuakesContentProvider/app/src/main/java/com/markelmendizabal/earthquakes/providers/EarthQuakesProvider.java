package com.markelmendizabal.earthquakes.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class EarthQuakesProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://com.markelmendizabal.provider.earthquakes/earthquakes");
    private static final int ALL_ROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final UriMatcher uriMatcher;
    private EarthQuakeOpenHelper helper;
    //public static final String[] allColumns = {KEY_ID, KEY_MAGNITUDE, KEY_PLACE, KEY_URL, KEY_LAT, KEY_LNG, KEY_DEPTH, KEY_TIME};

    public static class Columns implements BaseColumns {
        public static final String KEY_ID = "_id";
        public static final String KEY_MAGNITUDE = "magnitude";
        public static final String KEY_PLACE = "place";
        public static final String KEY_URL = "url";
        public static final String KEY_LAT = "lat";
        public static final String KEY_LNG = "lng";
        public static final String KEY_DEPTH = "depth";
        public static final String KEY_TIME = "time";

    }



    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.markelmendizabal.provider.earthquakes",
                "earthquakes", ALL_ROWS);

        uriMatcher.addURI("com.markelmendizabal.provider.earthquakes",
                "earthquakes/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        helper = new EarthQuakeOpenHelper(getContext(), EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        return false;
    }


    public EarthQuakesProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        switch	(uriMatcher.match(uri))	{

            case	ALL_ROWS:
                return	"vnd.android.cursor.dir/vnd.markelmendizabal.provider.earthquakes";
            case	SINGLE_ROW:
                return	"vnd.android.cursor.item/vnd.markelmendizabal.provider.earthquakes";
            default:
                throw	new	IllegalArgumentException("Unsupported URI:"	+ uri);

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert(EarthQuakeOpenHelper.DATABASE_TABLE, null, values);
        if (id > -1) {
            //	Construct	and	return	the	URI	of	the	newly	inserted	row.
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            //	Notify	any	observers	of	the	change	in	the	data	set.
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        } else {
            return null;
        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();

        } catch (SQLiteException ex) {
            db = helper.getReadableDatabase();

        }
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {

            case SINGLE_ROW:

                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(Columns._ID + "=?");
                selectionArgs = new String[]{rowID};

            default:
                break;

        }
        queryBuilder.setTables(EarthQuakeOpenHelper.DATABASE_TABLE);
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthQuakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;


        private static final String DATABASE_CREATE = "CREATE Table " + DATABASE_TABLE +
                " (" +
                Columns.KEY_ID + " TEXT PRIMARY KEY," +
                Columns.KEY_PLACE + " TEXT," +
                Columns.KEY_MAGNITUDE + " REAL," +
                Columns.KEY_LAT + " REAL," +
                Columns.KEY_LNG + " REAL," +
                Columns.KEY_DEPTH + " REAL," +
                Columns.KEY_URL + " TEXT," +
                Columns.KEY_TIME + " INTEGER)";


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
