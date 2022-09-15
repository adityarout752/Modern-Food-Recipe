package com.example.food_recipe.foodrecipe.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.food_recipe.foodrecipe.viewmodels.RecipeViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.constants.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipeViewModel
    private var mealTypeChip = Constants.DEFAULT_MEAL_TYPE
    private var mealTypeChipID = 0
    private var dietTypeChip = Constants.DEFAULT_DIET_TYPE
    private var dietTypeChipID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)
        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
       mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            Log.d("TAG",mealTypeChip)
            updateChip(value.selectedMealTypeId,mView.mealType_chipGroup)
            updateChip(value.selectedDietTypeId,mView.dietType_chipGroup)
        }
        mView.mealType_chipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId[0])
            val selectedMealType = chip.text.toString()
            mealTypeChip = selectedMealType
            mealTypeChipID = selectedChipId[0]
        }

        mView.dietType_chipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId[0])
            val selectedDietType = chip.text.toString()
            dietTypeChip = selectedDietType
            dietTypeChipID = selectedChipId[0]
        }

        mView.apply_btn.setOnClickListener {
           recipesViewModel.saveMealAndDietType(mealTypeChip,mealTypeChipID,dietTypeChip,dietTypeChipID)
            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment()
            findNavController().navigate(action)
        }
        return mView
    }

    private fun updateChip(chipId: Int, chipgroup: ChipGroup?) {

        if(chipId != 0) {
            chipgroup?.findViewById<Chip>(chipId)?.isChecked = true
        }
    }

}