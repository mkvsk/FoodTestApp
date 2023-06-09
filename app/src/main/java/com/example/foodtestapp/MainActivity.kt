package com.example.foodtestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodtestapp.ui.viewmodel.BagViewModel
import com.example.foodtestapp.ui.viewmodel.DishesViewModel
import com.example.foodtestapp.ui.viewmodel.FoodCategoriesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import online.example.foodtestapp.R
import online.example.foodtestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var bagViewModel: BagViewModel
    private lateinit var dishViewModel: DishesViewModel
    private lateinit var foodCategoriesViewModel: FoodCategoriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateViewModels()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfig = AppBarConfiguration(setOf(R.id.home_dest, R.id.bag_dest))
//        setupActionBarWithNavController(navController, appBarConfig)
        navView.setupWithNavController(navController)
    }

    private fun instantiateViewModels() {
        bagViewModel = ViewModelProvider(this)[BagViewModel::class.java]
        dishViewModel = ViewModelProvider(this)[DishesViewModel::class.java]
        foodCategoriesViewModel = ViewModelProvider(this)[FoodCategoriesViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}