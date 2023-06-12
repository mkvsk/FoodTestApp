package com.example.foodtestapp.ui.view

import android.os.Bundle
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
        dishAdapter = DishAdapter(requireContext())
        dishAdapter!!.setClickListener(this)
        rvDish!!.adapter = dishAdapter
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

                if (dishesViewModel.tag.value.isNullOrEmpty()) {
                    tagAdapter!!.setSelected(0)
                } else {
                    tagAdapter!!.setSelected(it.indexOf(dishesViewModel.tag.value))
                    dishesViewModel.tag.value?.let { tag ->
                        dishesViewModel.setTag(tag)
                    }
                }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTagClick(previousItem: Int, selectedItem: Int, tag: String) {
        tagAdapter!!.setSelected(selectedItem)
        tagAdapter!!.notifyItemChanged(previousItem)
        tagAdapter!!.notifyItemChanged(selectedItem)
        dishesViewModel.setTag(tag)
    }

}