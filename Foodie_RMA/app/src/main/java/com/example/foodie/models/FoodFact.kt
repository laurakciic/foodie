package com.example.foodie.models


import com.google.gson.annotations.SerializedName

data class FoodFact(

    // no need to create type converters bc we can store this value directly in our db

    @SerializedName("text")
    val text: String
)