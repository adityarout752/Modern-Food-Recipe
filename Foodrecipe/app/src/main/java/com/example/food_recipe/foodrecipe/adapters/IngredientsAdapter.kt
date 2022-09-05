import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.food_recipe.foodrecipe.model.ExtendedIngredient
import com.example.food_recipe.foodrecipe.model.Result
import com.example.foodrecipe.R
import com.example.foodrecipe.adapters.RecipesAdapter
import com.example.foodrecipe.constants.Constants.Companion.BASE_IMAGE_URL
import com.example.foodrecipe.constants.RecipesDiffUtil
import com.example.foodrecipe.databinding.IngredientsRowLayoutBinding
import com.example.foodrecipe.databinding.RecipesRowLayoutBinding
import java.util.*

class IngredientsAdapter(val context:Context): RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

   inner class MyViewHolder(val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding= IngredientsRowLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = holder.binding as IngredientsRowLayoutBinding
        binding.ingredientImageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)

        }
        binding.ingredientName.text = ingredientsList[position].name.capitalize(Locale.ROOT)
        binding.ingredientAmount.text = ingredientsList[position].amount.toString()
        binding.ingredientUnit.text = ingredientsList[position].unit
       binding.ingredientConsistency.text = ingredientsList[position].consistency
        binding.ingredientOriginal.text = ingredientsList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(context: Context,newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

}