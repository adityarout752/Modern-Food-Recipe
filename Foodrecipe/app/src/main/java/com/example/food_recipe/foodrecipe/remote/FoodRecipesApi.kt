package com.example.food_recipe.foodrecipe.remote

import com.example.food_recipe.foodrecipe.model.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
    @QueryMap queries:Map<String,String>
    ):Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQueries:Map<String,String>
    ) :Response<FoodRecipe>
}