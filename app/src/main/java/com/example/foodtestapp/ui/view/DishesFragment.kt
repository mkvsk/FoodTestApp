package com.example.foodtestapp.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtestapp.core.Dish
import com.example.foodtestapp.ui.util.AppDialogBuilder
import com.example.foodtestapp.ui.util.AppDialogListener
import com.example.foodtestapp.ui.util.obtainViewModel
import com.example.foodtestapp.ui.view.adapters.DishAdapter
import com.example.foodtestapp.ui.view.adapters.TagAdapter
import com.example.foodtestapp.ui.view.listeners.OnDishClickListener
import com.example.foodtestapp.ui.view.listeners.OnTagClickListener
import com.example.foodtestapp.ui.viewmodel.BagViewModel
import com.example.foodtestapp.ui.viewmodel.DishesViewModel
import com.example.foodtestapp.ui.viewmodel.FoodCategoriesViewModel
import online.example.foodtestapp.databinding.FragmentDishesBinding

class DishesFragment : Fragment(), OnDishClickListener, OnTagClickListener {
    private var _binding: FragmentDishesBinding? = null
    private val binding get() = _binding!!

    private val dishesViewModel by lazy { obtainViewModel(DishesViewModel::class.java) }
    private val foodCategoriesViewModel by lazy { obtainViewModel(FoodCategoriesViewModel::class.java) }
    private val bagViewModel by lazy { obtainViewModel(BagViewModel::class.java) }

    private var tagAdapter: TagAdapter? = null
    private var rvTag: RecyclerView? = null

    private var dishAdapter: DishAdapter? = null
    private var rvDish: RecyclerView? = null
    private var dishesByTag: ArrayList<Dish> = ArrayList()

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
        dishesViewModel.getDishes()
        setupTagAdapter()
        setupDishAdapter()
        initObservers()
        setupMenu()
    }

    private fun setupMenu() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupTagAdapter() {
        rvTag = binding.rvTags
        tagAdapter = TagAdapter(requireContext())
        tagAdapter!!.setClickListener(this)
        rvTag!!.adapter = tagAdapter
    }

    private fun setupDishAdapter() {
        rvDish = binding.rvDishes
        mRecyclerView = rvDish
        dishAdapter = DishAdapter(requireContext())
        dishAdapter!!.setClickListener(this)
        rvDish!!.adapter = dishAdapter
        rvDish!!.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun initObservers() {
        foodCategoriesViewModel.selectedCategory.observe(viewLifecycleOwner) {
            binding.toolbarTitle.text = it.name.toString()
        }

        dishesViewModel.allDishes.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                dishAdapter!!.setData(dishesViewModel.allDishes.value)
            }
        }

        dishesViewModel.allTags.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                tagAdapter!!.setData(dishesViewModel.allTags.value)
                tagAdapter!!.setSelected(0)
            }
        }

        dishesViewModel.tag.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty() && (!dishesViewModel.allDishes.value.isNullOrEmpty())) {
                dishesByTag.clear()
                dishesByTag.addAll(dishesViewModel.allDishes.value!!.filter { dish ->
                    dish.tags.contains(
                        dishesViewModel.tag.value
                    )
                })
                dishAdapter!!.setData(dishesByTag)
            }
        }

    }

    override fun onDishClick(dish: Dish) {
        val dishInfoDialog = AppDialogBuilder()
        dishInfoDialog.getDishInfoDialog(
            dish,
            requireContext(),
            layoutInflater,
            object : AppDialogListener {
                override fun onClick(addToBag: Boolean) {
                    if (addToBag) {
                        bagViewModel.addToBag(dish)
                    }
                }
            }).show()
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

    override fun onTagClick(previousItem: Int, selectedItem: Int, tag: String) {
        tagAdapter!!.selectedTag = selectedItem
        tagAdapter!!.notifyItemChanged(previousItem)
        tagAdapter!!.notifyItemChanged(selectedItem)
        dishesViewModel.setTag(tag)
    }

}