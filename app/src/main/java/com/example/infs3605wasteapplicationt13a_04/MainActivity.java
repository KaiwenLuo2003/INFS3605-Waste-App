package com.example.infs3605wasteapplicationt13a_04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

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
        menuBar = findViewById(R.id.menuBarIV);
        editProfile = findViewById(R.id.editProfileIV);

        //firebase documentation: https://firebase.google.com/docs/firestore/quickstart#java
        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }
}