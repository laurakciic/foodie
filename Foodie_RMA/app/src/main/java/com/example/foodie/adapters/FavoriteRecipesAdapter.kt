package com.example.foodie.adapters

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodie.data.database.entities.FavoritesEntity
import com.example.foodie.util.RecipesDiffUtil
import com.example.foodie.viewModels.MainViewModel
import foodie.databinding.FavoriteRecipesRowLayoutBinding

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoritesEntity>() // empty list of type FavoritesEntity

    class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // binding layout with RecyclerView
        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()            // updates all views
        }

        companion object {      // FavoriteRecipesRowLayoutBinding is autogenerated by DataBinding
            fun from(parent: ViewGroup): MyViewHolder { // returns MyViewHolder
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesAdapter.MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteRecipesAdapter.MyViewHolder, position: Int) {
        val currentRecipe = favoriteRecipes[position]   // get each & every row from RecyclerView, storing it dynamically in currentRecipe val
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)                // creating RecipeDiffUtilObject while passing old & new list
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        TODO("Not yet implemented")
    }
}

