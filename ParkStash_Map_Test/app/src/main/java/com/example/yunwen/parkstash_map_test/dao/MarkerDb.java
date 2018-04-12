package com.example.yunwen.parkstash_map_test.dao;

/**
 * Created by Yunwen on 2/15/2016.
 */

//this is the database for storing marker list
public class MarkerDb {
    //table name
    public static final String TABLE="Marker";

    //table id
    public static final String KEY_ID="id";
    public static final String KEY_topic="topic";
    public static final String KEY_content="content";
    public static final String KEY_time ="time";
    public static final String KEY_latitude = "latitude";
    public static final String KEY_longitude = "longitude";

    //property
    public int algorithm_ID;
    public String topic;
    public String content;
    public int time;
    public int latitude;
    public int longitude;
}
