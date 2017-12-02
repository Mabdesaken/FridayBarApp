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
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<Offer> list = new ArrayList<>();
    OfferAdapter adapter;
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
    private SharedPreferences sharedPreferences;

    //oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

         sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        getLocation();


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        //gestureDetector
        final GestureDoubleTap gestureDoubleTap = new GestureDoubleTap();
        GestureDetector gd = new GestureDetector(this, gestureDoubleTap);

        listview=(ListView)findViewById(R.id.listView);
        adapter=new OfferAdapter(this, list);
        listview.setAdapter(adapter);
        dref= FirebaseDatabase.getInstance().getReference().child("Events");

        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String string = dataSnapshot.getValue(String.class);

                double lat = (double) dataSnapshot.child("Lat").getValue();
                double lng = (double) dataSnapshot.child("Lng").getValue();
                Location locationOfferDistanceToLocation = new Location("");
                locationOfferDistanceToLocation.setLatitude(lat);
                locationOfferDistanceToLocation.setLongitude(lng);
                String offerTitle = String.valueOf(dataSnapshot.child("Title").getValue());
                String offerDescription = String.valueOf(dataSnapshot.child("Description").getValue());



                Log.e("Location of event: ", locationOfferDistanceToLocation.getLatitude() + " lng: " + locationOfferDistanceToLocation.getLongitude());
                Log.e("Location of device: ", lastKnownLocation.getLatitude() + " lng: " + lastKnownLocation.getLongitude());

                int locationDifference = (int) locationOfferDistanceToLocation.distanceTo(lastKnownLocation);
                String offerDistanceToLocation = String.valueOf(locationDifference);
                Offer offer = new Offer(offerDistanceToLocation, offerTitle, offerDescription);
                Log.e("OFFER LOCATION TO", offer.getDistanceToLocation());
                String prefDifString = sharedPreferences.getString("example_text", "500");
                Log.e("Preferences", prefDifString);
                Log.e("Distance: ", locationDifference +"");
                float prefDif = Float.parseFloat(prefDifString);
                if(prefDif>locationDifference) {
                    list.add(offer);
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

                Offer item = (Offer) adapter.getItemAtPosition(position);
                String itdes = item.getDescription();
                Log.e("D", itdes);

                Intent intent = new Intent(Main2Activity.this, itemSelectedActivity.class);
                intent.putExtra("Title", item.getTitle());
                intent.putExtra("Description", item.getDescription());
                intent.putExtra("Distance", item.getDistanceToLocation());

                //based on item add info to intent
                startActivity(intent);
            }
        });
    }

    private void getLocation() {
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
                }else {
                    Log.e("FAIL IN", "getLocation");
                }
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
    public void addOffer(View view){
        Intent intent = new Intent(Main2Activity.this, AddEventActivity.class);
        startActivity(intent);
    }
}
