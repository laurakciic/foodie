package com.example.foodie.util

import androidx.recyclerview.widget.DiffUtil

// utility class
class RecipesDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    // checks whether two objects represent the same Item in the old and new list
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    // checks whether two items have the same data
    // called by DiffUtil only if areItemsTheSame returns true
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}