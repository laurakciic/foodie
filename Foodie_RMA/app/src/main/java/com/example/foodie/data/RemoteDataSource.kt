package com.example.foodie.data

import com.example.foodie.data.network.FoodRecipesAPI
import com.example.foodie.models.FoodFact
import com.example.foodie.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

// will fetch data from FoodRecipesAPI (Spoonacular)
// FoodRecipesAPI injected inside this RemoteDataSource
// RemoteDataSource will request data from an API

// RemoteDataSource and LocalDataSource (local database) will be injected in one repository

class RemoteDataSource @Inject constructor(         // by annotating inject constr and specifying type we want
    private val foodRecipesAPI: FoodRecipesAPI      //    to inject, Hilt will search for that type in NetworkModule (fun that returns that same type) and that's how it will know how to create an instance of FoodRecipesAPI, same for the dep inside
){

    // getRecipes is a suspend fun so the fun needs suspend keyword (located in FoodRecipesAPI interface)
    // with Map we will add multiple queries to GET req
    // FoodRecipe is a model class created using Json to Kotlin plugin
    // FoodRecipe is wrapped inside Response (part of Retrofit lib)
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesAPI.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesAPI.searchRecipes(searchQuery)
    }

    suspend fun getFoodFact(apiKey: String): Response<FoodFact> {
        return foodRecipesAPI.getFoodFact(apiKey)
    }
}

