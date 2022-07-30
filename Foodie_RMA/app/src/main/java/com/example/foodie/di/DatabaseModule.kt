package com.example.foodie.di

import android.content.Context
import androidx.room.Room
import com.example.foodie.data.database.RecipesDatabase
import com.example.foodie.util.Constants.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// telling Hilt how to provide ROOM DB builder and RecipesDao
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides                                   // because ROOM lib is an external class
    fun provideDatabase(                        // fun provides ROOM databaseBuilder
        @ApplicationContext context: Context    // so we can inject it immediately
    ) = Room.databaseBuilder(                   // returns databaseBuilder
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    // calling abstract fun recipeDao() located in RecipeDatabase class
    fun provideDao(database: RecipesDatabase) = database.recipesDao()   // returns recipeDao()
}