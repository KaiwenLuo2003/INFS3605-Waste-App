package com.example.infs3605wasteapplicationt13a_04.recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.AddItemActivity;
import com.example.infs3605wasteapplicationt13a_04.MainActivity;
import com.example.infs3605wasteapplicationt13a_04.api.Recipe;
import com.example.infs3605wasteapplicationt13a_04.api.RecipeInterface;
import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.recycle.RecycleActivity;
import com.example.infs3605wasteapplicationt13a_04.ui.SpacingItemDecorator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity implements RecyclerViewAdapterRecipeView.ItemClickListener {
    public static final String INTENT_MESSAGE = "intent_message";
    private BottomNavigationView bottomNavigationView;

    private RecyclerViewAdapterRecipeView adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //from kaiwens branch
    private static final String TAG = "recipeActivity";
    public static final String api_key = "fd4dad4847msh681f68c54f6e396p14017djsnd0c43955d9e8";
    public static final String api_url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    public ArrayList<String> recipeNames = new ArrayList<>();
    public ArrayList<Recipe> recipeList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        //recipe api stuff
        OkHttpClient client = new OkHttpClient();//think its for the thread method not the retrofit

        //retrofit version
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeInterface recipeAPI = retrofit.create(RecipeInterface.class);
        Call<ResponseBody> call = recipeAPI.searchRecipeByIngredients("apples", 10, false, false, 1); //issue with this method, goes into fail route when calling api
        //Call<ResponseBody> call = recipeAPI.searchRecipe("burger", false, "vegetarian", "", "", 1, 0, false, "");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //apiDetails.setText("working");
                Log.d(TAG, "API CAll Success");
                Log.d(TAG, response.toString());
                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //apiDetails.setText(res);
                System.out.println(res);
                String recipeJson = res;//up to this point recipeJson has all the json info, just need to split and assign to object
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<Recipe>>() {
                }.getType();
                Collection<Recipe> recipes = gson.fromJson(recipeJson, collectionType);
                recipeList.addAll(recipes);
                //create arraylist of items based on the title of the api calls
                //apiDetails.setText(first.get().getTitle());
                for (int i = 0; i < recipeList.size(); i++) {
                    recipeNames.add(recipeList.get(i).getTitle());
                    System.out.println(recipeNames.get(i));//currently its got the correct recipes, its just not showing up in the recyclerview
                    adapter.notifyItemInserted(i);//temporary fix, item has been added to arrayList, but not sure if it would return the entire recipe when clicked on
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API Call Failed");
            }

        });
        //end of api stuff
        //System.out.println(recipeList.get(0));//currently is not filled with data

//        recipeNames.add("Cheesecake");
//        recipeNames.add("Pasta bake");
//        recipeNames.add("Lemonade");
        //Temporary data to populate recyclerview

        //Get handle for view elements
        recyclerView = findViewById(R.id.rvRecipeList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.recipesPage);

        //Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterRecipeView(this, recipeNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //Format the recycler view for readibility and aesthetics
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(60, 50);
        recyclerView.addItemDecoration(itemDecorator);


        //firebase documentation: https://firebase.google.com/docs/firestore/quickstart#java
        FirebaseFirestore db = FirebaseFirestore.getInstance();


    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(RecipeActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchPantryActivity(String msg) {
        Intent intent = new Intent(RecipeActivity.this, PantryActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeActivity(String msg) {
        Intent intent = new Intent(RecipeActivity.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecycleActivity(String msg) {
        Intent intent = new Intent(RecipeActivity.this, RecycleActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }
}