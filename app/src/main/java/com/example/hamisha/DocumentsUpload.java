package com.example.hamisha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hamisha.adapter.GridAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rey.material.widget.ImageView;
import com.rey.material.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class DocumentsUpload extends AppCompatActivity {

    LinearLayout idUpload,
            dlUpload,
            insuranceUpload,
            passUpload,
            NTSAUpload,
            nPlateUpload;

    Integer REQUEST_CAMERA = 1;

    com.rey.material.widget.ImageView activeImageView;

    private GridView gridView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_upload);


        gridView = findViewById(R.id.uploadItemsGrid);
        fab = findViewById(R.id.uploadFab);

        GridAdapter adapter = new GridAdapter(this);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cameraIntent(view);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDocuments();
            }
        });
    }

    private void uploadDocuments() {
        if (fetchDocuments() != null){

        }else {
            Toast.makeText(this,"Upload all documents",Toast.LENGTH_SHORT).show();
        }
    }

    private HashMap<String, String> fetchDocuments() {

        HashMap<String,String> documentsMap = new HashMap<>();
        int documentsCount =  gridView.getChildCount();

        for (int i = 0; i < documentsCount; i ++){
            Bitmap imageBMap;
            String documentName;
            CardView cardView = (CardView) gridView.getChildAt(i);
            LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
            TextView textView= (TextView)linearLayout.getChildAt(1);
            ImageView imageView= (ImageView) linearLayout.getChildAt(0);
            documentName = textView.getText().toString();

            Drawable drawable = imageView.getDrawable();

            if (drawable instanceof BitmapDrawable){
                imageBMap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                String imageBitMapinString = getBitMapString(imageBMap);
                documentsMap.put(documentName,imageBitMapinString);
            }
        }

        if (documentsMap.size() != documentsCount){
            return null;
        }
        return documentsMap;
    }

    private String getBitMapString(Bitmap imageBMap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBMap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagebytes,Base64.DEFAULT);
    }


    public void cameraIntent(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);

        int viewId = v.getId();
        CardView view = (CardView) v;
        LinearLayout linearLayout = (LinearLayout) view.getChildAt(0);
        activeImageView = (ImageView) linearLayout.getChildAt(0);
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
