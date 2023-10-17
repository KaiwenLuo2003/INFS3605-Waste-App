package com.example.infs3605wasteapplicationt13a_04;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.RetrofitOCRCall;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

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


    static final int REQUEST_IMAGE_CAPTURE = 1;
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


        //retrofit version
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.nanonets.com/api/v2/OCR/Model/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitOCRCall nanonetAPI = retrofit.create(RetrofitOCRCall.class);
        Call<ResponseBody> call = nanonetAPI.getModel();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API Call Failed");
            }
        });


    }

    public void uploadImgtoAPI(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.nanonets.com/api/v2/OCR/Model/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitOCRCall nanonetAPI = retrofit.create(RetrofitOCRCall.class);
        Call<ResponseBody> call = nanonetAPI.uploadImgFile();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API Call Failed");
            }
        });
    }

    public void getReceiptDataAPI(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.nanonets.com/api/v2/OCR/Model/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitOCRCall nanonetAPI = retrofit.create(RetrofitOCRCall.class);
        Call<ResponseBody> call = nanonetAPI.getReceiptData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API Call Failed");
            }
        });
    }


//        OkHttpClient client = new OkHttpClient();
    //basic version API Call w/ Threads
//        Thread thread = new Thread(new Runnable(){
//            @Override
//            public void run(){
//                Request request = new Request.Builder()
//                                .url("https://app.nanonets.com/api/v2/OCR/Model/49289810-b2ea-4227-8e77-244ec6aec526")
//                                .get()
//                                .addHeader("authorization", Credentials.basic("41d2114f-6a73-11ee-b75c-9ab569923c64", ""))
//                                .build();
//                try {
//                    Response response = client.newCall(request).execute();
//                    String result = response.toString();
//                    Log.d(TAG, "API Call Successful");
//                    Log.d(TAG, result);
//                } catch (IOException e) {
//                    Log.d(TAG, "API Call Unsuccessful");
//                    throw new RuntimeException(e);
//                        }
//            }
//        });
//        thread.start();


}