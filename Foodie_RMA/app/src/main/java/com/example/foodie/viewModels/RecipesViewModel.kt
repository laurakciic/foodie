package com.example.foodie.viewModels

import android.app.Application
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
import foodie.BuildConfig.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

// injected DataStoreRepository inside RecipesViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
    ): AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    // var of type Flow of MealAndDietType (hover over var)
    val readMealAndDietType = dataStoreRepository.readMealAndDietType


    // calling saveMealAndDietType fun from dataStoreRepository
    // inserting exact values from fun parameters
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }


     fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

         viewModelScope.launch {
             readMealAndDietType.collect { value ->
                 mealType = value.selectedMealType
                 dietType = value.selectedDietType
             }
         }

        //        key                     value
        queries[QUERY_NUMBER]            = DEFAULT_RECIPES_NUMBER  // num of recipes we want to get from request, possible from 1 to 100
        queries[QUERY_API_KEY]           = API_KEY
        queries[QUERY_TYPE]              = mealType
        queries[QUERY_DIET]              = dietType
        queries[QUERY_ADD_RECIPE_INFO]   = "true"
        queries[QUERY_FILL_INGREDIENTS]  = "true"

        return queries
    }

}