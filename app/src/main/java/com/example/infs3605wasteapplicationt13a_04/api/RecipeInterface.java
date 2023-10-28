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

    //api call to return a list of recipes by searched ingredients
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

    //api call to return specific details of recipe
    @Headers({"X-RapidAPI-Key: " + api_key,
            "X-RapidAPI-Host: " + api_host})
    @GET("/recipes/{id}/information?") // for whatever reason can only have one get method, even if references from recipe activity it wont recognize unless the get req is the first method in this file
    Call<ResponseBody> searchRecipeById(@Path("id")
            Integer id
    );

}
