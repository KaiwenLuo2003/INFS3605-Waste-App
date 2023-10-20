package com.example.infs3605wasteapplicationt13a_04;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.NanonetClient;
import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.ReceiptResponse;
import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.RetrofitOCRCall;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //Declarations
    public static final String INTENT_MESSAGE = "intent_message";
    private BottomNavigationView bottomNavigationView;
    private RelativeLayout pantry;
    private RelativeLayout shop;
    private RelativeLayout disposalOptions;
    private RelativeLayout recipe;
    private ImageView menuBar;
    private ImageView editProfile;

    private static final String apiKey = "41d2114f-6a73-11ee-b75c-9ab569923c64";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    final static String TAG = "Main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get handle for view elements
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.homePage);
        pantry = findViewById(R.id.pantryCardView);
        disposalOptions = findViewById(R.id.disposalOptionsCardView);
        recipe = findViewById(R.id.recipeCardView);
        shop = findViewById(R.id.shopCardView);
        menuBar = findViewById(R.id.menuBarIV);
        editProfile = findViewById(R.id.editProfileIV);

        //firebase documentation: https://firebase.google.com/docs/firestore/quickstart#java
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Check and request permissions when needed
        requestCameraPermissions();
        if (!checkStoragePermissions()) {
            requestStoragePermissions();
        }


//        signOutButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                AuthUI.getInstance()
//                        .signOut(MainActivity.this)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                            }
//                        });
//            }
//        });

        shop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try{
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    //then add intent to go to next screen
                } catch (ActivityNotFoundException e){
                    Log.d(TAG, "Camera not found");
                }
            }
        });


        //How to Thread: https://stackoverflow.com/questions/3489543/how-to-call-a-method-with-a-separate-thread-in-java

        //Image to text API Tests
//        getAPIModel();
//
        File image = new File("/storage/emulated/0/Pictures/PXL_20230926_092346453.jpg");
//        uploadImg(image);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");

                OkHttpClient client2 = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart(image.getName(), image.getPath(), RequestBody.create(MEDIA_TYPE_JPG, new File(image.getPath())))
                        .build();

                Request request = new Request.Builder()
                        .url("https://app.nanonets.com/api/v2/OCR/Model/{{model_id}}/LabelFile/")
                        .post(requestBody)
                        .addHeader("Authorization", Credentials.basic("2dccb768-6e4f-11ee-9011-8676698a674c", ""))
                        .build();

                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d(TAG, "API TEST POST SUCCESSFUL: " + response.toString());
                } catch (IOException e) {
                    Log.d(TAG, "API TEST POST FAILED :(");
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();

    }


    public ResponseBody uploadImg(File imgFile){
        MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(imgFile.getName(), imgFile.getPath(), RequestBody.create(MEDIA_TYPE_JPG, new File(imgFile.getPath())))
                .build();

        NanonetClient.NanonetApiService apiService = NanonetClient.NanonetApiService.getClient().create(NanonetClient.NanonetApiService.class);
        Call<ResponseBody> call = apiService.postReceiptData("Authorization " + Credentials.basic(apiKey, ""), requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "API POST Success: " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API POST Call Failed");
            }
        });
        return null;
    }

    public void getAPIModel(){
        NanonetClient.NanonetApiService apiService = NanonetClient.NanonetApiService.getClient().create(NanonetClient.NanonetApiService.class);

        Call<ResponseBody> call = apiService.getModel("Authorization " + Credentials.basic(apiKey, ""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d(TAG, "GET Success: " + response.toString());
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API GET Call Failed");
            }
        });
    }


    public void requestCameraPermissions(){
        ActivityResultLauncher<String> cameraPermission=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    Toast.makeText(MainActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.this, "Full app functionality requires Camera Permission", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cameraPermission.launch(android.Manifest.permission.CAMERA);
    }

    private boolean checkStoragePermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false; // Permission is not granted
            }
        }
        return true; // All permissions are granted
    }

    private void requestStoragePermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };

        ActivityCompat.requestPermissions(this, permissions, STORAGE_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false; // Permission denied
                    break;
                }
            }

            if (allPermissionsGranted) {
                // Permissions granted, you can proceed with external storage operations
                Log.d(TAG, "Storage permission granted");
            } else {
                // Permissions denied, handle this situation (e.g., show an explanation, disable functionality, etc.)
                Log.d(TAG, "Storage permission denied");
            }
        }
    }


}