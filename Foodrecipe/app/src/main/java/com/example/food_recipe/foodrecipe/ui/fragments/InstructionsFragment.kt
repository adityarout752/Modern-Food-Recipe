package com.example.food_recipe.foodrecipe.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.food_recipe.foodrecipe.model.Result
import com.example.foodrecipe.R
import com.example.foodrecipe.databinding.FragmentIngredientsBinding
import com.example.foodrecipe.databinding.FragmentInstructionsBinding


class InstructionsFragment : Fragment() {
    private var _binding : FragmentInstructionsBinding?=null
    private val binding get() =_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentInstructionsBinding.inflate(inflater, container, false)
        val args=arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")
        binding.instructionsWebView.webViewClient = object :WebViewClient(){}
        binding.instructionsWebView.loadUrl(myBundle?.sourceUrl!!)
        return binding.root
    }

}