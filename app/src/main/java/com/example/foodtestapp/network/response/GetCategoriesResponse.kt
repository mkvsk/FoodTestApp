package com.example.foodtestapp.network.response

import com.example.foodtestapp.core.FoodCategory
import com.google.gson.annotations.SerializedName

class GetCategoriesResponse(
    @SerializedName("сategories")
    val allCategories: ArrayList<FoodCategory>
)
