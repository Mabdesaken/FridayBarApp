package com.example.aupke.fridaybar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class itemSelectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);

        TextView date = findViewById(R.id.DateText);
        TextView description = findViewById(R.id.DescriptionText);
        Intent intent = getIntent();
        Offer offer = (Offer) intent.getSerializableExtra(OperationNames.offer);
        date.setText(offer.getDate());
        description.setText(OperationNames.descriptionField);

    }

    public void goBack(View view){
        Intent intent = new Intent(itemSelectedActivity.this, allOfferActivity.class);
        startActivity(intent);
    }
}
