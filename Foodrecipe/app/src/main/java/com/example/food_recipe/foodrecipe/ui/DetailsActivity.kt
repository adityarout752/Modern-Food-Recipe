package com.example.food_recipe.foodrecipe.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.food_recipe.foodrecipe.adapters.PagerAdapter
import com.example.food_recipe.foodrecipe.db.FavouritesEntity
import com.example.food_recipe.foodrecipe.ui.fragments.IngredientsFragment
import com.example.food_recipe.foodrecipe.ui.fragments.InstructionsFragment
import com.example.food_recipe.foodrecipe.ui.fragments.OverviewFragment
import com.example.food_recipe.foodrecipe.viewmodels.MainViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModels: MainViewModel by viewModels()
    private var binding: ActivityDetailsBinding? = null

    private var recipeSaved = false
    private var savedRecipeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("OverView")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments, titles, supportFragmentManager
        )

        binding?.viewPager?.adapter = adapter
        binding?.tabLayout?.setupWithViewPager(binding?.viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu.findItem(R.id.addToFavouritesMenu)
        checkSavedRecipe(menuItem)
        return true
    }


    private fun checkSavedRecipe(menuItem: MenuItem) {


        mainViewModels.readFavouritesRecipes.observe(this) { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    } else {
                        changeMenuColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.addToFavouritesMenu && !recipeSaved) {
            saveToFavourites(item)
        } else if (item.itemId == R.id.addToFavouritesMenu && recipeSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun saveToFavourites(item: MenuItem) {
        val favouritesEntity = FavouritesEntity(0, args.result)
        mainViewModels.insertFavouritesRecipe(favouritesEntity)
        changeMenuColor(item, R.color.yellow)
        showSnackbar("Recipes Saved")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
            FavouritesEntity(
                savedRecipeId,
                args.result
            )
        mainViewModels.deleteFavouritesRecipe(favoritesEntity)
        changeMenuColor(item, R.color.white)
        showSnackbar("Removed from Favorites.")
        recipeSaved = false
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            binding?.detailsLayout?.rootView!!, message, Snackbar.LENGTH_SHORT
        ).setAction("okay") {}.show()
    }

    private fun changeMenuColor(item: MenuItem, yellow: Int) {
        item.icon.setTint(ContextCompat.getColor(this, yellow))
    }
}

