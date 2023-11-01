package com.example.infs3605wasteapplicationt13a_04.recipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605wasteapplicationt13a_04.AddItemActivity;
import com.example.infs3605wasteapplicationt13a_04.MainActivity;
import com.example.infs3605wasteapplicationt13a_04.MapActivity;
import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.api.ExtendedIngredient;
import com.example.infs3605wasteapplicationt13a_04.api.Recipe;
import com.example.infs3605wasteapplicationt13a_04.api.RecipeInfo;
import com.example.infs3605wasteapplicationt13a_04.api.RecipeInterface;
import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeDetail extends AppCompatActivity {

    public static final String INTENT_MESSAGE = "intent_message";
    private static final String TAG = "recipeDetail";
    public static final String api_key = "fd4dad4847msh681f68c54f6e396p14017djsnd0c43955d9e8";
    public static final String api_url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private BottomNavigationView bottomNavigationView;
    private int selectedRecipeId;
    private TextView instructions;
    private String link;
    private Button openLink;
    private TextView title;
    private ImageView recipeImage;
    private TextView usedIngredients;
    private ArrayList<ExtendedIngredient> ingredientsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        System.out.println("loaded detail recipe");
        Intent intent = getIntent();

        //parse selected recipe from the recyclerview here and find recipe ID
        if (intent.hasExtra(INTENT_MESSAGE)) {
            selectedRecipeId = Integer.parseInt(intent.getStringExtra(INTENT_MESSAGE));//parse selected recipe ID
            System.out.println(selectedRecipeId);
        } else {
            System.out.println("Did not return recipeID");
        }

        //Get handle for view elements
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.recipesPage);
        instructions = findViewById(R.id.tvInstruction);
        openLink = findViewById(R.id.button3);
        title = findViewById(R.id.tvTitle);
        recipeImage = findViewById(R.id.ivRecipe);
        usedIngredients = findViewById(R.id.tvIngredients);


        //api calls
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeInterface recipeAPI = retrofit.create(RecipeInterface.class);
        Call<ResponseBody> call = recipeAPI.searchRecipeById(selectedRecipeId);
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
                String recipeJson = res;
                Gson gson = new Gson();

                //set view elements with recipe information
                RecipeInfo recipeInfo = gson.fromJson(recipeJson, RecipeInfo.class);
                instructions.setText(recipeInfo.getInstructions());
                link = recipeInfo.getSourceUrl();

                title.setText(recipeInfo.getTitle());
                usedIngredients.setText("");
                Picasso.get().load(recipeInfo.getImage()).into(recipeImage);

                openLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                    }
                });

                //update arraylist of items based on the title of the api calls
                ingredientsList.addAll(recipeInfo.getExtendedIngredients());
                for (int i=0; i<ingredientsList.size(); i++) {
                    usedIngredients.append(ingredientsList.get(i).getName()+"\n");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API Call Failed");
            }

        });

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
