package com.example.infs3605wasteapplicationt13a_04.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity implements RecyclerViewAdapterRecipeView.ItemClickListener {
    public static final String INTENT_MESSAGE = "intent_message";
    private BottomNavigationView bottomNavigationView;
    private ImageView loadingGif;

    private RecyclerViewAdapterRecipeView adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
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
        setTitle("Recipes");

        //Loading animation
        loadingGif = findViewById(R.id.loadingGif);
        loadingGif.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingGif.setVisibility(View.INVISIBLE);
            }
        }, 1500);


        //retrofit api call
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeInterface recipeAPI = retrofit.create(RecipeInterface.class);
        //call made to api using method from RecipeInterface
        Call<ResponseBody> call = recipeAPI.searchRecipeByIngredients("apples", 10, false, false, 1);
        //call is enqueued
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "API CAll Success");
                Log.d(TAG, response.toString());
                String res = null;
                try {
                    res = response.body().string();
                    Log.d(TAG, response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(res);
                String recipeJson = res;//up to this point recipeJson has all the json info, just need to split and assign to object
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<Recipe>>() {
                }.getType();
                //adds list of recipes from api call into a collection to be added to an ArrayList
                Collection<Recipe> recipes = gson.fromJson(recipeJson, collectionType);
                recipeList.addAll(recipes);
                //create arraylist of items based on the title of the api calls
                for (int i = 0; i < recipeList.size(); i++) {
                    recipeNames.add(recipeList.get(i).getTitle());
                    adapter.notifyItemInserted(i);
                    //notifies the recyclerview that a new recipe has been added, needed as the onResponse method is loaded after the recyclerview is initialized
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API Call Failed");
            }
        });
        //end of api call

        //Get handle for view elements
        recyclerView = findViewById(R.id.rvRecipeList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.recipesPage);

        //Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterRecipeView(this, recipeList);
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
        Toast.makeText(this, "You clicked " + adapter.getItem(position).getTitle() + " on row number " + (position+1), Toast.LENGTH_SHORT).show();
        Recipe tempRecipe = recipeList.get(position);
        launchRecipeDetail(INTENT_MESSAGE, tempRecipe);
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


    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeDetail(String msg, Recipe recipe) {
        Intent intent = new Intent(RecipeActivity.this, RecipeDetail.class);
        intent.putExtra(RecipeActivity.INTENT_MESSAGE, recipe.getId().toString());
        startActivity(intent);
    }
}
