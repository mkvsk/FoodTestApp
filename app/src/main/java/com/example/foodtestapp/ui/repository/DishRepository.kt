package com.example.foodtestapp.ui.repository

import android.util.Log
import com.example.foodtestapp.callback.ResultCallback
import com.example.foodtestapp.network.RetrofitFactory
import com.example.foodtestapp.network.response.GetDishesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class DishRepository {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    companion object {
        const val TAG = "DishRepository"
    }

    fun getDishes(callback: ResultCallback<GetDishesResponse>) {
        scope.launch(Dispatchers.IO) {
            RetrofitFactory.apiService().getDishes().enqueue(object : Callback<GetDishesResponse> {
                override fun onResponse(
                    call: Call<GetDishesResponse>,
                    response: Response<GetDishesResponse>
                ) {
                    if (response.code() == 200 || response.code() == 201) {
                        Log.d(CategoryRepository.TAG, "GET DISHES OK ${response.body().toString()}")
                        callback.onResult(response.body())
                    } else {
                        Log.d(
                            CategoryRepository.TAG,
                            "GET DISHES ERROR ${response.body().toString()}"
                        )
                        callback.onFailure(null)
                    }
                }

                override fun onFailure(call: Call<GetDishesResponse>, t: Throwable) {
                    Log.d(CategoryRepository.TAG, "GET DISHES EXCEPTION")
                    t.printStackTrace()
                    callback.onFailure(null)
                }

            })
        }
    }

}