package com.example.food_recipe.foodrecipe.ui.fragments.foodjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodrecipe.R

class foodjokeFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foodjoke, container, false)
        check()
    }

    private fun check() {
        TODO("Not yet implemented")
    }
}