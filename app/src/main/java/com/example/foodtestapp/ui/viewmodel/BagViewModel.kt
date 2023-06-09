package com.example.foodtestapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodtestapp.core.BagItem
import com.example.foodtestapp.core.Dish
import online.example.foodtestapp.R

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
            if (bagItems.value!!.contains(BagItem(dish))) {
                val itemToAdd = bagItems.value!!.find { it.dish == dish }
                bagItems.value!!.forEach { bagItem ->
                    if (bagItem == itemToAdd) {
                        bagItem.qty!!.inc()
                    }
                }
            } else {
                bagItems.value!!.add(BagItem(dish))
            }
        }
    }

    fun removeFromBag(bagItem: BagItem) {
        val itemToRemove = bagItems.value!!.find { it == bagItem }
        if (itemToRemove!!.qty!! == 1) {
            bagItems.value!!.remove(itemToRemove)
        }
//        else if (itemToRemove.qty!! > 1) {
//            bagItems.value!!.find {
//                it.dish == dish
//                it.qty.inc()
//            }
//        }
    }
}