package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.AddItemActivity;
import com.example.infs3605wasteapplicationt13a_04.MainActivity;
import com.example.infs3605wasteapplicationt13a_04.MapActivity;
import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;
import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.example.infs3605wasteapplicationt13a_04.recipe.RecipeActivity;
import com.example.infs3605wasteapplicationt13a_04.ui.SpacingItemDecorator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReceiptResultActivity extends AppCompatActivity implements RecyclerViewAdapterReceiptResultView.ItemClickListener{
    private BottomNavigationView bottomNavigationView;

    private RecyclerViewAdapterReceiptResultView adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_result);


        ArrayList<IngredientItem> receiptResultItems = createReceiptItemsList();


        //Get handle for view elements
        recyclerView = findViewById(R.id.rvReceiptResultList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cameraPage);

        //Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterReceiptResultView(this, receiptResultItems);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //Format the recycler view for readibility and aesthetics
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(60, 50);
        recyclerView.addItemDecoration(itemDecorator);

        /*
        TODO: hardcode in items from receipt
        TODO: figure out way to add those items into the pantry/fridge
        TODO: create method to edit the items on receiptresult + pantry screens
         */

    }

    public ArrayList<IngredientItem> createReceiptItemsList(){
        ArrayList<IngredientItem> receiptItemsList = new ArrayList<>();

        //for dates, get today's date and add a certain amount of days/weeks
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());


        try{
            cal.setTime(df.parse(date.toString()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //1 week expiry stuff
        cal.add(Calendar.DAY_OF_MONTH, 7);
        String lettuceDate = df.format(cal.getTime());

        //2 week expiry stuff
        cal.add(Calendar.DAY_OF_MONTH, 7);
        String chickenDate = df.format(cal.getTime());

        //multi-month expiry stuff
        cal.add(Calendar.DAY_OF_MONTH, 30);
        String pankoDate = df.format(cal.getTime());

        receiptItemsList.add(new IngredientItem("Iceberg Lettuce", lettuceDate, R.drawable.lettuce, 1));
        receiptItemsList.add(new IngredientItem("WW RSPCA Chicken Mince 500g", chickenDate, R.drawable.chicken_leg, 1));
        receiptItemsList.add(new IngredientItem("Mr Chens Pantry Panko Brd Crumb 250g", pankoDate, R.drawable.bread, 1));
        receiptItemsList.add(new IngredientItem("Brioche Gourmet Burger Buns 4pk 250g", lettuceDate, R.drawable.bread, 1));

        return receiptItemsList;
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
        Intent intent = new Intent(ReceiptResultActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchPantryActivity(String msg) {
        Intent intent = new Intent(ReceiptResultActivity.this, PantryActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeActivity(String msg) {
        Intent intent = new Intent(ReceiptResultActivity.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecycleActivity(String msg) {
        Intent intent = new Intent(ReceiptResultActivity.this, MapActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(ReceiptResultActivity.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }


    @Override
    public void onItemClick(View view, int position) {

    }

}
