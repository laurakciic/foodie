package com.example.foodie.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodie.models.Result
import com.example.foodie.util.Constants.Constants.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)