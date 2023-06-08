package com.example.foodtestapp.core

import com.google.gson.annotations.SerializedName

data class Dish(
    @SerializedName("id")
    var id: Int? = 0,

    @SerializedName("name")
    var name: String? = "",

    @SerializedName("price")
    var price: Double? = 0.0,

    @SerializedName("weight")
    var weight: Double? = 0.0,

    @SerializedName("description")
    var description: String? = "",

    @SerializedName("image_url")
    var image_url: String? = "",

    @SerializedName("tegs")
    var tags: ArrayList<String> = ArrayList()
)