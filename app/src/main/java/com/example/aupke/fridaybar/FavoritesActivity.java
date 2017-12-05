package com.example.aupke.fridaybar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ListView listView;
    private OfferAdapter adapter;
    private Intent intent = getIntent();
    private ArrayList<Offer> offerArrayList = (ArrayList<Offer>) intent.getExtras().get(OperationNames.offer);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_featured:
                    Intent intent = new Intent(FavoritesActivity.this, HotOffersActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_offers:
                    intent = new Intent(FavoritesActivity.this, allOfferActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_favorites:
                    intent = new Intent(FavoritesActivity.this, FavoritesActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);

        //BottomNavigationBar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //listview
        listView=(ListView)findViewById(R.id.favoritesView);
        adapter = new OfferAdapter(this, offerArrayList);
        listView.setAdapter(adapter);

    }

}
