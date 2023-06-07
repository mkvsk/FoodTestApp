package com.example.foodtestapp.network

import com.example.foodtestapp.network.response.GetCategoriesResponse
import com.example.foodtestapp.network.response.GetDishesResponse
import retrofit2.Call
import retrofit2.http.GET

interface NetworkInfoService {

    @GET("058729bd-1402-4578-88de-265481fd7d54")
    fun getCategories(): Call<GetCategoriesResponse>

    @GET("c7a508f2-a904-498a-8539-09d96785446e")
    fun getDishes(): Call<GetDishesResponse>
}