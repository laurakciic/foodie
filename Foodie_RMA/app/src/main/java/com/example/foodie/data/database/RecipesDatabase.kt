package com.example.foodie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodie.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class],
    version = 1,                        // needs to be incremented whenever changing schema
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}