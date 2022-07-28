package com.example.foodie

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodie.data.Repository
import com.example.foodie.models.FoodRecipe
import com.example.foodie.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

// should extend ViewModel but since we are going to need an application reference in this view model
//  then we're going to extend AndroidViewModel
// Repository is injected here

// created MutableLiveData obj which will hold responses from API
// getRecipes calls getRecipesSafeCall fun and from there we're requesting data from API with getRecipes fun from RemoteDataSource
//  also handling all Error responses with handleFoodRecipesResponse fun

// created fun that checks internet conn

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    // type is MutableLiveData, uses model class FoodRecipe that is wrapped inside NetworkResult

    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    // suspend because getRecipes is a suspend fun

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()     // every time we call the fun, it will respond with loading state, when we get actual data from API, we're going to response with either success or error
        if (hasInternetConnection()) {      // then get req whose response will be stored in recipeResponse MutableLiveData obj
            try {                           // repository (injected in MainViewModel) calls remote (to get the access of RemoteDataSource) which then calls getRecipes (created in RemoteDataSource)
                val response = repository.remote.getRecipes(queries)    // passing map of queries (from param)
                recipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    // handleFoodRecipesResponse takes response from API (which we passed from above try block), handles and parses it

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            // sometimes it takes long for API to response to our req
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }

            // sometimes result is successfull from API but it's empty (no recipes for specified queries)
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }

            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())    // as param we pass error message from API
            }
        }
    }

    // fun returns true if internet conn is available, false if its not

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager            // casted inside ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}

