package com.example.foodie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// data access object
@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)         // when fetching new data, it will replace the old ones
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    // return type is Flow, List of RecipesEntity wrapped inside Flow
    // Flow in RecipesDao, Repository, in ViewModel converting Flow to LiveData
    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>
}