package com.example.foodie.ui.fragments.facts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodie.util.Constants.Constants.API_KEY
import com.example.foodie.util.NetworkResult
import com.example.foodie.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import foodie.databinding.FragmentFoodFactBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint  // bc MainViewModel is using dependency injection so we need that to create an instance of it
class FoodFactFragment : Fragment() {

    // initializing view model by delegating it to viewModels (other way is using view model provider)
    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding: FragmentFoodFactBinding? = null
    private val binding get() = _binding!!

    private var foodFact = "No Food Fact Available"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodFactBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        // requesting API data
        mainViewModel.getFoodFact(API_KEY)

        // foodFactResponse is a mutable live data object that contains data from API
        mainViewModel.foodFactResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {                            // if response was successful
                    binding.foodFactTextView.text = response.data?.text  // use binding to get reference of foodFactTextView and set its text to response.data.text (field inside FoodFact model class)
                    if (response.data != null) {
                        foodFact = response.data.text
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()             // LOADING DATA FROM CACHE
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodJokeFragment", "Loading")
                }
            }
        }

        return binding.root
    }


    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readFoodFact.observe(viewLifecycleOwner) { database ->
                if (!database.isNullOrEmpty()) {
                    // basically getting latest API data which we have in database
                    // set text to database on position 0 (first) bc we're going to have only 1 row inside FoodFact table
                    binding.foodFactTextView.text = database.first().foodFact.text      // field from FoodFact model class
                    foodFact = database.first().foodFact.text
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null             // preventing memory leaks
    }
}