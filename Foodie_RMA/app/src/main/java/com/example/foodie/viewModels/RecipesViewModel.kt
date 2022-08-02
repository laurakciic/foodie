package com.example.foodie.viewModels

import android.app.Application
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodie.data.DataStoreRepository
import com.example.foodie.data.MealAndDietType
import com.example.foodie.util.Constants
import com.example.foodie.util.Constants.Constants.DEFAULT_DIET_TYPE
import com.example.foodie.util.Constants.Constants.DEFAULT_MEAL_TYPE
import com.example.foodie.util.Constants.Constants.DEFAULT_RECIPES_NUMBER
import com.example.foodie.util.Constants.Constants.QUERY_ADD_RECIPE_INFO
import com.example.foodie.util.Constants.Constants.QUERY_API_KEY
import com.example.foodie.util.Constants.Constants.QUERY_DIET
import com.example.foodie.util.Constants.Constants.QUERY_FILL_INGREDIENTS
import com.example.foodie.util.Constants.Constants.QUERY_NUMBER
import com.example.foodie.util.Constants.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import foodie.BuildConfig.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

// injected DataStoreRepository inside RecipesViewModel
@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
    ): AndroidViewModel(application) {

    private lateinit var mealAndDiet: MealAndDietType

    private var mealType = DEFAULT_MEAL_TYPE    // when applyQuery runs, their value will change
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false

    // var of type Flow of MealAndDietType (hover over var)
    val readMealAndDietType = dataStoreRepository.readMealAndDietType


    // calling saveMealAndDietType fun from dataStoreRepository
    // inserting exact values from fun parameters
    fun saveMealAndDietType() = viewModelScope.launch(Dispatchers.IO) {
        if (this@RecipesViewModel::mealAndDiet.isInitialized) {
            dataStoreRepository.saveMealAndDietType(
                mealAndDiet.selectedMealType,
                mealAndDiet.selectedMealTypeId,
                mealAndDiet.selectedDietType,
                mealAndDiet.selectedDietTypeId
            )
        }
    }

    fun saveMealAndDietTypeTemp(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        mealAndDiet = MealAndDietType(
            mealType,
            mealTypeId,
            dietType,
            dietTypeId
        )
    }

     fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

         queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
         queries[QUERY_API_KEY] = API_KEY
         queries[QUERY_ADD_RECIPE_INFO] = "true"
         queries[QUERY_FILL_INGREDIENTS] = "true"

         if (this@RecipesViewModel::mealAndDiet.isInitialized) {
             queries[QUERY_TYPE] = mealAndDiet.selectedMealType
             queries[QUERY_DIET] = mealAndDiet.selectedDietType
         } else {
             queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
             queries[QUERY_DIET] = DEFAULT_DIET_TYPE
         }

         return queries
    }

    fun showNetworkStatus() {       // fun check value of networkStatus var defined at the beginning
        if(!networkStatus) {        // if networkStatus false
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

}