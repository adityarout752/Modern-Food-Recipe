package com.example.food_recipe.foodrecipe.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food_recipe.foodrecipe.model.FoodJoke

@Entity(tableName = "FOOD_JOKE_TABLE")
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}