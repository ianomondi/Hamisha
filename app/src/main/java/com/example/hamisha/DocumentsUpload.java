package com.example.hamisha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rey.material.widget.ImageView;

public class DocumentsUpload extends AppCompatActivity implements View.OnClickListener {

    LinearLayout idUpload,
            dlUpload,
            insuranceUpload,
            passUpload,
            NTSAUpload,
            nPlateUpload;
    Integer REQUEST_CAMERA = 1;

    com.rey.material.widget.ImageView activeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_upload);

        idUpload = findViewById(R.id.idUpload);
        dlUpload = findViewById(R.id.dlUpload);
        insuranceUpload = findViewById(R.id.insuranceUpload);
        passUpload = findViewById(R.id.passPortUpload);
        NTSAUpload = findViewById(R.id.NTSAUpload);
        nPlateUpload = findViewById(R.id.nPlateUpload);

        idUpload.setOnClickListener(this);
        dlUpload.setOnClickListener(this);
        insuranceUpload.setOnClickListener(this);
        passUpload.setOnClickListener(this);
        NTSAUpload.setOnClickListener(this);
        nPlateUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        cameraIntent(id);
    }

    public void cameraIntent(int viewId){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);

        LinearLayout view = (LinearLayout) findViewById(viewId);
        activeImageView = (ImageView) view.getChildAt(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_CAMERA) {

                Bundle bundle = data.getExtras();
                Bitmap imgBitmap = (Bitmap) bundle.get("data");

                activeImageView.setImageBitmap(imgBitmap);
            }
        }
    }
}
