package com.example.foodie.ui.fragments.favourites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodie.adapters.FavoriteRecipesAdapter
import com.example.foodie.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import foodie.R
import foodie.databinding.FragmentFavouriteRecipesBinding

@AndroidEntryPoint  // bc MainViewModel is using dependency injection
class FavouriteRecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }    // requireActivity is bc of setOnLongClickListener

    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!                             // read only variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter

        setHasOptionsMenu(true)  // shows menu in action bar

        setupRecyclerView(binding.favoriteRecipesRecyclerView)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)    // inflater to inflate menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAll_favorite_recipes_menu){    // delete all fav recipes
            mainViewModel.deleteAllFavoriteRecipes()
            showSnackBar()  // no params since fun is here used only once so the message is in impl
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(){
        Snackbar.make(
            binding.root,
            "All recipes removed.",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    // called whenever FavouriteRecipesFragment is finished/closed (when we navigate away from him)
    override fun onDestroyView() {
        super.onDestroyView()          // whenever we destroy fragment, we set binding to null
        _binding = null
        mAdapter.clearContextualActionMode()
    }
}