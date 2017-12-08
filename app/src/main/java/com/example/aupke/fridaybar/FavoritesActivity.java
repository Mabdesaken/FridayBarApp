package com.example.aupke.fridaybar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements FavoritesOfferAdapter.OnDeleteButtonListener {

    private ListView listView;
    private FavoritesOfferAdapter adapter;
    private ArrayList<Offer> offerArrayList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_featured:
                    Intent intent = new Intent(FavoritesActivity.this, FeaturedActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_offers:
                    intent = new Intent(FavoritesActivity.this, AllOfferActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_favorites:
                    intent = new Intent(FavoritesActivity.this, FavoritesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //BottomNavigationBar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);


        View view = LayoutInflater.from(this).inflate(R.layout.action_bar_custom_empty, null);
        TextView title = view.findViewById(R.id.action_bar_title);
        title.setText(R.string.title_favorites);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(view);

        //listview


        offerArrayList = new ArrayList<>(OfferManager.getOffers(this));
        if(offerArrayList.size() == 0){
            Toast.makeText(this, "Please double tap an offer to add it to Favorites", Toast.LENGTH_LONG).show();
        }
        listView=(ListView)findViewById(R.id.favoritesView);
        adapter = new FavoritesOfferAdapter(this, offerArrayList);
        adapter.setListener(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void OnDeleteRequest(int position) {
        Offer offer = offerArrayList.remove(position);
        OfferManager.deleteOffer(offer, this);
        adapter.notifyDataSetChanged();
    }
}
