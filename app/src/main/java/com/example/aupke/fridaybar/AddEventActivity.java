package com.example.aupke.fridaybar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity {

    private DatePickerDialog datePicker;

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
        CheckBox exclusive = findViewById(R.id.exclusiveCheckBox);




        String barString = "HardCoded bar";
        String titleString = title.getText().toString();
        String dateString = date.getText().toString();
        String descriptionString = description.getText().toString();
        String typeString = type.getSelectedItem().toString();
        boolean checkBoolean = exclusive.isChecked();


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(OperationNames.eventRoute).push();
        Offer offer = new Offer(barString, titleString, descriptionString, typeString, dateString, 56.153553, 10.214211, checkBoolean);
        Offer offer1 = new Offer(barString,dateString,titleString,descriptionString);
        mDatabase.setValue(offer);
        mDatabase.setValue(offer1);

        Intent intent = new Intent(AddEventActivity.this, AllOfferActivity.class);
        startActivity(intent);
    }



}
