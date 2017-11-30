package com.example.aupke.fridaybar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class itemSelectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);

        TextView date = findViewById(R.id.DateText);
        TextView description = findViewById(R.id.DescriptionText);
        if(getIntent().hasExtra("Date"))date.setText(getIntent().getExtras().getString("Date"));
        if(getIntent().hasExtra("Description"))description.setText(getIntent().getExtras().getString("Description"));

    }

    public void goBack(View view){
        Intent intent = new Intent(itemSelectedActivity.this, Main2Activity.class);
        startActivity(intent);
    }
}
