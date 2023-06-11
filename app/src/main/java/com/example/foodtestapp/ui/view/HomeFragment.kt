package com.example.foodtestapp.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtestapp.core.FoodCategory
import com.example.foodtestapp.ui.util.FormatUtil
import com.example.foodtestapp.ui.util.obtainViewModel
import com.example.foodtestapp.ui.view.adapters.CategoryAdapter
import com.example.foodtestapp.ui.view.listeners.OnCategoryClickListener
import com.example.foodtestapp.ui.viewmodel.FoodCategoriesViewModel
import com.example.foodtestapp.ui.viewmodel.UserViewModel
import online.example.foodtestapp.R
import online.example.foodtestapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), OnCategoryClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val foodCategoriesViewModel by lazy { obtainViewModel(FoodCategoriesViewModel::class.java) }
    private val userViewModel by lazy { obtainViewModel(UserViewModel::class.java) }

    private var categoryAdapter: CategoryAdapter? = null
    private var rv: RecyclerView? = null
    final val KEY_RECYCLER_STATE = "recycler_state"
    private var mBundleRecyclerViewState: Bundle? = null
    private var mListState: Parcelable? = null
    private var mRecyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodCategoriesViewModel.getCategories()
        setupAdapter()
        initObservers()
        initViews()
    }

    private fun setupAdapter() {
        rv = binding.rvCategories
        mRecyclerView = rv
        categoryAdapter = CategoryAdapter(requireContext())
        categoryAdapter!!.setClickListener(this)
        rv!!.adapter = categoryAdapter
        rv!!.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun initObservers() {
        foodCategoriesViewModel.allCategories.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                categoryAdapter!!.setData(foodCategoriesViewModel.allCategories.value)
            }
        }

        userViewModel.locationCity.observe(viewLifecycleOwner) {
            it.let {
                binding.toolbar.title = it
            }
        }
    }

    private fun initViews() {
        binding.toolbar.subtitle = FormatUtil.getCurrentDateFormat()
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

    override fun onCategoryClick(selectedCategory: FoodCategory) {
        foodCategoriesViewModel.setSelectedCategory(selectedCategory)
        NavHostFragment.findNavController(this).navigate(R.id.action_go_to_dishes_from_categories)
    }

}