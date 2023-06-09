package com.example.foodtestapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodtestapp.callback.ResultCallback
import com.example.foodtestapp.core.FoodCategory
import com.example.foodtestapp.network.response.GetCategoriesResponse
import com.example.foodtestapp.ui.repository.CategoryRepository

class FoodCategoriesViewModel : ViewModel() {
    companion object {
        private const val TAG = "CategoriesViewModel"
    }

    private val repository by lazy { CategoryRepository() }

    private val _allCategories = MutableLiveData<ArrayList<FoodCategory>?>()
    val allCategories: LiveData<ArrayList<FoodCategory>?> get() = _allCategories

    fun setCategories(value: ArrayList<FoodCategory>?) {
        _allCategories.value = value
    }

    fun getCategories() {
        repository.getCategories(object : ResultCallback<GetCategoriesResponse> {
            override fun onResult(value: GetCategoriesResponse?) {
                value?.let {
                    setCategories(it.allCategories)
                }
            }

            override fun onFailure(value: GetCategoriesResponse?) {}

        })
    }

    private val _selectedCategory = MutableLiveData<FoodCategory>()
    val selectedCategory: LiveData<FoodCategory> get() = _selectedCategory
    fun setSelectedCategory(foodCategory: FoodCategory) {
        _selectedCategory.value = foodCategory
    }
}