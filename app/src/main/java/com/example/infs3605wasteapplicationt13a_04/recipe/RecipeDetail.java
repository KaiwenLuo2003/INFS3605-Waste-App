package com.example.infs3605wasteapplicationt13a_04.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605wasteapplicationt13a_04.AddItemActivity;
import com.example.infs3605wasteapplicationt13a_04.MainActivity;
import com.example.infs3605wasteapplicationt13a_04.MapActivity;
import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class RecipeDetail extends AppCompatActivity {

    public static final String INTENT_MESSAGE = "intent_message";
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        System.out.println("loaded detail recipe");


        //Get handle for view elements
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.recipesPage);


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
        Intent intent = new Intent(RecipeDetail.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchPantryActivity(String msg) {
        System.out.println("hello");
        Intent intent = new Intent(RecipeDetail.this, PantryActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeActivity(String msg) {
        Intent intent = new Intent(RecipeDetail.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecycleActivity(String msg) {
        Intent intent = new Intent(RecipeDetail.this, MapActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(RecipeDetail.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }
}
