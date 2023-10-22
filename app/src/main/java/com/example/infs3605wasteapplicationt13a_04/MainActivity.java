package com.example.infs3605wasteapplicationt13a_04;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.example.infs3605wasteapplicationt13a_04.recipe.RecipeActivity;
import com.example.infs3605wasteapplicationt13a_04.recycle.RecycleActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
    private TextView itemsExpiringNotice;
    private TextView locationInformation;

    @SuppressLint("MissingInflatedId")
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
        itemsExpiringNotice = findViewById(R.id.itemExpiringTV);
        locationInformation = findViewById(R.id.locationTV);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddItemActivity("Message from HomeActivity");
            }
        });

        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRecipeActivity("Message from HomeActivity");
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
                        System.out.print("recipe");
                        return true;
                    case R.id.pantryPage:
                        launchPantryActivity("Message from MainActivity");
                        return true;
                    case R.id.cameraPage:
                        launchAddItemActivity("Message from MainActivity");
                        return true;
                    case R.id.recipesPage:
                        launchRecipeActivity("Message from HomeActivity");
                        return true;
                    case R.id.recyclePage:
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
        System.out.println("hello");
        Intent intent = new Intent(MainActivity.this, PantryActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecycleActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, RecycleActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

}