package com.example.infs3605wasteapplicationt13a_04.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RecipeInterface {

    @Headers("X-RapidAPI-Key': 'fd4dad4847msh681f68c54f6e396p14017djsnd0c43955d9e8, " +
            "X-RapidAPI-Host': 'spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")




    @GET("/findByIngredients?")
    Call<ResponseBody> getRecipeByIngredients(
            @Query("ingredients") String ingredientsList,
            @Query("number") int number,
            @Query("limitLicense") boolean limitLicense,
            @Query("ignorePantry") boolean ignorePantry,
            @Query("ranking") int ranking
    );

}
