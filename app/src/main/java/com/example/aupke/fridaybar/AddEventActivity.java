package com.example.aupke.fridaybar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);



    }

    public void confirmAddEvent(View view){
        TextView title = findViewById(R.id.addEventTitel);
        TextView date = findViewById(R.id.addEventDate);
        TextView description = findViewById(R.id.addEventDescription);
        Spinner type = findViewById(R.id.addType);



        String barString = "HardCoded bar";
        String titleString = title.getText().toString();
        String dateString = date.getText().toString();
        String descriptionString = description.getText().toString();
        String typeString = type.getSelectedItem().toString();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(OperationNames.eventRoute).push();
        Offer offer = new Offer(barString, titleString, descriptionString, typeString, dateString, 56.153553, 10.214211);
        mDatabase.setValue(offer);

        Intent intent = new Intent(AddEventActivity.this, allOfferActivity.class);
        startActivity(intent);
    }


}
