package com.example.foodtestapp.ui.view.listeners

import com.example.foodtestapp.core.BagItem

interface OnBagItemAddRemoveListener {
    fun onBagItemAddRemove(bagItem: BagItem, remove: Boolean)
}
