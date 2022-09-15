package com.example.foodie.models


import com.google.gson.annotations.SerializedName

data class FoodFact(
    @SerializedName("text")
    val text: String
)