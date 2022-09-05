package com.example.food_recipe.foodrecipe.repo

import com.example.food_recipe.foodrecipe.remote.LocalDataSource
import com.example.food_recipe.foodrecipe.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject



@ActivityRetainedScoped
//Scope annotation for bindings that should exist for the life of an activity, surviving configuration.
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote=remoteDataSource
    val local =localDataSource
}