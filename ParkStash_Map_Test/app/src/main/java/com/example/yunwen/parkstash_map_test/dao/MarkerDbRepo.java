package com.example.yunwen.parkstash_map_test.dao;

/**
 * Created by Yunwen on 2/15/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class MarkerDbRepo {
    private MarkerDbHelper markerDbHelper;

    public MarkerDbRepo(Context context){
        markerDbHelper =new MarkerDbHelper(context);
    }

    public int insert(MarkerDb markerDb){
        //Open Db，write data
        SQLiteDatabase db= markerDbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MarkerDb.KEY_time, markerDb.time);
        values.put(MarkerDb.KEY_content, markerDb.content);
        values.put(MarkerDb.KEY_topic, markerDb.topic);
        values.put(MarkerDb.KEY_latitude, markerDb.latitude);
        values.put(MarkerDb.KEY_longitude, markerDb.longitude);
        //
        long algorithm_Id=db.insert(MarkerDb.TABLE,null,values);
        db.close();
        return (int)algorithm_Id;
    }

    public void delete(int algorithm_Id){
        SQLiteDatabase db= markerDbHelper.getWritableDatabase();
        db.delete(MarkerDb.TABLE, MarkerDb.KEY_ID+"=?", new String[]{String.valueOf(algorithm_Id)});
        db.close();
    }
    public void update(MarkerDb markerDb){
        SQLiteDatabase db= markerDbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MarkerDb.KEY_time, markerDb.time);
        values.put(MarkerDb.KEY_content, markerDb.content);
        values.put(MarkerDb.KEY_topic, markerDb.topic);
        values.put(MarkerDb.KEY_latitude, markerDb.latitude);
        values.put(MarkerDb.KEY_longitude, markerDb.longitude);
        db.update(MarkerDb.TABLE, values, MarkerDb.KEY_ID + "=?", new String[]{String.valueOf(markerDb.algorithm_ID)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getAlgorithmList(){
        SQLiteDatabase db= markerDbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MarkerDb.KEY_ID+","+
                MarkerDb.KEY_latitude+","+
                MarkerDb.KEY_longitude+","+
                MarkerDb.KEY_content+","+
                MarkerDb.KEY_topic+","+
                MarkerDb.KEY_time +" FROM "+ MarkerDb.TABLE;
        ArrayList<HashMap<String,String>> mapArrayList=new ArrayList<>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> map=new HashMap<>();
                map.put("id",cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_ID)));
                map.put("topic",cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_topic)));
                map.put("content",cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_content)));
                map.put("latitude",cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_latitude)));
                map.put("longitude",cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_longitude)));
                mapArrayList.add(map);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mapArrayList;
    }

    public MarkerDb getColumnById(int Id){
        SQLiteDatabase db= markerDbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MarkerDb.KEY_ID + "," +
                MarkerDb.KEY_latitude + "," +
                MarkerDb.KEY_longitude + "," +
                MarkerDb.KEY_content + "," +
                MarkerDb.KEY_topic + "," +
                MarkerDb.KEY_time +
                " FROM " + MarkerDb.TABLE
                + " WHERE " +
                MarkerDb.KEY_ID + "=?";
        int iCount=0;
        MarkerDb markerDb =new MarkerDb();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                markerDb.algorithm_ID =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_ID));
                markerDb.content =cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_content));
                markerDb.topic  =cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_topic));
                markerDb.time =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_time));
                markerDb.latitude =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_latitude));
                markerDb.longitude =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_longitude));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return markerDb;
    }

    public MarkerDb getValueByKey(int Id){
        SQLiteDatabase db= markerDbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MarkerDb.KEY_ID + "," +
                MarkerDb.KEY_latitude + "," +
                MarkerDb.KEY_longitude + "," +
                MarkerDb.KEY_content + "," +
                MarkerDb.KEY_topic + "," +
                MarkerDb.KEY_time +
                " FROM " + MarkerDb.TABLE
                + " WHERE " +
                MarkerDb.KEY_ID + "=?";

        int iCount=0;

        MarkerDb markerDb =new MarkerDb();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                markerDb.algorithm_ID =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_ID));
                markerDb.content =cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_content));
                markerDb.topic  =cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_topic));
                markerDb.time =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_time));
                markerDb.latitude =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_latitude));
                markerDb.longitude =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_longitude));
                Log.d("MainActivity", "The " + " Topic is: " + cursor.getString(cursor.getColumnIndex(markerDb.topic)));
                Log.d("MainActivity", "The content is: " + cursor.getString(cursor.getColumnIndex(markerDb.content)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return markerDb;
    }

    public MarkerDb getColumnByTopic(String topic){
        SQLiteDatabase db= markerDbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MarkerDb.KEY_ID + "," +
                MarkerDb.KEY_latitude + "," +
                MarkerDb.KEY_longitude + "," +
                MarkerDb.KEY_content + "," +
                MarkerDb.KEY_topic + "," +
                MarkerDb.KEY_time +
                " FROM " + MarkerDb.TABLE
                + " WHERE " +
                MarkerDb.KEY_topic + "=?";
        int iCount=0;
        MarkerDb markerDb =new MarkerDb();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(topic)});
        if(cursor.moveToFirst()){
            do{
                markerDb.algorithm_ID =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_ID));
                markerDb.content =cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_content));
                markerDb.topic  =cursor.getString(cursor.getColumnIndex(MarkerDb.KEY_topic));
                markerDb.time =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_time));
                markerDb.latitude =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_latitude));
                markerDb.longitude =cursor.getInt(cursor.getColumnIndex(MarkerDb.KEY_longitude));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return markerDb;
    }
}
