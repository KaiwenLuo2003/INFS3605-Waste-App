package com.example.infs3605wasteapplicationt13a_04;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
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

import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.RetrofitOCRCall;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

        //Image to text API Tests
        getAPIModel();

        File image = new File("/storage/emulated/0/Pictures/PXL_20230926_092346453.jpg");
        uploadImg(image);





    }

    public ResponseBody uploadImg(File imgFile){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.nanonets.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestBody requestImg = RequestBody.create(MultipartBody.FORM, imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "image", imgFile.getName(), requestImg);

        RetrofitOCRCall nanonetAPI = retrofit.create(RetrofitOCRCall.class);
        Call<ResponseBody> call = nanonetAPI.getReceiptData(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.toString());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API POST Call Failed");
            }
        });
        return null;
    }

    public void getAPIModel(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.nanonets.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitOCRCall nanonetAPI = retrofit.create(RetrofitOCRCall.class);
        Call<ResponseBody> call = nanonetAPI.getModel();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d(TAG, response.toString());
                Log.d(TAG, "API GET Call Successful");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API GET Call Failed");
            }
        });
    }

}