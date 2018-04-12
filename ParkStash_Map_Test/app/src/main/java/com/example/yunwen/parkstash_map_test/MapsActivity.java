package com.example.yunwen.parkstash_map_test;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yunwen.parkstash_map_test.dao.MarkerDb;
import com.example.yunwen.parkstash_map_test.dao.MarkerDbRepo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;

    public enum AppStart {
        FIRST_TIME, FIRST_TIME_VERSION, NORMAL;
    }

    private static final String LAST_APP_VERSION = "last_app_version";

    private static final String TAG = "MapsActivity";

    private int _algorithm_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawerLayout();
    }

    /**
     * Initialization of Drawer Navigation
     */
    public void initDrawerLayout(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        setFragment();

        setDataBase();
    }


    /**
     * Initialization of MapFragment
     */
    public void setFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Initialization of MapFragment
     */
    public void setDataBase() {

        switch (checkAppStart()) {
            case NORMAL:
                // We don't want to get on the user's nerves
                break;
            case FIRST_TIME_VERSION:
                // TODO show what's new
                break;
            case FIRST_TIME:
                // Add locations to Db
                setLocations();
                break;
            default:
                break;
        }
    }

    /**
     * Set Starting Locations
     */
    public void setLocations(){
        addToDb("parking 1",37.338, - 121.884);
        addToDb("parking 2",37.336, - 121.885);
        addToDb("parking 3",37.338, - 121.889);
    }

    /**
     * Add a place to database
     * @param data: name of place
     * @param lat: latitude of place
     * @param lgt: longitude of place
     */
    private void addToDb(String data, double lat, double lgt) {
        MarkerDbRepo repo = new MarkerDbRepo(this);
        MarkerDb markerDb = repo.getColumnByTopic(data);
        try {
            if (markerDb.topic.equals(data)) {
                repo.update(markerDb);
                Toast.makeText(this, "No content", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            markerDb.time = 25;
            markerDb.content = "";
            markerDb.topic = data;
            markerDb.latitude = lat;
            markerDb.longitude = lgt;
            markerDb.algorithm_ID = _algorithm_id;
            _algorithm_id = repo.insert(markerDb);
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * List all data from database
     */
    public void listAll(){
        int _algorithm_id = 0;
        MarkerDbRepo repo = new MarkerDbRepo(this);
        MarkerDb markerDb = new MarkerDb();
        markerDb = repo.getColumnById(_algorithm_id);
        ArrayList<HashMap<String, String>> mapArrayList =  repo.getMapArrayList();
        if(mapArrayList.size()!=0) {
            //Show Db list
            for(int i = 0; i < mapArrayList.size();i++){
                Log.d(TAG,"topic is: " + mapArrayList.get(i).get("topic"));
                Log.d(TAG,"Latitude is: " + mapArrayList.get(i).get("latitude"));
            }

        }else{
            Toast.makeText(this, "No content", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setLocations();
        //List All the Markers
        // Add a marker in Sydney and move the camera
        LatLng lastPlace = new LatLng(37.341, -121.879);
        mMap.addMarker(new MarkerOptions().position(lastPlace).title("Parkstash"));
        listAll();
        //Latitude, Longitude, title
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPlace,15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_book) {
            // Handle the book action
        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public AppStart checkAppStart() {
        PackageInfo pInfo;
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        AppStart appStart = AppStart.NORMAL;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int lastVersionCode = sharedPreferences
                    .getInt(LAST_APP_VERSION, -1);
            int currentVersionCode = pInfo.versionCode;
            appStart = checkAppStart(currentVersionCode, lastVersionCode);
            // Update version in preferences
            sharedPreferences.edit()
                    .putInt(LAST_APP_VERSION, currentVersionCode).apply();
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Check Start",
                    "Unable to determine current app version from pacakge manager. Defenisvely assuming normal app start.");
        }
        return appStart;
    }

    public AppStart checkAppStart(int currentVersionCode, int lastVersionCode) {
        if (lastVersionCode == -1) {
            return AppStart.FIRST_TIME;
        } else if (lastVersionCode < currentVersionCode) {
            return AppStart.FIRST_TIME_VERSION;
        } else if (lastVersionCode > currentVersionCode) {
            Log.d("Check Start", "Current version code (" + currentVersionCode
                    + ") is less then the one recognized on last startup ("
                    + lastVersionCode
                    + "). Defenisvely assuming normal app start.");
            return AppStart.NORMAL;
        } else {
            return AppStart.NORMAL;
        }
    }
}
