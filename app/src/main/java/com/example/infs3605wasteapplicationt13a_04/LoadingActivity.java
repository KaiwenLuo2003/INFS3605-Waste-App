package com.example.infs3605wasteapplicationt13a_04;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    private TextView recycleFact;
    private ImageView loadingGif;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        recycleFact = findViewById(R.id.RecycleFact);
        recycleFact.setText("Did you know that soft plastics cannot be recycled?\n \n" +
                "Make sure to leave your chip packets in the bin!");

/*
    - receive intent from previous screen
    - delay response 1.5-2sec
    - intent to the next screen
 */
//        try {
//            wait(2);
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


    }

}
