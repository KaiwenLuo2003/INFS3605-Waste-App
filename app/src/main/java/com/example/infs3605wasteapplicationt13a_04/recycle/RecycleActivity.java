package com.example.infs3605wasteapplicationt13a_04.recycle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605wasteapplicationt13a_04.AddItemActivity;
import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.example.infs3605wasteapplicationt13a_04.recipe.RecipeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class RecycleActivity extends AppCompatActivity {
    //Declarations
    public static final String INTENT_MESSAGE = "intent_message";
    private BottomNavigationView bottomNavigationView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        //Get handle for view elements
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.homePage);

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
        Intent intent = new Intent(RecycleActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchPantryActivity(String msg) {
        System.out.println("hello");
        Intent intent = new Intent(RecycleActivity.this, PantryActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeActivity(String msg) {
        Intent intent = new Intent(RecycleActivity.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecycleActivity(String msg) {
        Intent intent = new Intent(RecycleActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(RecycleActivity.this, RecycleActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

}