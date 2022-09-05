package com.example.food_recipe.foodrecipe.di

import android.content.Context
import androidx.room.Room
import com.example.food_recipe.foodrecipe.db.RecipesDao
import com.example.food_recipe.foodrecipe.db.RecipesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context,
            RecipesDatabase::class.java,
            "recipes_table"
        ).build()


    @Singleton
    @Provides
    fun providesDao(database: RecipesDatabase): RecipesDao {
       return database.recipesDao()
    }
}