package com.example.foodtestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.foodtestapp.ui.viewmodel.BagViewModel
import com.example.foodtestapp.ui.viewmodel.DishesViewModel
import com.example.foodtestapp.ui.viewmodel.FoodCategoriesViewModel
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