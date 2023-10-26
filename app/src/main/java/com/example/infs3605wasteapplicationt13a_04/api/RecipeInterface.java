package com.example.infs3605wasteapplicationt13a_04.api;

import java.util.Optional;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeInterface {

    public static final String api_key = "fd4dad4847msh681f68c54f6e396p14017djsnd0c43955d9e8";
    public static final String api_host = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes";

    @Headers({"X-RapidAPI-Key: " + api_key,
            "X-RapidAPI-Host: " + api_host})
    @GET("/recipes/findByIngredients?") // for whatever reason can only have one get method, even if references from recipe activity it wont recognize unless the get req is the first method in this file
    Call<ResponseBody> searchRecipeByIngredients(
            @Query("ingredients") String ingredientsList,
            @Query("number") Integer number,
            @Query("limitLicense") Boolean limitLicense,
            @Query("ignorePantry") Boolean ignorePantry,
            @Query("ranking") Integer ranking
    );

    @Headers({"X-RapidAPI-Key: " + api_key,
            "X-RapidAPI-Host: " + api_host})
    @GET("/recipes/{id}/information?") // for whatever reason can only have one get method, even if references from recipe activity it wont recognize unless the get req is the first method in this file
    Call<ResponseBody> searchRecipeById(@Path("id")
            Integer id
    );

    @Headers({"X-RapidAPI-Key: " + api_key,
            "X-RapidAPI-Host: " + api_host})
    @GET("/recipes/search?")
    Call<ResponseBody> searchRecipe(
            @Query("query") String query ,//look into using <Optional> currently sint working as
            @Query("instructionsRequired") boolean instructionsRequired,
            @Query("diet") String diet,
            @Query("excludeIngredients") String excludeIngredients,
            @Query("intolerances") String intolerances,
            @Query("number") int resultsNo,
            @Query("offset") int offset,
            @Query("limitLicense") boolean limitLicense,
            @Query("cuisine") String cuisine
    );

    @Headers({"X-RapidAPI-Key: " + api_key,
            "X-RapidAPI-Host: " + api_host})
    @GET("/recipes/complexSearch?")
    Call<ResponseBody> complexSearch(
            @Query("query") String query,
            @Query("cuisine") String cuisine,
            @Query("excludeCuisine") String excludeCuisine,
            @Query("diet") String diet,
            @Query("intolerances") String intolerances,
            @Query("equipment") String equipment,
            @Query("includeIngredients") String includeIngredients,
            @Query("excludeIngredients") String excludeIngredients,
            @Query("type") String type,
            @Query("instructionsRequired") boolean instructionsRequired,
            @Query("fillIngredients") boolean fillIngredients,
            @Query("addRecipeInformation") boolean addRecipeInformation,
            @Query("titleMatch") String titleMatch,
            @Query("maxReadyTime") int maxReadyTime,
            @Query("ignorePantry") boolean ignorePantry,
            @Query("sort") String sort,
            @Query("sortDirection") String sortDirection,
            @Query("minCarbs") int minCarbs,
            @Query("maxCarbs") int maxCarbs,
            @Query("minProtein") int minProtein,
            @Query("maxProtein") int maxProtein,
            @Query("minCalories") int minCalories,
            @Query("maxCalories") int maxCalories,
            @Query("minFat") int minFat,
            @Query("maxFat") int maxFat,
            @Query("minAlcohol") int minAlcohol,
            @Query("maxAlcohol") int maxAlcohol,
            @Query("minCaffeine") int minCaffeine,
            @Query("maxCaffeine") int maxCaffeine,
            @Query("minCopper") int minCopper,
            @Query("maxCopper") int maxCopper,
            @Query("minCalcium") int minCalcium,
            @Query("maxCalcium") int maxCalcium,
            @Query("minCholine") int minCholine,
            @Query("maxCholine") int maxCholine,
            @Query("minCholesterol") int minCholesterol,
            @Query("maxCholesterol") int maxCholesterol,
            @Query("minFluoride") int minFluoride,
            @Query("maxFluoride") int maxFluoride,
            @Query("minSaturatedFat") int minSaturatedFat,
            @Query("maxSaturatedFat") int maxSaturatedFat,
            @Query("minVitaminA") int minVitaminA,
            @Query("maxVitaminA") int maxVitaminA,
            @Query("minVitaminC") int minVitaminC,
            @Query("maxVitaminC") int maxVitaminC,
            @Query("minVitaminD") int minVitaminD,
            @Query("maxVitaminD") int maxVitaminD,
            @Query("minVitaminE") int minVitaminE,
            @Query("maxVitaminE") int maxVitaminE,
            @Query("minVitaminK") int minVitaminK,
            @Query("maxVitaminK") int maxVitaminK,
            @Query("minVitaminB1") int minVitaminB1,
            @Query("maxVitaminB1") int maxVitaminB1,
            @Query("minVitaminB2") int minVitaminB2,
            @Query("maxVitaminB2") int maxVitaminB2,
            @Query("minVitaminB5") int minVitaminB5,
            @Query("maxVitaminB5") int maxVitaminB5,
            @Query("minVitaminB3") int minVitaminB3,
            @Query("maxVitaminB3") int maxVitaminB3,
            @Query("minVitaminB6") int minVitaminB6,
            @Query("maxVitaminB6") int maxVitaminB6,
            @Query("minVitaminB12") int minVitaminB12,
            @Query("maxVitaminB12") int maxVitaminB12,
            @Query("minFiber") int minFiber,
            @Query("maxFiber") int maxFiber,
            @Query("minFolate") int minFolate,
            @Query("maxFolate") int maxFolate,
            @Query("minFolicAcid") int minFolicAcid,
            @Query("maxFolicAcid") int maxFolicAcid,
            @Query("minIodine") int minIodine,
            @Query("maxIodine") int maxIodine,
            @Query("minIron") int minIron,
            @Query("maxIron") int maxIron,
            @Query("minMagnesium") int minMagnesium,
            @Query("maxMagnesium") int maxMagnesium,
            @Query("minManganese") int minManganese,
            @Query("maxManganese") int maxManganese,
            @Query("minPhosphorus") int minPhosphorus,
            @Query("maxPhosphorus") int maxPhosphorus,
            @Query("minPotassium") int minPotassium,
            @Query("maxPotassium") int maxPotassium,
            @Query("minSelenium") int minSelenium,
            @Query("maxSelenium") int maxSelenium,
            @Query("minSodium") int minSodium,
            @Query("maxSodium") int maxSodium,
            @Query("minSugar") int minSugar,
            @Query("maxSugar") int maxSugar,
            @Query("minZinc") int minZinc,
            @Query("maxZinc") int maxZinc,
            @Query("offset") int offset,
            @Query("number") int number,
            @Query("limitLicense") boolean limitLicense,
            @Query("ranking") int ranking
    );




}
