package com.example.food_recipe.foodrecipe.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipe.foodrecipe.db.FavouritesEntity
import com.example.food_recipe.foodrecipe.ui.fragments.favourites.FavoriteRecipesFragmentDirections
import com.example.food_recipe.foodrecipe.viewmodels.MainViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.constants.RecipesDiffUtil
import com.example.foodrecipe.databinding.FavouritesRecipeRowLayoutBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.favourites_recipe_row_layout.view.*
import kotlinx.android.synthetic.main.recipes_row_layout.*
import kotlinx.android.synthetic.main.recipes_row_layout.view.*
import kotlinx.android.synthetic.main.recipes_row_layout.view.favouritesrecipesRowLayout

class FavouritesRecipeAdapter(val requireActivity: FragmentActivity,private val mainViewModel: MainViewModel) :
    RecyclerView.Adapter<FavouritesRecipeAdapter.MyViewHolder>(), ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavouritesEntity>()
    private var multiSelection = false

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    private var selectedRecipes = arrayListOf<FavouritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()


    class MyViewHolder(private val binding: FavouritesRecipeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavouritesEntity) {
            binding.favouritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    FavouritesRecipeRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRecipe = favoriteRecipes[position]

        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        /**
         * Single Click Listener
         * */
        holder.itemView.favouritesrecipesRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        currentRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        /**
         * Long Click Listener
         * */
        holder.itemView.favouritesrecipesRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }

        }



    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    fun setData(newFavoriteRecipes: List<FavouritesEntity>) {
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favourites_contextual_menu, menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favourites_recipe_menu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavouritesRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed.")

            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }

        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color:Int){
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity,color)
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavouritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.favouritesrecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.favorite_row_cardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }
}