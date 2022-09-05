package com.example.food_recipe.foodrecipe.model


import com.google.gson.annotations.SerializedName

data class FoodRecipe(

    @SerializedName("results")
    val results: ArrayList<Result>

)