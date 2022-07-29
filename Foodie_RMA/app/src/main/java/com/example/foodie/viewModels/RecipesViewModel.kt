package com.example.foodie.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foodie.util.Constants
import com.example.foodie.util.Constants.Constants.QUERY_ADD_RECIPE_INFO
import com.example.foodie.util.Constants.Constants.QUERY_API_KEY
import com.example.foodie.util.Constants.Constants.QUERY_DIET
import com.example.foodie.util.Constants.Constants.QUERY_FILL_INGREDIENTS
import com.example.foodie.util.Constants.Constants.QUERY_NUMBER
import com.example.foodie.util.Constants.Constants.QUERY_TYPE
import foodie.BuildConfig.API_KEY

class RecipesViewModel(application: Application): AndroidViewModel(application) {

     fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        //        key                     value
        queries[QUERY_NUMBER]            = "50"        // num of recipes we want to get from request, possible from 1 to 100
        queries[QUERY_API_KEY]           = API_KEY
        queries[QUERY_TYPE]              = "snack"     // hardcoded for now
        queries[QUERY_DIET]              = "vegan"     // hardcoded for now
        queries[QUERY_ADD_RECIPE_INFO]   = "true"
        queries[QUERY_FILL_INGREDIENTS]  = "true"

        return queries
    }

}