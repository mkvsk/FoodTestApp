package com.example.foodtestapp.ui.view.listeners

import com.example.foodtestapp.core.Dish

interface OnBagItemClick {
    fun onBagItemClick(dish: Dish, addOneMore: Boolean)
}
