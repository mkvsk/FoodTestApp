package com.example.foodtestapp.callback

import java.io.Serializable

interface ResultCallback<T> : Serializable {
    fun onResult(value: T?)
    fun onFailure(value: T?)
}