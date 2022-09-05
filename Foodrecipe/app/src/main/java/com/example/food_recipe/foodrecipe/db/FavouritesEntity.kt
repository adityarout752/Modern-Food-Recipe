package com.example.food_recipe.foodrecipe.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food_recipe.foodrecipe.model.Result

@Entity(tableName = "favourites_recipes_table")
class FavouritesEntity(
  @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result: Result
)