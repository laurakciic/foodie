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

        // ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        // Bottom Sheet and Preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"

        const val PREFERENCES_NAME = "foodie_preferences"   // name of the DataStore preference, under it all other values are stored
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

    }
}