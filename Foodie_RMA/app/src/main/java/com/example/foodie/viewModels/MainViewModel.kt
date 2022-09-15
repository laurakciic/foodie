package com.example.foodie.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Parcelable
import androidx.lifecycle.*
import com.example.foodie.data.Repository
import com.example.foodie.data.database.entities.FavoritesEntity
import com.example.foodie.data.database.entities.FoodFactEntity
import com.example.foodie.data.database.entities.RecipesEntity
import com.example.foodie.models.FoodFact
import com.example.foodie.models.FoodRecipe
import com.example.foodie.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

// should extend ViewModel but since we are going to need an application reference in this view model
//  then we're going to extend AndroidViewModel
// Repository is injected here

// created MutableLiveData obj which will hold responses from API
// getRecipes calls getRecipesSafeCall fun and from there we're requesting data from API with getRecipes fun from RemoteDataSource
//  also handling all Error responses with handleFoodRecipesResponse fun

// created fun that checks internet conn

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    var recyclerViewState: Parcelable? = null

    /** ROOM DATABASE */

    // called readDatabase() fun from Dao interface
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()   // Flow -> LiveData
    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes().asLiveData()
    val readFoodFact: LiveData<List<FoodFactEntity>> = repository.local.readFoodFact().asLiveData()

    // inserting data in DB
    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)           // calling insertRecipes fun from localDataSource and storing data in DB
        }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipes(favoritesEntity)
        }

    private fun insertFoodFact(foodFactEntity: FoodFactEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodFact(foodFactEntity)
        }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoritesEntity)
        }

    fun deleteAllFavoriteRecipes() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteRecipes()
        }

    /** RETROFIT */

    // type is MutableLiveData, uses model class FoodRecipe that is wrapped inside NetworkResult

    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchedRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var foodFactResponse: MutableLiveData<NetworkResult<FoodFact>> = MutableLiveData()

    // viewModelScope runs func inside Kotlin coroutines

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    fun getFoodFact(apiKey: String) = viewModelScope.launch {
        getFoodFactSafeCall(apiKey)
    }

    // suspend because getRecipes is a suspend fun

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()     // every time we call the fun, it will respond with loading state, when we get actual data from API, we're going to response with either success or error
        if (hasInternetConnection()) {      // then get req whose response will be stored in recipeResponse MutableLiveData obj
            try {                           // repository (injected in MainViewModel) calls remote (to get the access of RemoteDataSource) which then calls getRecipes (created in RemoteDataSource)
                val response = repository.remote.getRecipes(queries)    // passing map of queries (from param)
                recipesResponse.value = handleFoodRecipesResponse(response)

                val foodRecipe = recipesResponse.value!!.data
                if(foodRecipe != null) {              // checking if response is not null
                    offlineCacheRecipes(foodRecipe)   // caching
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()     // every time we call the fun, it will respond with loading state, when we get actual data from API, we're going to response with either success or error
        if (hasInternetConnection()) {      // then get req whose response will be stored in recipeResponse MutableLiveData obj
            try {                           // repository (injected in MainViewModel) calls remote (to get the access of RemoteDataSource) which then calls getRecipes (created in RemoteDataSource)
                val response = repository.remote.searchRecipes(searchQuery)    // passing map of queries (from param)
                searchedRecipesResponse.value = handleFoodRecipesResponse(response)     // reusing handleFoodRecipesReponse fun from above, because response will be food recipe model class aswell
            } catch (e: Exception) {
                searchedRecipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            searchedRecipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun getFoodFactSafeCall(apiKey: String) {
        foodFactResponse.value = NetworkResult.Loading()     // every time we call the fun, it will respond with loading state, when we get actual data from API, we're going to response with either success or error
        if (hasInternetConnection()) {      // then get req whose response will be stored in recipeResponse MutableLiveData obj
            try {                           // repository (injected in MainViewModel) calls remote (to get the access of RemoteDataSource) which then calls getRecipes (created in RemoteDataSource)
                val response = repository.remote.getFoodFact(apiKey)    // passing map of queries (from param)
                foodFactResponse.value = handleFoodFactResponse(response)     // reusing handleFoodRecipesResponse fun from above, because response will be food recipe model class aswell

                // whenever we get data from API & that data is not null, then call offlineCacheFoodFact and cache it
                val foodFact = foodFactResponse.value!!.data    // get foodFact actual data, value
                if(foodFact != null){
                    offlineCacheFoodFact(foodFact)
                }
            } catch (e: Exception) {
                foodFactResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            foodFactResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)       // to insert data to DB foodRecipe needs to be converted into recipesEntity
        insertRecipes(recipesEntity)
    }

    private fun offlineCacheFoodFact(foodFact: FoodFact) {
        val foodFactEntity = FoodFactEntity(foodFact)       // to insert data to DB foodRecipe needs to be converted into recipesEntity
        insertFoodFact(foodFactEntity)
    }

    // handleFoodRecipesResponse takes response from API (which we passed from above try block), handles and parses it

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when {
            // sometimes it takes long for API to response to our req
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }

            // sometimes result is successful from API but it's empty (no recipes for specified queries)
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

    private fun handleFoodFactResponse(response: Response<FoodFact>): NetworkResult<FoodFact> {
        when {
            // sometimes it takes long for API to response to our req
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }

            response.isSuccessful -> {
                val foodFact = response.body()
                return NetworkResult.Success(foodFact!!)
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

