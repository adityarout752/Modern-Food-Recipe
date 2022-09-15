package com.example.food_recipe.foodrecipe.remote

import com.example.food_recipe.foodrecipe.model.FoodJoke
import com.example.food_recipe.foodrecipe.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject



class RemoteDataSource @Inject constructor(
        private val foodRecipesApi: FoodRecipesApi
        //hilt will search for this specific type in Network Module,and search for function
        //which return same type
) {
    suspend fun getRecipes(quaries:Map<String,String>):Response<FoodRecipe>{
        return  foodRecipesApi.getRecipes(quaries)
    }

    suspend fun searchRecipes(searchQuery : Map<String,String>) :Response<FoodRecipe>{
        return  foodRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey:String) :Response<FoodJoke>{
        return foodRecipesApi.getFoodJoke(apiKey)
    }
}