package com.example.infs3605wasteapplicationt13a_04.pantry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.AddItemActivity;
import com.example.infs3605wasteapplicationt13a_04.MainActivity;
import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.recycle.RecycleActivity;
import com.example.infs3605wasteapplicationt13a_04.recipe.RecipeActivity;
import com.example.infs3605wasteapplicationt13a_04.ui.SpacingItemDecorator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity implements RecyclerViewAdapterPantryView.ItemClickListener{
    public static final String INTENT_MESSAGE = "intent_message";
    private BottomNavigationView bottomNavigationView;

    private RecyclerViewAdapterPantryView adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        //Temporary data to populate recyclerview
        ArrayList<String> pantryItemNames = new ArrayList<>();
        pantryItemNames.add("Chocolate");
        pantryItemNames.add("Cheese");
        pantryItemNames.add("Lemon");

        //Get handle for view elements
        recyclerView = findViewById(R.id.rvPantryList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.pantryPage);

        //Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterPantryView(this, pantryItemNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        //Format the recycler view for readibility and aesthetics
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(60, 50);
        recyclerView.addItemDecoration(itemDecorator);


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
        Intent intent = new Intent(PantryActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchPantryActivity(String msg) {
        Intent intent = new Intent(PantryActivity.this, PantryActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeActivity(String msg) {
        Intent intent = new Intent(PantryActivity.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecycleActivity(String msg) {
        Intent intent = new Intent(PantryActivity.this, RecycleActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(PantryActivity.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}