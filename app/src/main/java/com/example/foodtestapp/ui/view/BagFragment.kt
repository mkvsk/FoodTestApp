package com.example.foodtestapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtestapp.core.BagItem
import com.example.foodtestapp.ui.util.FormatUtil
import com.example.foodtestapp.ui.util.obtainViewModel
import com.example.foodtestapp.ui.view.adapters.BagAdapter
import com.example.foodtestapp.ui.view.listeners.OnBagItemAddRemoveListener
import com.example.foodtestapp.ui.viewmodel.BagViewModel
import com.example.foodtestapp.ui.viewmodel.UserViewModel
import online.example.foodtestapp.R
import online.example.foodtestapp.databinding.FragmentBagBinding

class BagFragment : Fragment(), OnBagItemAddRemoveListener {
    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!

    private val bagViewModel by lazy { obtainViewModel(BagViewModel::class.java) }
    private val userViewModel by lazy { obtainViewModel(UserViewModel::class.java) }

    private var bagAdapter: BagAdapter? = null
    private var rv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupAdapter()
        initObservers()
    }

    private fun initViews() {
        binding.toolbar.subtitle = FormatUtil.getCurrentDateFormat()
    }

    private fun initObservers() {
        userViewModel.locationCity.observe(viewLifecycleOwner) {
            it.let {
                binding.toolbar.title = it
            }
        }

        bagViewModel.bagItems.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                bagAdapter!!.setData(bagViewModel.bagItems.value)
                bagViewModel.recalculateTotalSum()
            } else {
                bagViewModel.setTotalSum(0.0)
            }
        }

        bagViewModel.totalSum.observe(viewLifecycleOwner) {
            if (it == 0.0) {
                binding.btnAddToBag.visibility = View.GONE
            } else {
                binding.btnAddToBag.text = String.format(
                    resources.getString(R.string.bag_dish_price_format), bagViewModel.totalSum.value
                )
                binding.btnAddToBag.visibility = View.VISIBLE
            }
        }
    }

    private fun setupAdapter() {
        rv = binding.rvBag
        bagAdapter = BagAdapter(requireContext())
        bagAdapter!!.setClickListener(this)
        rv!!.adapter = bagAdapter
        rv!!.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBagItemAddRemove(bagItem: BagItem, remove: Boolean) {
        if (remove) {
            bagViewModel.removeFromBag(bagItem)
        } else {
            bagViewModel.addToBag(bagItem.dish!!)
        }
        bagAdapter!!.notifyDataSetChanged()
    }

}