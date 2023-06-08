package com.example.foodtestapp.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtestapp.core.Dish
import com.example.foodtestapp.ui.util.obtainViewModel
import com.example.foodtestapp.ui.view.adapters.DishAdapter
import com.example.foodtestapp.ui.view.listeners.OnDishClickListener
import com.example.foodtestapp.ui.viewmodel.DishesViewModel
import online.example.foodtestapp.databinding.FragmentDishesBinding

class DishesFragment : Fragment(), OnDishClickListener {
    private var _binding: FragmentDishesBinding? = null
    private val binding get() = _binding!!

    private val dishesViewModel by lazy { obtainViewModel(DishesViewModel::class.java) }

    private var dishAdapter: DishAdapter? = null
    private var rv: RecyclerView? = null
    final val KEY_RECYCLER_STATE = "recycler_state"
    private var mBundleRecyclerViewState: Bundle? = null
    private var mListState: Parcelable? = null
    private var mRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        dishesViewModel.getDishes()
        setupTagAdapter()
        setupDishAdapter()
        initObservers()
        initListeners()
    }

    private fun setupTagAdapter() {

    }

    private fun setupDishAdapter() {

    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }

    private fun initViews() {

    }

    override fun onDishClickListener(dish: Dish) {

    }

    override fun onResume() {
        super.onResume()
        if (mBundleRecyclerViewState != null) {
            Looper.myLooper()?.let {
                Handler(it).post {
                    mListState = mBundleRecyclerViewState?.getBundle(KEY_RECYCLER_STATE)
                    mRecyclerView!!.layoutManager?.onRestoreInstanceState(mListState)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mBundleRecyclerViewState = Bundle()
        mListState = mRecyclerView!!.layoutManager?.onSaveInstanceState()
        mBundleRecyclerViewState?.putParcelable(KEY_RECYCLER_STATE, mListState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}