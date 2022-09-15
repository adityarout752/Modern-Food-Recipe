package com.example.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipe.foodrecipe.model.FoodRecipe
import com.example.food_recipe.foodrecipe.model.Result
import com.example.foodrecipe.constants.RecipesDiffUtil
import com.example.foodrecipe.databinding.RecipesRowLayoutBinding


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {
    private var recipe = ArrayList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()//update changes in data
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentposition = recipe[position]
        holder.bind(currentposition)//bind the current result everytime
    }

    override fun getItemCount(): Int {

        return recipe.size

    }

    fun setData(newData: FoodRecipe) {
        val recipesDiffUtil = RecipesDiffUtil(recipe, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipe = newData.results
        diffUtilResult.dispatchUpdatesTo(this)

    }
}