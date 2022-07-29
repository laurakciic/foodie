package com.example.foodie.util

import foodie.BuildConfig

class Constants {

    object Constants {

        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY  = BuildConfig.API_KEY

        // API Query keys
        const val QUERY_NUMBER           = "number"
        const val QUERY_API_KEY          = "apiKey"
        const val QUERY_TYPE             = "type"
        const val QUERY_DIET             = "diet"
        const val QUERY_ADD_RECIPE_INFO  = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

    }
}