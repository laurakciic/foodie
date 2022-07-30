package com.example.foodie.data.database

import androidx.room.TypeConverter
import com.example.foodie.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// because we cannot store complex objects in DB directly
class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String {        // converting to Json/string (writing from DB)
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type     // converting back to object (reading from DB)
        return gson.fromJson(data, listType)
    }

}