package com.example.food_recipe.foodrecipe.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food_recipe.foodrecipe.model.FoodRecipe

@Entity(tableName = "recipes_table")
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int =0
}