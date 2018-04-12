package com.example.yunwen.parkstash_map_test.dao;

/**
 * Created by Yunwen on 2/15/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MarkerDbHelper extends SQLiteOpenHelper {
    //Db version
    private static final int DATABASE_VERSION = 1;
    //name of the db
    private static final String DATABASE_NAME = "marker.db";

    public MarkerDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
        String CREATE_TABLE_STUDENT="CREATE TABLE "+ MarkerDb.TABLE+"("
                + MarkerDb.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MarkerDb.KEY_latitude+" TEXT, "
                + MarkerDb.KEY_longitude+" TEXT, "
                + MarkerDb.KEY_topic+" TEXT, "
                + MarkerDb.KEY_time +" INTEGER, "
                + MarkerDb.KEY_content+" TEXT)";
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if the table exist, delete it
        db.execSQL("DROP TABLE IF EXISTS "+ MarkerDb.TABLE);
        //recreate the table
        onCreate(db);
    }
}
