package com.example.foodtestapp.core

import com.fasterxml.jackson.annotation.JsonProperty

data class Dish(
    var id: Int? = 0,
    var name: String? = "",
    var price: Double? = 0.0,
    var weight: Double? = 0.0,
    var description: String? = "",
    var image_url: String? = "",
    @JsonProperty("tegs")
    var tags: ArrayList<String> = ArrayList()
)