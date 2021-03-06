package com.example.foodie.data

import com.example.foodie.data.database.RecipesDao
import com.example.foodie.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// contains all the queries from recipesDao because we injected it
class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    // inserting recipes into DB
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)                     // calling insertRecipes fun from recipesDao
    }

}