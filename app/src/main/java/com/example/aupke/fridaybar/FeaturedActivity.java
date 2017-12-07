package com.example.aupke.fridaybar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FeaturedActivity extends AppCompatActivity implements ChildEventListener {

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

        //listview

        listView=(ListView)findViewById(R.id.featuredView);;
        adapter = new FeaturedOfferAdapter(this, featuredList);
        listView.setAdapter(adapter);

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
}
