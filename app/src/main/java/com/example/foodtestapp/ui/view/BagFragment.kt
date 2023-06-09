package com.example.foodtestapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtestapp.core.BagItem
import com.example.foodtestapp.core.Dish
import com.example.foodtestapp.ui.util.obtainViewModel
import com.example.foodtestapp.ui.view.adapters.BagAdapter
import com.example.foodtestapp.ui.view.adapters.CategoryAdapter
import com.example.foodtestapp.ui.view.listeners.OnBagItemAddRemoveListener
import com.example.foodtestapp.ui.viewmodel.BagViewModel
import online.example.foodtestapp.R
import online.example.foodtestapp.databinding.FragmentBagBinding

class BagFragment : Fragment(), OnBagItemAddRemoveListener {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdaper()
        initObservers()
    }

    private fun initObservers() {
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

    private fun setupAdaper() {
        rv = binding.rvBag
        mRecyclerView = rv
        bagAdapter = BagAdapter(requireContext())
        bagAdapter!!.setClickListener(this)
        rv!!.adapter = bagAdapter
        rv!!.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBagItemAddRemove(bagItem: BagItem, remove: Boolean) {
        if (remove) {
            bagViewModel.removeFromBag(bagItem)
            bagViewModel.recalculateTotalSum()
        } else {
            bagViewModel.addToBag(bagItem.dish!!)
        }
        bagAdapter!!.setData(bagViewModel.bagItems.value)
        bagAdapter!!.notifyDataSetChanged()
    }
}