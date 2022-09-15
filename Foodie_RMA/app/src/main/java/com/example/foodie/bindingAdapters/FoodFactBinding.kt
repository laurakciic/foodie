package com.example.foodie.bindingAdapters

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.example.foodie.data.database.entities.FoodFactEntity
import com.example.foodie.models.FoodFact
import com.example.foodie.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodFactBinding {

    companion object {

        @BindingAdapter("readApiResponse3", "readDatabase3", requireAll = false)    // false bc we're not gonna use both attributes on every view
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,                 // bc we're gonna need to use this binding adapter on 2 different views (MaterialCardView, ProgressBar)
            apiResponse: NetworkResult<FoodFact>?,
            database: List<FoodFactEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {       // when API is loading, set progressBar visibility to visible & materialCard visibility to invisible
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE      // when data loading show only progress bar
                        }
                        is MaterialCardView -> {
                            view.visibility = View.INVISIBLE    // hide materialCardView when data is loading
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE  // set MaterialCardView to visible when we receive an error
                            if (database != null) {         // but if DB is not null/empty, hide MaterialCardView
                                if (database.isEmpty()) {
                                    view.visibility = View.INVISIBLE    // no need to show it bc db is empty
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when(view){
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}