package com.example.foodie.data.network

import com.example.foodie.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesAPI {

    // simple function that takes queries inside the parametres
    // - annotated with GET annotation in which we specified an endpoint
    //   which we use to receive data from an API
    // - suspend because our fun will use Kotlin Coroutines
    // - it will also run on a background thread instead of a main thread
    // QueryMap - all queries inside one map
    // FoodRecipe is wrapped inside of Response class which is part of Retrofit library
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(@QueryMap searchQuery: Map<String, String>): Response<FoodRecipe>

}