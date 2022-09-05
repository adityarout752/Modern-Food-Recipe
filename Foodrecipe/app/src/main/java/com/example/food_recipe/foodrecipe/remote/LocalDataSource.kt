package com.example.food_recipe.foodrecipe.remote

import com.example.food_recipe.foodrecipe.db.FavouritesEntity
import com.example.food_recipe.foodrecipe.db.RecipesDao
import com.example.food_recipe.foodrecipe.db.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipes() : Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
       return  recipesDao.insertRecipes(recipesEntity)
    }

    fun readFavouritesRecipes() : Flow<List<FavouritesEntity>> {
        return recipesDao.readFavouriteRecipes()
    }
    suspend fun insertFavouritesRecipes(favouritesEntity: FavouritesEntity) {
        return  recipesDao.insertFavouritesRecipes(favouritesEntity)
    }

    suspend fun deleteFavouritesRecipes(favouritesEntity: FavouritesEntity) {
        return  recipesDao.deleteFavouritesRecipes(favouritesEntity)
    }

    suspend fun deleteAllFavouritesRecipes() {
        return  recipesDao.deleteAllFavouritesRecipes()
    }


}