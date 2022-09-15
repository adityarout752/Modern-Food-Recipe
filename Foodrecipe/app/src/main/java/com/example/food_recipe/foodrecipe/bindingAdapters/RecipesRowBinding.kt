package com.example.food_recipe.foodrecipe.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.food_recipe.foodrecipe.model.Result
import com.example.food_recipe.foodrecipe.ui.fragments.recipes.RecipesFragmentDirections
import com.example.foodrecipe.R
import org.jsoup.Jsoup
import java.lang.Exception


class RecipesRowBinding {
    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result) {
            recipeRowLayout.setOnClickListener {
                try {
                    val action =
                        RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onRecipeClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {

            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_baseline_terrain_24)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textview: TextView, likes: Int) {
            textview.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textview: TextView, minutes: Int) {
            textview.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColour")
        @JvmStatic
        fun applyVeganColour(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }


        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textview: TextView, description: String?) {
            if (description != null) {
                val desc =Jsoup.parse(description).text()
                textview.text =desc
            }
        }
    }
}