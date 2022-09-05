package com.example.food_recipe.foodrecipe.db

import androidx.room.*
import com.example.food_recipe.foodrecipe.db.RecipesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouritesRecipes(favouritesEntity: FavouritesEntity)

    @Query("SELECT * FROM favourites_recipes_table ORDER BY id ASC")
    fun readFavouriteRecipes() :  Flow<List<FavouritesEntity>>

    @Delete
    suspend fun deleteFavouritesRecipes(favouritesEntity: FavouritesEntity)

    @Query("DELETE FROM FAVOURITES_RECIPES_TABLE")
    suspend fun deleteAllFavouritesRecipes()

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
     fun  readRecipes() : Flow<List<RecipesEntity>>
}