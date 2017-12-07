package com.example.aupke.fridaybar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class itemSelectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);

        TextView title = findViewById(R.id.is_TitleText);
        TextView bar = findViewById(R.id.is_BarText);
        TextView date = findViewById(R.id.is_DateText);
        TextView description = findViewById(R.id.is_DescriptionText);
        TextView distance = findViewById(R.id.is_DistanceText);
        Intent intent = getIntent();
        Offer offer = (Offer) intent.getSerializableExtra(OperationNames.offer);

        title.setText(offer.getTitle());
        description.setText(offer.getDescription());
        date.setText(offer.getDate());
        Log.e("Distance", String.valueOf(offer.getDistanceToLocation()));
        distance.setText(offer.getDistanceToLocation() + "m");
        bar.setText(offer.getBar());

    }



    public void goBack(View view){
        Intent intent = new Intent(itemSelectedActivity.this, allOfferActivity.class);
        startActivity(intent);
    }
}
