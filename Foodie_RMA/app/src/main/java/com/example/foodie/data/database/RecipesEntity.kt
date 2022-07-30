package com.example.foodie.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodie.models.FoodRecipe
import com.example.foodie.util.Constants.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe  // RecipesEntity or table will contain only 1 row of foodRecipe
) {
    @PrimaryKey(autoGenerate = false)   // when fetching new data from API, we will replace all existing data
    var id: Int = 0                     // whenever app reads db it will fetch the newest data in db
}