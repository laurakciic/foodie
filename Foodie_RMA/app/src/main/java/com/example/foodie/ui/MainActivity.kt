package com.example.foodie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import foodie.R
import foodie.databinding.ActivityMainBinding

// navigating from one to another fragment in bottom navigation view

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(                  // pass our destinations
            setOf(
                R.id.recipesFragment,
                R.id.favouriteRecipesFragment,
                R.id.cookingTipFragment
            )
        )

        // bottomNavView to access view from activity_main.xml layout
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    // called whenever user chooses to navigate Up within application's activity hierarchy from the action bar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}