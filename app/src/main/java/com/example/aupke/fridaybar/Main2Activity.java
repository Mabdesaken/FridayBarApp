package com.example.aupke.fridaybar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements LocationListener {

    DatabaseReference dref;
    ListView listview;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private String descriptionString = "Debug";
    private TextView mTextMessage;

    private FusedLocationProviderClient mFusedLocationClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_featured:
                    Intent intent = new Intent(Main2Activity.this, HotOffersActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_offers:
                    intent = new Intent(Main2Activity.this, Main2Activity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_favorites:
                    intent = new Intent(Main2Activity.this, FavoritesActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };
    private Location lastKnownLocation;

    //oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(descriptionString, "Permissions not granted");
            ActivityCompat.requestPermissions(Main2Activity.this, perms, 1);
            return;
        }
        Log.e(descriptionString, "Succes: Permissions Granted");
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){

                    lastKnownLocation = location;
                }
            }
        });


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        listview=(ListView)findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        listview.setAdapter(adapter);
        dref= FirebaseDatabase.getInstance().getReference().child("Events");
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String string = dataSnapshot.getValue(String.class);

                double lat = (double) dataSnapshot.child("Lat").getValue();
                double lng = (double) dataSnapshot.child("Lng").getValue();
                Location location = new Location("");
                location.setLatitude(lat);
                location.setLongitude(lng);


                Log.e("Location of event: ", location.getLatitude() + " lng: " + location.getLongitude());
                Log.e("Location of device: ", lastKnownLocation.getLatitude() + " lng: " + lastKnownLocation.getLongitude());

                float locationDifference = location.distanceTo(lastKnownLocation);
                float hardCodedDistance = 500;

                String prefDifString = sharedPreferences.getString("example_text", "500");
                Log.e("Preferences", prefDifString);
                Log.e("Distance: ", locationDifference +"");
                float prefDif = Float.parseFloat(prefDifString);
                if(prefDif>locationDifference) {
                    list.add((String) dataSnapshot.child("Title").getValue());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                String item = String.valueOf(adapter.getItemAtPosition(position));
                Log.e("D", item);
                /*dref.child(item).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for ( DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if(dataSnapshot1.getKey().equals("Description")){
                                descriptionString = String.valueOf(dataSnapshot1.getValue());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Log.e("DES", descriptionString);
                */
                Intent intent = new Intent(Main2Activity.this, itemSelectedActivity.class);
                String description = dref.child(item).child("Description").getKey();
                Log.e("D", description);
                String date = String.valueOf(dref.child(item).child("Date"));
                intent.putExtra("Description", description);
                intent.putExtra("Date", date);
                //based on item add info to intent
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == R.id.action_settings){
            Intent intent = new Intent(Main2Activity.this, SettingsActivity.class);
            startActivity(intent);
            return  true;
        }
    return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
