package com.example.infs3605wasteapplicationt13a_04.pantry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.AddItemActivity;
import com.example.infs3605wasteapplicationt13a_04.DBHandler;
import com.example.infs3605wasteapplicationt13a_04.EditItemActivity;
import com.example.infs3605wasteapplicationt13a_04.LoginActivity;
import com.example.infs3605wasteapplicationt13a_04.MainActivity;
import com.example.infs3605wasteapplicationt13a_04.MapActivity;
import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;
import com.example.infs3605wasteapplicationt13a_04.recipe.RecipeActivity;
import com.example.infs3605wasteapplicationt13a_04.ui.SpacingItemDecorator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class PantryActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private static PantryAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<IngredientItem> pantryList = new ArrayList<>();

    private final DBHandler dbHandler = new DBHandler(PantryActivity.this);

    private SQLiteDatabase db;

    public static String ITEM_TAG = "Pantry Item";
    public static String ACTIVITY_INDICATOR = "Activity Indicator";
    public static int activityIndicator = 2;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        setTitle("Pantry");

        db = dbHandler.getReadableDatabase();
        pantryList = getPantryItems();

        //To editable detail screen
        PantryAdapter.ItemClickListener listener = new PantryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, String itemName) {
                Intent intent = new Intent(PantryActivity.this, EditItemActivity.class);
                intent.putExtra(ITEM_TAG, itemName);
                intent.putExtra(ACTIVITY_INDICATOR, activityIndicator);
                startActivity(intent);
            }
        };

        //Get handle for view elements
        recyclerView = findViewById(R.id.rvPantryList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.pantryPage);

        //Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PantryAdapter(this, pantryList, listener);
        recyclerView.setAdapter(mAdapter);


        //Format the recycler view for readability and aesthetics
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(60, 50);
        recyclerView.addItemDecoration(itemDecorator);


    }

    public ArrayList<IngredientItem> getPantryItems(){
        ArrayList<IngredientItem> results = dbHandler.getItems();
        return results;
    }

    public static void updateRecyclerView(DBHandler dbHandler){
        pantryList.clear();
        pantryList = dbHandler.getItems();
        mAdapter.updateList(pantryList);
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
                }

                return false;
            }
        });
        return false;
    }

//    @Override
//    // React to user interaction with the menu
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.alphabetSortItem:
//                Collections.sort(pantryList, new Comparator<IngredientItem>() {
//                    @Override
//                    public int compare(IngredientItem t1, IngredientItem t2) {
//                        return t1.getItemName().compareToIgnoreCase(t2.getItemName());
//                    }
//                });
//                mAdapter.notifyDataSetChanged();
//            case R.id.expirySortItem:
//                Collections.sort(pantryList, new Comparator<IngredientItem>() {
//                    @Override
//                    public int compare(IngredientItem t1, IngredientItem t2) {
//                        return t1.getExpiryDate().compareTo(t2.getExpiryDate());
//                    }
//                });
//                mAdapter.notifyDataSetChanged();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    // React to user interaction with the menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accountMenuItem:
                return true;
            case R.id.logOutMenuItem:
                FirebaseAuth.getInstance().signOut();
                returnToLogIn("Message from HomeActivity");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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



    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(PantryActivity.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void returnToLogIn(String msg) {
        Intent intent = new Intent(PantryActivity.this, LoginActivity.class);
        intent.putExtra(LoginActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }
}