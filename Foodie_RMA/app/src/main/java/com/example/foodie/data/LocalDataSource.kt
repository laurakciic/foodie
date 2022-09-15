package com.example.foodie.data

import com.example.foodie.data.database.RecipesDao
import com.example.foodie.data.database.entities.FavoritesEntity
import com.example.foodie.data.database.entities.FoodFactEntity
import com.example.foodie.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// contains all the queries from recipesDao because we injected it
class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return recipesDao.readFavoriteRecipes()
    }

    fun readFoodFact(): Flow<List<FoodFactEntity>> {
        return recipesDao.readFoodFact()
    }

    // inserting recipes into DB
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)                     // calling insertRecipes fun from recipesDao
    }

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun insertFoodFact(foodFactEntity: FoodFactEntity) {
        recipesDao.insertFoodFact(foodFactEntity)
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

}