package com.example.infs3605wasteapplicationt13a_04;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.ReceiptResultActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LoadingActivity extends AppCompatActivity {

    private static final String TAG = "Loading Activity";

    private TextView recycleFact;
    private ImageView loadingGif;
    private String randomRecyclingFact;
    private int randomInt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        recycleFact = findViewById(R.id.RecycleFact);

        //add facts
        ArrayList<String> recyclingFacts = new ArrayList<>();
        recyclingFacts.add("Did you know that soft plastics cannot be recycled?\n \n" +
                "Make sure to leave your chip packets in the bin!");
        recyclingFacts.add("Tip: Make sure to clean hard plastics before you recycle them!");
        recyclingFacts.add("Did you know that there are different recycling symbols?\n \n" +
                "Check the packaging to find out how to properly dispose them!");
        recyclingFacts.add("Going on a trip with food still in the fridge? \n\n" +
                "Check the in-app map to see food donation centres near you!");

        //randomly select from arraylist
        randomInt = (int)(Math.random() * recyclingFacts.size());
        randomRecyclingFact = recyclingFacts.get(randomInt);
        Log.d(TAG, "Fact: " + randomRecyclingFact);

        //display on screen
        recycleFact.setText(randomRecyclingFact);

/*
    - receive intent from previous screen
    - delay response 1.5-2sec
    - intent to the next screen

 */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, ReceiptResultActivity.class);
                startActivity(intent);
            }
        }, 2000);


    }

}
