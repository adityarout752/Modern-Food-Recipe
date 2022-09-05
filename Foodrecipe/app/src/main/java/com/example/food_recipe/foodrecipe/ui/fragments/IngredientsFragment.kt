package com.example.food_recipe.foodrecipe.ui.fragments

import IngredientsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food_recipe.foodrecipe.model.Result
import com.example.foodrecipe.R
import com.example.foodrecipe.databinding.FragmentIngredientsBinding
import com.example.foodrecipe.databinding.FragmentOverviewBinding

class IngredientsFragment : Fragment() {
    private var _binding : FragmentIngredientsBinding?=null
    private val binding get() =_binding!!
    var myBundle: Result? = null
    private var mAdapter:IngredientsAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentIngredientsBinding.inflate(inflater, container, false)
        val args=arguments
       myBundle = args?.getParcelable("recipeBundle")
        setUPRecyclerView()
        setData()
        return binding.root
    }

    private fun setData() {
        myBundle?.extendedIngredients?.let{
            mAdapter?.setData(requireContext(),it)
        }
    }

    private fun setUPRecyclerView(){
        mAdapter=IngredientsAdapter(requireContext())
        binding.ingredientsRecyclerview.adapter =mAdapter
    }

}