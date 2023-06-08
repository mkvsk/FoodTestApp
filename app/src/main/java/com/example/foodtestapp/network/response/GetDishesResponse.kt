package com.example.foodtestapp.network.response

import com.example.foodtestapp.core.Dish
import com.google.gson.annotations.SerializedName

class GetDishesResponse(
    @SerializedName("dishes")
    val allDishes: ArrayList<Dish>
)
