package com.example.hamisha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

public class TruckOwnerUploadsActivity extends AppCompatActivity
{
    private Spinner makeSpinner, modelSpinner;
    private Button buttonToUploads ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_owner_uploads);

        buttonToUploads = findViewById(R.id.toUploadsBtn);

        buttonToUploads.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DocumentsUpload.class));
        });


        MaterialSpinner makeSpinner = (MaterialSpinner) findViewById(R.id.make_spinner);
        makeSpinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        makeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        MaterialSpinner modelSpinner = (MaterialSpinner) findViewById(R.id.model_spinner);
        modelSpinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        modelSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
    }


}
