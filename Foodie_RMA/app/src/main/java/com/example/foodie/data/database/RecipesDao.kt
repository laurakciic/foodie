package com.example.foodie.data.database

import androidx.room.*
import com.example.foodie.data.database.entities.FavoritesEntity
import com.example.foodie.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

// data access object
@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)         // when fetching new data, it will replace the old ones
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)

    // return type is Flow, List of RecipesEntity wrapped inside Flow
    // Flow in RecipesDao, Repository, in ViewModel converting Flow to LiveData
    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()
}