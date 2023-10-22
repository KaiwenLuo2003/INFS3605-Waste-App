package com.example.infs3605wasteapplicationt13a_04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.example.infs3605wasteapplicationt13a_04.api.Recipe;
import com.example.infs3605wasteapplicationt13a_04.api.RecipeInterface;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity {

    TextView apiDetails;
    RecyclerView recyclerView;
    private static final String TAG = "recipeActivity";
    public static final String api_key = "fd4dad4847msh681f68c54f6e396p14017djsnd0c43955d9e8";
    public static final String api_url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        apiDetails = findViewById(R.id.apiDetails);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiDetails.setText("test");
        OkHttpClient client = new OkHttpClient();//think its for the thread method not the retrofit

        //retrofit version
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeInterface recipeAPI = retrofit.create(RecipeInterface.class);
        Call<ResponseBody> call = recipeAPI.searchRecipeByIngredients("apples", 1, false, false, 1); //issue with this method, goes into fail route when calling api
        //Call<ResponseBody> call = recipeAPI.searchRecipe("burger", false, "vegetarian", "", "", 1, 0, false, "");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                System.out.println(response.isSuccessful());
                if (response.isSuccessful()) {
                    apiDetails.setText("working");
                    Log.d(TAG, "API CAll Success");
                    Log.d(TAG, response.toString());
                    String res = null;
                    try {
                        res = response.body().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    apiDetails.setText(res);
                    System.out.println(res);
                    String recipeJson = res;//up to this point recipeJson has all the json info, just need to split and assign to object
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<Recipe>>(){}.getType();
                    Collection<Recipe> recipes = gson.fromJson(recipeJson, collectionType);
                    //List<Recipe>
                    //Recipe recipe = gson.fromJson(recipeJson, Recipe.class);
                    Optional<Recipe> first = recipes.stream().findFirst();
                    apiDetails.setText(first.get().getTitle());
                } else {
                    apiDetails.setText("fail");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "API Call Failed");
            }
        });

    }

//    public void maptoJson() {
//        String json = ;
//        Gson gson = new Gson();
//        Map<String, String> map = gson.fromJson(json, Map.class);
//    }



//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try  {
//                    Request request = new Request.Builder()
//                            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/complexSearch?query=pasta&cuisine=italian&excludeCuisine=greek&diet=vegetarian&intolerances=gluten&equipment=pan&includeIngredients=tomato%2Ccheese&excludeIngredients=eggs&type=main%20course&instructionsRequired=true&fillIngredients=false&addRecipeInformation=false&titleMatch=Crock%20Pot&maxReadyTime=20&ignorePantry=true&sort=calories&sortDirection=asc&minCarbs=10&maxCarbs=100&minProtein=10&maxProtein=100&minCalories=50&maxCalories=800&minFat=10&maxFat=100&minAlcohol=0&maxAlcohol=100&minCaffeine=0&maxCaffeine=100&minCopper=0&maxCopper=100&minCalcium=0&maxCalcium=100&minCholine=0&maxCholine=100&minCholesterol=0&maxCholesterol=100&minFluoride=0&maxFluoride=100&minSaturatedFat=0&maxSaturatedFat=100&minVitaminA=0&maxVitaminA=100&minVitaminC=0&maxVitaminC=100&minVitaminD=0&maxVitaminD=100&minVitaminE=0&maxVitaminE=100&minVitaminK=0&maxVitaminK=100&minVitaminB1=0&maxVitaminB1=100&minVitaminB2=0&maxVitaminB2=100&minVitaminB5=0&maxVitaminB5=100&minVitaminB3=0&maxVitaminB3=100&minVitaminB6=0&maxVitaminB6=100&minVitaminB12=0&maxVitaminB12=100&minFiber=0&maxFiber=100&minFolate=0&maxFolate=100&minFolicAcid=0&maxFolicAcid=100&minIodine=0&maxIodine=100&minIron=0&maxIron=100&minMagnesium=0&maxMagnesium=100&minManganese=0&maxManganese=100&minPhosphorus=0&maxPhosphorus=100&minPotassium=0&maxPotassium=100&minSelenium=0&maxSelenium=100&minSodium=0&maxSodium=100&minSugar=0&maxSugar=100&minZinc=0&maxZinc=100&offset=0&number=10&limitLicense=false&ranking=2")
//                            .get()
//                            .addHeader("X-RapidAPI-Key", "fd4dad4847msh681f68c54f6e396p14017djsnd0c43955d9e8")
//                            .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
//                            .build();
//
//                    try {
//                        Response response = client.newCall(request).execute();//crashing here
//                        String test = response.toString();
//                        Log.d(TAG, "REEEEEEEE");
//                        Log.d(TAG, test);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    //apiDetails.setText("test");
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();








}