package com.example.foodie.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.viewModels.MainViewModel
import com.example.foodie.adapters.RecipesAdapter
import com.example.foodie.util.Constants.Constants.API_KEY
import com.example.foodie.util.NetworkResult
import com.example.foodie.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import foodie.databinding.FragmentRecipesBinding

// initialized view models and recycler view adapter
@AndroidEntryPoint      // here and in MainActivity, important because of Hilt DI
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel // initialize MainViewModel (class)
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }       // lazy initialization,for RecyclerView adapter setup
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //mView = inflater.inflate(R.layout.fragment_recipes, container, false)

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        // whenever app starts, RecyclerView will setup and showShimmerEffect will appear
        // shimmer effect is active until we get data from API
        setupRecyclerView()
        requestApiData()

        return binding.root
    }

    // fun that will start and fetch data from API
    // calling getRecipes() from MainViewModel which will get data from API
    //   and will store response inside recipesResponse (MutableLiveData obj)
    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {       // when response is successful get data and pass it to RecyclerView adapter
                    hideShimmerEffect()             //  and hide shimmer eff
                    response.data?.let { mAdapter.setData(it) }      // calling recipesAdapter, his method setData and passing FoodRecipe we received from API
                }                                                    // FoodRecipe (it) is nullable so ? near data is added
                is NetworkResult.Error -> {         // in case of error, display toast message
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter   // setting adapter to RecipesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }

}