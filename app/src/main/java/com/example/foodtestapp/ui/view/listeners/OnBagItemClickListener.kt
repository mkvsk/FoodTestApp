package com.example.foodtestapp.ui.view.listeners

import com.example.foodtestapp.core.Dish

interface OnBagItemClickListener {
    fun onBagItemClick(dish: Dish, addOneMore: Boolean)
}
