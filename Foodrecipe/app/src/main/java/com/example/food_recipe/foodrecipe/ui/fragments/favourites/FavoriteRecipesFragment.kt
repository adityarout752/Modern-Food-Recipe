package com.example.food_recipe.foodrecipe.ui.fragments.favourites

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipe.foodrecipe.adapters.FavouritesRecipeAdapter
import com.example.food_recipe.foodrecipe.viewmodels.MainViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.databinding.FragmentFavoriteRecipesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private val mainViewModel:MainViewModel by viewModels()
    private val mAdapter: FavouritesRecipeAdapter by lazy { FavouritesRecipeAdapter(requireActivity(),mainViewModel) }


    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter
setHasOptionsMenu(true)
        setupRecyclerView(binding.favoriteRecipesRecyclerView)

        return binding.root
    }
    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAll_favorite_recipes_menu){
            mainViewModel.deleteAllFavouritesRecipe()
            showSnackBar("All recipes removed")
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favourite_recipe_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }
    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }

}


