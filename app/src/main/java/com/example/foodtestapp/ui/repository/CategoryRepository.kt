package com.example.foodtestapp.ui.repository

import android.util.Log
import com.example.foodtestapp.callback.ResultCallback
import com.example.foodtestapp.network.RetrofitFactory
import com.example.foodtestapp.network.response.GetCategoriesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class CategoryRepository {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    companion object {
        const val TAG = "CategoryRepository"
    }

    fun getCategories(callback: ResultCallback<GetCategoriesResponse>) {
        scope.launch(Dispatchers.IO) {
            RetrofitFactory.apiService().getCategories()
                .enqueue(object : Callback<GetCategoriesResponse> {
                    override fun onResponse(
                        call: Call<GetCategoriesResponse>,
                        response: Response<GetCategoriesResponse>
                    ) {
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d(TAG, "GET CATEGORIES OK ${response.body().toString()}")
                            callback.onResult(response.body())
                        } else {
                            Log.d(
                                TAG,
                                "GET CATEGORIES ERROR ${response.body().toString()}"
                            )
                            callback.onFailure(null)
                        }
                    }

                    override fun onFailure(call: Call<GetCategoriesResponse>, t: Throwable) {
                        Log.d(TAG, "GET CATEGORIES EXCEPTION")
                        t.printStackTrace()
                        callback.onFailure(null)
                    }

                })
        }
    }

}