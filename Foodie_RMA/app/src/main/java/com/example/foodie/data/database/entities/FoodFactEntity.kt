package com.example.foodie.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodie.models.FoodFact
import com.example.foodie.util.Constants.Constants.FOOD_FACT_TABLE

@Entity(tableName = FOOD_FACT_TABLE)
class FoodFactEntity(
    @Embedded                           // inspects foodJoke model class and take its field & store it inside the table
    var foodFact: FoodFact              // model class
) {
    @PrimaryKey(autoGenerate = false)   // false bc we have only 1 row and that row will be updated anytime we request new data
    var id: Int = 0                     // only 1 item inside foodFact table
}