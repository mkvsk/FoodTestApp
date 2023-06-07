package com.example.foodtestapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodtestapp.callback.ResultCallback
import com.example.foodtestapp.core.Dish
import com.example.foodtestapp.network.response.GetDishesResponse
import com.example.foodtestapp.ui.repository.DishRepository

class DishesViewModel : ViewModel() {
    companion object {
        private const val TAG = "DishesViewModel"
    }

    private val repository by lazy { DishRepository() }

    private val _allDishes = MutableLiveData<ArrayList<Dish>?>()
    val allDishes: LiveData<ArrayList<Dish>?> get() = _allDishes

    fun setDishes(value: ArrayList<Dish>) {
        _allDishes.value = value
    }

    fun getDishes() {
        repository.getDishes(object : ResultCallback<GetDishesResponse> {
            override fun onResult(value: GetDishesResponse?) {
                value?.let {
                    setDishes(it.allDishes)
                }
            }

            override fun onFailure(value: GetDishesResponse?) {}

        })
    }
}