package com.example.hamisha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DriverChoiseActivity extends AppCompatActivity
{
    private CardView truckOwner,bodaOwner,truckDriver,bodaDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_choise);

        truckOwner  = (CardView) findViewById(R.id.truck_owner_view);
        bodaOwner  = (CardView) findViewById(R.id.boda_owner_view);
        truckDriver  = (CardView) findViewById(R.id.driver_view);
        bodaDriver  = (CardView) findViewById(R.id.boda_driver_view);

        truckOwner.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), TruckOwnerUploadsActivity.class));
            }
        });



        bodaOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        truckDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bodaDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
