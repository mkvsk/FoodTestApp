package com.example.foodtestapp.ui.view

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtestapp.core.Dish
import com.example.foodtestapp.ui.util.obtainViewModel
import com.example.foodtestapp.ui.view.adapters.BagAdapter
import com.example.foodtestapp.ui.view.listeners.OnBagItemClick
import com.example.foodtestapp.ui.viewmodel.BagViewModel
import online.example.foodtestapp.databinding.FragmentBagBinding

class BagFragment : Fragment(), OnBagItemClick {
    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!

    private val bagViewModel by lazy { obtainViewModel(BagViewModel::class.java) }

    private var bagAdapter: BagAdapter? = null
    private var rv: RecyclerView? = null
    final val KEY_RECYCLER_STATE = "recycler_state"
    private var mBundleRecyclerViewState: Bundle? = null
    private var mListState: Parcelable? = null
    private var mRecyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onBagItemClick(dish: Dish, addOneMore: Boolean) {

    }


}