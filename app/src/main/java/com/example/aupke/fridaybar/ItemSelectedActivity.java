package com.example.aupke.fridaybar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ItemSelectedActivity extends AppCompatActivity {

    private String lat;
    private String lng;
    private String barName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);

        View view = LayoutInflater.from(this).inflate(R.layout.action_bar_custom_empty, null);
        TextView titleBar = view.findViewById(R.id.action_bar_title);
        titleBar.setText(R.string.title_offer_selected);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(view);

        TextView title = findViewById(R.id.is_TitleText);
        TextView bar = findViewById(R.id.is_BarText);
        TextView date = findViewById(R.id.is_DateText);
        TextView description = findViewById(R.id.is_DescriptionText);
        TextView distance = findViewById(R.id.is_DistanceText);
        Intent intent = getIntent();
        Offer offer = (Offer) intent.getSerializableExtra(OperationNames.offer);
        lat = String.valueOf(offer.getLat());
        lng = String.valueOf(offer.getLng());
        barName = String.valueOf(offer.getBar());

        title.setText(offer.getTitle());
        description.setText(offer.getDescription());
        date.setText(offer.getDate());
        Log.e("Distance", String.valueOf(offer.getDistanceToLocation()));
        distance.setText(offer.getDistanceToLocation() + "m");
        bar.setText(barName);

    }



    public void goBack(View view){
        super.onBackPressed();
    }

    public void openMapsWithDirections(View view){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" +lat + "," + lng + " (" + barName + ")"));
        startActivity(intent);
    }
}
