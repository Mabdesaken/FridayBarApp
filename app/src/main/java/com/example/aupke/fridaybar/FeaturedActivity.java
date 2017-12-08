package com.example.aupke.fridaybar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FeaturedActivity extends AppCompatActivity implements ChildEventListener, AdapterView.OnItemClickListener {

    private TextView mTextMessage;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_featured:
                    Intent intent = new Intent(FeaturedActivity.this, FeaturedActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_offers:
                    intent = new Intent(FeaturedActivity.this, allOfferActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_favorites:
                    intent = new Intent(FeaturedActivity.this, FavoritesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    };
    private FeaturedOfferAdapter adapter;
    private ListView listView;
    private DatabaseReference dref;
    private ArrayList<Offer> featuredList = new ArrayList<>();
    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        getLocation();

        View view = LayoutInflater.from(this).inflate(R.layout.action_bar_custom_empty, null);
        TextView title = view.findViewById(R.id.action_bar_title);
        title.setText(R.string.title_features);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(view);

        //listview

        listView=(ListView)findViewById(R.id.featuredView);;
        adapter = new FeaturedOfferAdapter(this, featuredList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        dref= FirebaseDatabase.getInstance().getReference().child(OperationNames.eventRoute);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Offer offer = dataSnapshot.getValue(Offer.class);
        Log.e("CHILDADDED", String.valueOf(offer));
        if(offer.isChecked()) {
            Log.e("CHECKED", "TRUE");
            featuredList.add(offer);
        }
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
    protected void onResume() {
        super.onResume();
        featuredList.clear();
        dref.addChildEventListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dref.removeEventListener(this);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("Adapter", "open");
        Log.e("Location", String.valueOf(lastKnownLocation));
        Offer offer = featuredList.get(i);
        //Distance to Location calculation
        Location offerLocation = new Location("");
        offerLocation.setLatitude(offer.getLat());
        offerLocation.setLongitude(offer.getLng());

        int distance = (int) offerLocation.distanceTo(lastKnownLocation);
        offer.setDistanceToLocation(distance);
        Intent intent = new Intent(FeaturedActivity.this, itemSelectedActivity.class);
        intent.putExtra(OperationNames.offer,offer);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    private void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(FeaturedActivity.this, perms, 1);
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.e("Location", "UPDATED");
                    lastKnownLocation = location;
                } else {
                    Log.e("FAIL IN", "getLocation");
                }
            }
        });
    }
}
