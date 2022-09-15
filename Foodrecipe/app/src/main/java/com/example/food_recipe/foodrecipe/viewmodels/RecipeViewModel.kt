package com.example.food_recipe.foodrecipe.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.food_recipe.foodrecipe.data.DataStoreRepository
import com.example.foodrecipe.constants.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel @ViewModelInject constructor(
    application: Application, private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {
    private var mealType = Constants.DEFAULT_MEAL_TYPE
    private var dietType = Constants.DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false
     val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    fun saveBackOnline(backOnline:Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQuaries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        viewModelScope.launch {
            readMealAndDietType.collect{ values->
                mealType =values.selectedMealType
                dietType = values.selectedDietType

            }
        }

        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = mealType
        queries[Constants.QUERY_DIET] = dietType
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        return queries

    }

    fun searchQuery(searchQuery:String) : HashMap<String,String> {

        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_SEARCH] = searchQuery
        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        Log.d("querymap","${queries}")
        return queries
    }

    fun showNetworkStatus() {
        if(!networkStatus) {
            Toast.makeText(getApplication(),"No Internet Connection Available",Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if(networkStatus) {
            if(backOnline) {
                Toast.makeText(getApplication(),"We are Back Online",Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}