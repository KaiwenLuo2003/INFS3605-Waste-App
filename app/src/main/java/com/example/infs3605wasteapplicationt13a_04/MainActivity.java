package com.example.infs3605wasteapplicationt13a_04;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.infs3605wasteapplicationt13a_04.Pantry.PantryActivity;
import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.NanonetClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.ACCESS_MEDIA_LOCATION
    };

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

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddItemActivity("Message from HomeActivity");
            }
        });

        pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPantryActivity("Message from HomeActivity");
            }
        });

        //firebase documentation: https://firebase.google.com/docs/firestore/quickstart#java
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Check and request permissions when needed
        requestCameraPermissions();
        verifyStoragePermissions(MainActivity.this);


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
        getAPIModel();

        File image = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures", "PXL_20230926_092346453.jpg");
        uploadImg(image);

        //Nanonets Documentation version
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//                MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
//
//                OkHttpClient client2 = new OkHttpClient();
//
//                RequestBody requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("file", image.getPath(), RequestBody.create(MEDIA_TYPE_JPG, new File(image.getPath())))
//                        .build();
//
//                Request request = new Request.Builder()
//                        .url("https://app.nanonets.com/api/v2/OCR/Model/49289810-b2ea-4227-8e77-244ec6aec526/LabelFile/")
//                        .post(requestBody)
//                        .addHeader("Authorization", Credentials.basic("2dccb768-6e4f-11ee-9011-8676698a674c", ""))
//                        .build();
//
//                try {
//                    okhttp3.Response response = client.newCall(request).execute();
//                    Log.d(TAG, image.getName() + ": " + image.getPath());
//                    Log.d(TAG, "API TEST POST SUCCESSFUL: " + response.toString());
//                } catch (IOException e) {
//                    Log.d(TAG, "API TEST POST FAILED :(");
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        t1.start();

    }


    public ResponseBody uploadImg(File imgFile){
        MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imgFile.getPath(), RequestBody.create(MEDIA_TYPE_JPG, new File(imgFile.getPath())))
                .build();

        NanonetClient.NanonetApiService apiService = NanonetClient.NanonetApiService.getClient().create(NanonetClient.NanonetApiService.class);
        Call<ResponseBody> call = apiService.postReceiptData("Authorization " + Credentials.basic(apiKey, ""), requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, imgFile.getName() + ": " + imgFile.getPath());
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

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.d(TAG, "permission: " + permission + " and permGranted: " + PackageManager.PERMISSION_GRANTED);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    STORAGE_PERMISSION_REQUEST_CODE
            );
            Log.d(TAG, "Storage permission granted");
            Toast.makeText(activity, "Storage Permission Granted", Toast.LENGTH_SHORT).show();

        }
    }




    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homePage:
                        launchHomePageActivity("Message from MainActivity");
                        return true;
                    case R.id.pantryPage:
                        launchPantryActivity("Message from MainActivity");
                        return true;
                    case R.id.cameraPage:
                        launchAddItemActivity("Message from MainActivity");
                    case R.id.recipePage:Page:
                        launchRecipeActivity("Message from MainActivity");
                    case R.id.recyclePage:Page:
                        launchRecycleActivity("Message from MainActivity");
                        return true;
                }

                return false;
            }
        });
        return false;
    }



    //Methods to open new activities for navigation bar functionalities
    public void launchAddItemActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchPantryActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, PantryActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecycleActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

}