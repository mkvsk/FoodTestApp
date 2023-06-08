package com.example.foodtestapp.core

import com.google.gson.annotations.SerializedName

data class FoodCategory(
    @SerializedName("id")
    var id: Int? = 0,

    @SerializedName("name")
    var name: String? = "",

    @SerializedName("image_url")
    var image_url: String? = ""
)