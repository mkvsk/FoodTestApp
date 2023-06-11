package com.example.foodtestapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodtestapp.core.BagItem
import com.example.foodtestapp.core.Dish

class BagViewModel : ViewModel() {
    companion object {
        private const val TAG = "BagViewModel"
    }

    private val _totalSum = MutableLiveData<Double>(0.0)
    val totalSum: LiveData<Double> get() = _totalSum

    fun setTotalSum(value: Double) {
        _totalSum.value = value
    }

    private val _bagItems = MutableLiveData<ArrayList<BagItem>?>()
    val bagItems: LiveData<ArrayList<BagItem>?> get() = _bagItems

    fun setBagItems(value: ArrayList<BagItem>) {
        _bagItems.value = value
    }

    fun recalculateTotalSum() {
        if (bagItems.value.isNullOrEmpty()) {
            setTotalSum(0.0)
        } else {
            setTotalSum(bagItems.value!!.sumOf { it.dish!!.price!! * it.qty!! }.toDouble())
        }
    }

    fun addToBag(dish: Dish) {
        if (bagItems.value == null) {
            setBagItems(ArrayList())
            bagItems.value!!.add(BagItem(dish))
        } else {
            val findItem = bagItems.value!!.find { it.dish!!.id!! == dish.id }
            if (findItem != null) {
                findItem.qty = findItem.qty!! + 1
            } else {
                bagItems.value!!.add(BagItem(dish))
            }
        }
        recalculateTotalSum()
    }

    fun removeFromBag(bagItem: BagItem) {
        val itemToRemove = bagItems.value!!.find { it == bagItem }
        if (itemToRemove!!.qty!! <= 1) {
            bagItems.value!!.remove(itemToRemove)
        } else {
            itemToRemove.qty = itemToRemove.qty!! - 1
        }
        recalculateTotalSum()
    }
}