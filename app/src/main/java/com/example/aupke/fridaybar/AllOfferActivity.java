package com.example.aupke.fridaybar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class AllOfferActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, LocationListener, ChildEventListener, AdapterView.OnItemClickListener{

    DatabaseReference dref;
    ListView listview;
    ArrayList<Offer> list = new ArrayList<>();
    ArrayList<Offer> favorites = new ArrayList<>();
    OfferAdapter adapter;
    private String descriptionString = "Debug";
    private TextView mTextMessage;
    private boolean nonDoubleClick = true;
    private long firstClickTime = 0L;
    private final int DOUBLE_CLICK_TIMEOUT = 200;

    private FusedLocationProviderClient mFusedLocationClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_featured:
                    Intent intent = new Intent(AllOfferActivity.this, FeaturedActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_offers:
                    intent = new Intent(AllOfferActivity.this, AllOfferActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_favorites:
                    intent = new Intent(AllOfferActivity.this, FavoritesActivity.class);
                    intent.putExtra(OperationNames.offer, favorites);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    };

    private Location lastKnownLocation;
    private SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeRefreshLayout;

    //oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_offers);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        getLocation();


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        listview=(ListView)findViewById(R.id.listView);
        adapter=new OfferAdapter(this, list);
        listview.setAdapter(adapter);

        //sets ActionBar
        View view = LayoutInflater.from(this).inflate(R.layout.action_bar_custom_maps, null);
        TextView title = view.findViewById(R.id.action_bar_title);
        title.setText(R.string.all_offers_title);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(view);

        dref= FirebaseDatabase.getInstance().getReference().child(OperationNames.eventRoute);

        listview.setOnItemClickListener(this);
        listview.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Intent intent = new Intent(AllOfferActivity.this, MapsActivity.class);
                intent.putExtra("list", list);
                intent.putExtra("currentPosition", lastKnownLocation);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        dref.addChildEventListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dref.removeEventListener(this);
    }

    private void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AllOfferActivity.this, perms, 1);
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lastKnownLocation = location;
                } else {
                    Log.e("FAIL IN", "getLocation");
                }
            }
        });
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(AllOfferActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }*/
    public void openSettings(View view){
        Intent intent = new Intent(AllOfferActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {

    }
    public void addOffer(View view){
        Intent intent = new Intent(AllOfferActivity.this, AddEventActivity.class);
        startActivity(intent);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        Offer offerDat = dataSnapshot.getValue(Offer.class);


        double lat = (double) offerDat.getLat();
        double lng = (double) offerDat.getLng();
        Location locationOfferDistanceToLocation = new Location("");
        locationOfferDistanceToLocation.setLatitude(lat);
        locationOfferDistanceToLocation.setLongitude(lng);

        int locationDifference = (int) locationOfferDistanceToLocation.distanceTo(lastKnownLocation);
        //String offerDistanceToLocation = String.valueOf(locationDifference);
        offerDat.setDistanceToLocation(locationDifference);
        String prefDifString = sharedPreferences.getString("pref_distance", "1000");
        if(prefDifString.isEmpty()){
            prefDifString = "1000";
        }
        float prefDif = Float.parseFloat(prefDifString);
        String prefType = sharedPreferences.getString("pref_type", "Everything");

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMAN);
        try {
            Date dato = format.parse(offerDat.getDate() + "-" + year);
            Date datoNu = format.parse(date);
            int diff = (int) (dato.getTime() - datoNu.getTime());

            //Checks if events are within preferences and within 7 days
            if (prefDif > locationDifference && diff < 518400000 && diff > 0) {
                if (prefType.equals(OperationNames.everythingType) || prefType.equals(offerDat.getType())) {
                    list.add(offerDat);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Collections.sort(list);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        synchronized(this) {
            if(firstClickTime == 0) {
                firstClickTime = SystemClock.elapsedRealtime();
                nonDoubleClick = true;
            } else {
                long deltaTime = SystemClock.elapsedRealtime() - firstClickTime;
                firstClickTime = 0;
                if(deltaTime < DOUBLE_CLICK_TIMEOUT) {
                    nonDoubleClick = false;
                    this.onItemDoubleClick(parent, view, position, id);
                    return;
                }
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if(nonDoubleClick) {
                        // logic for single click event
                        Offer offer = list.get(position);
                        String itdes = offer.getDescription();

                        Intent intent = new Intent(AllOfferActivity.this, ItemSelectedActivity.class);

                        intent.putExtra(OperationNames.offer, offer);


                        //based on offer add info to intent
                        startActivity(intent);
                    }
                }

            }, DOUBLE_CLICK_TIMEOUT);
        }
    }

    private void onItemDoubleClick(AdapterView<?> parent, View view, int position, long id) {
        //logic for double tap event
        Offer offer = list.get(position);
        OfferManager.addOffer(offer, this);

        Toasty.normal(getApplicationContext(), "",
                Toast.LENGTH_SHORT, ContextCompat.getDrawable(this, R.drawable.heart_icon)).show();
    }


    public void openMaps(View view) {
        Intent intent = new Intent(AllOfferActivity.this, MapsActivity.class);
        intent.putExtra("list", list);
        intent.putExtra("currentPosition", lastKnownLocation);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        finish();
        startActivity(getIntent());
        swipeRefreshLayout.setRefreshing(false);
        overridePendingTransition(0,0);
    }
}
