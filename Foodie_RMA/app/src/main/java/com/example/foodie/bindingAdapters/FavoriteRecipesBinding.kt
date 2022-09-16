package com.example.foodie.bindingAdapters

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodie.adapters.FavoriteRecipesAdapter
import com.example.foodie.data.database.entities.FavoritesEntity

class FavoriteRecipesBinding {
    companion object {

        // setting visibility of views & for RecyclerView setting data from FavoriteRecipesAdapter
        // check if recipes table is empty
        // if it is: show hidden views, set recyclerView visibility to invisible
        // setVisibility refers to favoritesEntity, setData refers to mAdapter
        // passing live data from fragment_favourite_recipes.xml
        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setVisibility(
            view: View,                                 // bc binding adapter will be used on multiple different views (recyclerView, imageView, textView)
            favoritesEntity: List<FavoritesEntity>?,    // live data object from MainViewModel, used for reading from favorite recipes table from DB
            mAdapter: FavoriteRecipesAdapter?) {
            when (view) {
                is RecyclerView -> {
                    val dataCheck = favoritesEntity.isNullOrEmpty()     // checking if data is null/empty
                    view.isInvisible = dataCheck                        // display hidden views (when no recipe is saved), hide recycler view
                    if(!dataCheck){
                        favoritesEntity?.let { mAdapter?.setData(it) }  // update recyclerView
                    }
                }
                else -> view.isVisible = favoritesEntity.isNullOrEmpty()
            }
        }

    }
}