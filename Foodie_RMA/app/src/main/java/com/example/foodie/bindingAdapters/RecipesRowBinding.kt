package com.example.foodie.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import foodie.R

// holds all the bindings (binding adaptors) for recipes_row_layout
class RecipesRowBinding {

    // companion obj so it can access fun inside it elsewhere in project
    companion object {

        // fun using Coil to display images inside recycler view
        // params: ImageView on which we're going to use this fun/binding adaptor, and image url
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)  // when image is loading it will have a fade in animation 600 ms
                error(R.drawable.ic_error_placeholder)
            }
        }

        // convert int value of number of likes to string
        // params: view on which we're going to use this fun (TextView), value (integer) of likes
        @BindingAdapter("setNumberOfLikes")    // name of the attribute used in recipes_row_layout
        @JvmStatic                                    // telling compiler to make fun static so we can access it elsewhere in project
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        // convert int value of number of minutes to string
        // same princip as above
        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        // fun to colour leaf image and Vegan text to green if the recipe is vegan
        // params: first one is View because it's parent class of both TextView and ImageView that we used (leaf and text)
        @BindingAdapter("applyVeganColour")
        @JvmStatic
        fun applyVeganColour(view: View, vegan: Boolean) {      // boolean for is it vegan
            if(vegan) {
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }
    }

}