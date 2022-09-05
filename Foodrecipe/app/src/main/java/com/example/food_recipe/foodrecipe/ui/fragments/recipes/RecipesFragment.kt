package com.example.food_recipe.foodrecipe.ui.fragments.recipes
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_recipe.foodrecipe.constants.NetworkListener
import com.example.food_recipe.foodrecipe.constants.NetworkResult
import com.example.food_recipe.foodrecipe.viewmodels.MainViewModel
import com.example.food_recipe.foodrecipe.viewmodels.RecipeViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.adapters.RecipesAdapter
import com.example.foodrecipe.databinding.FragmentRecipesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import observeOnce

@Suppress("DEPRECATION")
@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {


    private var _binding:FragmentRecipesBinding?=null
    private val binding get() =_binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipeViewModel
    private val mAdapter by lazy { RecipesAdapter() }
    lateinit var networkListener: NetworkListener
    var menuHost: MenuHost? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel= ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainviewmodel = mainViewModel
        binding.floatingActionButton.setOnClickListener {
            if(recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            } else {
                recipesViewModel.showNetworkStatus()
            }
        }
        setHasOptionsMenu(true)
      setUpRecyclerView()
        recipesViewModel.readBackOnline.observe(viewLifecycleOwner){
            recipesViewModel.backOnline = it
        }
//        requestApiData()
        readDatabase()

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{ status ->
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }
        binding.floatingActionButton.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            } else {
                recipesViewModel.showNetworkStatus()
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    private fun readDatabase() {

        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) {
                if(it.isNotEmpty()) {
                    Log.d("TAG","DATABASE")
                    mAdapter.setData(it[0].foodRecipe)
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData(){
        Log.d("TAG","RETROFIT")
    mainViewModel.getRecipes(recipesViewModel.applyQuaries())
    mainViewModel.recipesResponse.observe(viewLifecycleOwner) {response->
        when(response){
            is NetworkResult.Success ->{
//                hideShimmerEffect()
                response.data?.let {

                    Log.d("adi","${it}")
                    mAdapter.setData(it)
                }
            }
            is NetworkResult.Error -> {
//                hideShimmerEffect()
                Log.d("adi", response.message.toString())
                Toast.makeText(
                    requireContext(),
                    response.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is NetworkResult.Loading ->{
//                showShimmerEffect()
            }
        }
    }
}

    private fun searchApiData(searchQuery : String){

     mainViewModel.searchRecipes(recipesViewModel.searchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner){ response ->
          Log.d("queryres","$response")
            when(response) {

                is NetworkResult.Success -> {
                    val foodRecipe = response.data
                    foodRecipe?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(requireContext(),response.message.toString(),Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }

        }
    }

    private fun setUpRecyclerView(){
        binding.Recyclerview.adapter=mAdapter
        binding.Recyclerview.layoutManager=LinearLayoutManager(requireContext())
//        showShimmerEffect()
    }
    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if(query != null) {
            searchApiData(query)
        }
      return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
      return true
    }
}