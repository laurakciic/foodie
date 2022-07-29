package com.example.foodie.models


import com.example.foodie.models.ExtendedIngredient
import com.google.gson.annotations.SerializedName

// Result class is binded with recipes_row_layout.xml (data binding)
// = binded API data with recipes_row_layout.xml

// info we will recieve from API:
data class Result(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,                    // converted to String so it can be displayed in TextView
    @SerializedName("cheap")
    val cheap: Boolean,
    @SerializedName("dairyFree")
    val dairyFree: Boolean,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,                    // converted to String so it can be displayed in TextView
    @SerializedName("sourceName")
    val sourceName: String,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean
)