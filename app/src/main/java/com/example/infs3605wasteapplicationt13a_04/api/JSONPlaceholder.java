package com.example.infs3605wasteapplicationt13a_04.api;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceholder {
    @GET("recipes?")//not sure as this is just after the /
    Call<ArrayList<Recipe>> getRecipes();


}
