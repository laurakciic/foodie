package com.example.foodie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodie.models.FoodRecipe
import com.example.foodie.models.Result
import com.example.foodie.util.RecipesDiffUtil
import foodie.databinding.RecipesRowLayoutBinding   // automatically generated class

// RecipesAdapter - needed in order to display API data in recycler view
// also binds recipes adapter data with recipes_row_layout
// extends RecyclerView.Adapter, passed MyViewHolder class

// also created RecipesDiffUtil class for RecipesAdapter
class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()        // empty list var is a type of Result model class

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {     // passing the root element from recipes_row_layout.xml

        fun bind(result: Result) {                  // left var result = var created inside recipes_row_layout
            binding.result = result                 // right var result = var passed in param
            binding.executePendingBindings()        // updates layout whenever there's a change inside data
        }

        companion object {
            // with this fun we're returning class MyViewHolder created above
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)    // binding is passed by inflating the layout (recipes_row_layout)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)    // gets parent from param, parent is then used in from() fun to create LayoutInflater
    }                                       //      and binding

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // creating a var that will hold every row in RecyclerView and then we're binding that data/recipe to recipe_row_layout with bind() fun above
        val currentRecipe = recipes[position]    // stores current item from RecyclerView because we're using position param
                                                 //   to get dynamically the position of a row
        // holder is MyViewHolder
        // we need to call its bind() fun to bind var from recipes_row_layout with currentResult in his onBindViewHolder
        // this will make RecyclerView update each and every time new data from API is received
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size                  // recipes is an empty list created above
    }

    // fun used from recipes fragment to set data from recipe (called every time we fetch data from API)
    // passing newData every time
    // getting results from FoodRecipe and storing them inside recipe var (empty list)
    // every time there's new data from this fun, we want to store that data inside empty list recipes var
    fun setData(newData: FoodRecipe){

        // calling RecipesDiffUtil class and passing old and new list
        // .results to access data because we're using model class (Result) and not FoodRecipe
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)    // calculate diff between those 2 lists, will update only views that are not the same
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)                  // this refers to this class which is an RecyclerView adapter

        //notifyDataSetChanged()     tell RecyclerView to update the values (views) when receiving new data - overkill

    }
}