package com.example.aupke.fridaybar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        String titleString = title.getText().toString();
        String dateString = date.getText().toString();
        String descriptionString = description.getText().toString();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Datbar").child(titleString).child("Title").setValue(titleString);
        mDatabase.child("Datbar").child(titleString).child("Date").setValue(dateString);
        mDatabase.child("Datbar").child(titleString).child("Description").setValue(descriptionString);

        Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
