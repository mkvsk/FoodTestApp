package com.example.foodtestapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodtestapp.core.Dish

class UserViewModel : ViewModel() {
    companion object {
        const val TAG = "UserViewModel"
    }

    private val _locationCity = MutableLiveData<String>()
    val locationCity: LiveData<String> get() = _locationCity

    fun setLocationCity(value: String) {
        _locationCity.value = value
    }

}