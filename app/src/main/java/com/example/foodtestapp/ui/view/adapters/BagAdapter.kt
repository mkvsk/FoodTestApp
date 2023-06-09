package com.example.foodtestapp.ui.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodtestapp.core.BagItem
import com.example.foodtestapp.ui.view.listeners.OnBagItemAddRemoveListener
import com.example.foodtestapp.ui.view.listeners.OnCategoryClickListener
import online.example.foodtestapp.R
import online.example.foodtestapp.databinding.RvBagItemBinding

class BagAdapter(private val context: Context) :
    RecyclerView.Adapter<BagAdapter.BagItemViewHolder>(), BindableAdapter<BagItem> {

    private var data: ArrayList<BagItem> = ArrayList()
    private lateinit var listener: OnBagItemAddRemoveListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagItemViewHolder {
        val binding = RvBagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BagItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BagAdapter.BagItemViewHolder, position: Int) {
        val bagDishItem = data[position]
        holder.bind(bagDishItem)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: ArrayList<BagItem>?) {
        data?.let {
            this.data = it
            notifyDataSetChanged()
        }
    }

    fun setClickListener(listener: OnBagItemAddRemoveListener) {
        this.listener = listener
    }

    inner class BagItemViewHolder(rvBagItemBinding: RvBagItemBinding) :
        RecyclerView.ViewHolder(rvBagItemBinding.root) {
        private val binding = rvBagItemBinding

        fun bind(bagDishItem: BagItem?) {
            Glide
                .with(context)
                .load(bagDishItem!!.dish!!.image_url)
                .into(binding.ivImage)

            binding.tvName.text = bagDishItem.dish!!.name.toString()
            binding.tvPrice.text = String.format(
                context.getString(R.string.dish_price_format),
                bagDishItem.dish!!.price.toString()
            )
            binding.tvWeight.text = String.format(
                context.getString(R.string.dish_weight_format),
                bagDishItem.dish!!.weight.toString()
            )
            binding.tvQty.text = bagDishItem.qty.toString()

            binding.btnMinus.setOnClickListener {
                listener.onBagItemAddRemove(bagDishItem, remove = true)
            }
            binding.btnPlus.setOnClickListener {
                listener.onBagItemAddRemove(bagDishItem, remove = false)
            }
        }
    }

}
