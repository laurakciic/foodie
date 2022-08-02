package com.example.foodie.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater          //
import androidx.appcompat.widget.SearchView
//import android.view.View
//import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.viewModels.MainViewModel
import com.example.foodie.adapters.RecipesAdapter
import com.example.foodie.util.Constants.Constants.API_KEY
import com.example.foodie.util.NetworkListener
import com.example.foodie.util.NetworkResult
import com.example.foodie.util.observeOnce
import com.example.foodie.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import foodie.R
import foodie.databinding.FragmentRecipesBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

// initialized view models and recycler view adapter
@ExperimentalCoroutinesApi
@AndroidEntryPoint      // here and in MainActivity, important because of Hilt DI
class RecipesFragment : Fragment() {

    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel // initialize MainViewModel (class)
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }       // lazy initialization,for RecyclerView adapter setup

    private lateinit var networkListener: NetworkListener

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

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this   // because of LiveData obj and var
        binding.mainViewModel = mainViewModel

        // whenever app starts, RecyclerView will setup and showShimmerEffect will appear
        // shimmer effect is active until we get data from API
        setupRecyclerView()
        readDatabase()

        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->                                        // because collect is a suspend fun, we need to call this inside Kotlin Coroutine (lifecycleScope)
                    Log.d("NetworkListener", status.toString())
                }
        }

        binding.mealDietSelectionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter   // setting adapter to RecipesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        // launches coroutine
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {     // if DB isn't empty && args from backFromBottomSheet is false, read data and display it in RecyclerView
                    Log.d("RecipesFragment", "readDatabase called")  // every time we get from bottom sheet we're gonna get new data
                    mAdapter.setData(database.first().foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }

    // when DB is empty
    // fun that will start and fetch data from API
    // calling getRecipes() from MainViewModel which will get data from API
    //   and will store response inside recipesResponse (MutableLiveData obj)
    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {       // when response is successful get data and pass it to RecyclerView adapter
                    hideShimmerEffect()             //  and hide shimmer eff
                    response.data?.let { mAdapter.setData(it) }      // populating DB with new data, calling recipesAdapter, his method setData and passing FoodRecipe we received from API
                    recipesViewModel.saveMealAndDietType()
                }                                                    // FoodRecipe (it) is nullable so ? near data is added
                is NetworkResult.Error -> {         // in case of error, display toast message
                    hideShimmerEffect()
                    loadDataFromCache()             // show user previous data
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
        }
    }

    // in case user encounters an error show him previous state (data)
    // called if NetworkResult encounters error
    private fun loadDataFromCache() {
        // lifecycleScope.launch because readData is not a suspend fun
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if(database.isNotEmpty()) {
                    mAdapter.setData(database.first().foodRecipe)
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.hideShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {      // whenever RecipesFragment is destroyed
        super.onDestroyView()
        mainViewModel.recyclerViewState =
            binding.recyclerView.layoutManager?.onSaveInstanceState()
        _binding = null             // to avoid memory leaks
    }
}