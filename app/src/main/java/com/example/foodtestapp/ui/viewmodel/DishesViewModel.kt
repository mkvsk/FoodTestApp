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
        val tmp = HashSet<String>()
        repository.getDishes(object : ResultCallback<GetDishesResponse> {
            override fun onResult(value: GetDishesResponse?) {
                value?.let {
                    setDishes(it.allDishes)
                    it.allDishes.forEach { dish ->
                        dish.tags.forEach { tag ->
                            tmp.add(tag)
                        }
                    }
                    setAllTags(tmp)
                }
            }

            override fun onFailure(value: GetDishesResponse?) {}

        })
    }

    private val _allTags = MutableLiveData<HashSet<String>>()
    val allTags: LiveData<HashSet<String>> get() = _allTags

    fun setAllTags(value: HashSet<String>) {
        _allTags.value = value
    }

    private val _tag = MutableLiveData<String>()
    val tag: LiveData<String> get() = _tag

    fun setTag(value: String) {
        _tag.value = value
    }
}