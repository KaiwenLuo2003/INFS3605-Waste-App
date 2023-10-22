package com.example.infs3605wasteapplicationt13a_04;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "intent_message";
    private BottomNavigationView bottomNavigationView;
    private SurfaceView surfaceView;
    private String currentPhotoPath;

    private static final int REQUEST_IMAGE_CAPTURE = 201;
    private TextView receiptText;
    final static String TAG = "AddItemsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_item);

        //Get handle for view elements
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cameraPage);

        //init objects
        LinearLayout scanButton = findViewById(R.id.scanItemLinearLayout);
        ImageView imageFilterButton = findViewById(R.id.imageFilterButton);


        Log.d(TAG, "actual context: " + AddItemActivity.this.getApplicationContext().toString());
        Context context = AddItemActivity.this.getApplicationContext();

        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        Log.d(TAG, "photoFile created");
                    } catch (IOException ex) {
                        Log.d(TAG, "Error occcured while creating the File");
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Log.d(TAG, "photo file exists");
                        Uri photoURI = FileProvider.getUriForFile(AddItemActivity.this,
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        Log.d(TAG, "picture taken: " + photoURI);


                        //set imageview as pic
                        imageFilterButton.setImageURI(photoURI);
                        //sometimes works?? sometimes doesn't?? I think the file is being created tho
                    }
                }



            }
        });




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir("/storage/emulated/0/Pictures"); //File path: /storage/emulated/0/Pictures/IMG_20231014_171606.jpg
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );



        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "image file created at : " + currentPhotoPath);
        return image;
    }

}